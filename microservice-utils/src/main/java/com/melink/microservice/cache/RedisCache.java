package com.melink.microservice.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.melink.microservice.cache.tag.Tag;
import com.melink.microservice.exception.PlatformException;
import com.melink.microservice.json.JsonSerializer;
import com.melink.microservice.utils.SerializeUtil;
import com.melink.microservice.utils.Tools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

public class RedisCache extends AbstractPlatformCache<String, Object> {

	private final Logger log = LoggerFactory.getLogger(RedisCache.class);

	private final JedisPool redisPool;

	private int ttl = 60 * 20; // default the ttl to 20 minutes. this can be
								// tweaked in the spring config
	// or on the fly using the backdoor controller.

	public RedisCache(JedisPool pool, String name) {
		super(name);
		this.redisPool = pool;
	}

	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
		} catch (Exception e) {
			log.warn("jedis get resource error", e);
		}
		return jedis;
	}

	public Object putByte(String key, Object value) {
		return putByte(key, value, ttl);
	}

	public Object putByte(String key, Object value, int ttl) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}

		Jedis client = null;
		try {
			client = getJedis();
			if (value instanceof String) {
				return client.setex(key, ttl, value.toString());
			}
//			if (value instanceof Number) {
//				return client.setex(key, ttl, value.toString());
//			}

			if (ttl == -1) {
				return client.set(key.getBytes(), SerializeUtil.serialize(value));
			} else {
				return client.setex(key.getBytes(), ttl, SerializeUtil.serialize(value));
			}
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object put(String key, Object value) {
		return put(key, value, ttl);
	}

	public Object put(String key, Object value, int ttl) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}

		Jedis client = null;
		try {
			client = redisPool.getResource();
			if (value instanceof String) {
				return client.setex(key, ttl, value.toString());
			}
			if (value instanceof Number) {
				return client.setex(key, ttl, value.toString());
			}

			return client.setex(key, ttl, JsonSerializer.newInstance().toJson(value));
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object get(String key) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		String entry = null;
		Jedis client = null;
		try {
			client = redisPool.getResource();
			entry = client.get(key);
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}

		if (entry == null) {
			log.warn("lsp.cache: cache miss for key {}", key);
			return null;
		}

		try {
			return JsonSerializer.newInstance().fromJson(entry, Object.class);
		} catch (Exception ex) {
			log.error("Exception hit trying to transfer entry for key {}: " + ex.getMessage(), key);
			return entry;
		}
	}

	public Object get(String key, Class clazz) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		String entry = null;
		Jedis client = null;
		try {
			client = redisPool.getResource();
			entry = client.get(key);
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}

		if (entry == null) {
			log.warn("lsp.cache: cache miss for key {}", key);
			return null;
		}
		if (clazz == String.class) {
			return entry;
		}
		try {
			return JsonSerializer.newInstance().fromJson(entry, clazz);
		} catch (Exception ex) {
			log.error("Exception hit trying to transfer entry for key {}", key);
			return entry;
		}
	}

	public Object get(String key, Class clazz1, Class clazz2) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		String entry = null;
		Jedis client = null;
		try {
			client = redisPool.getResource();
			entry = client.get(key);
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}

		if (entry == null) {
			log.warn("lsp.cache: cache miss for key {}", key);
			return null;
		}

		try {
			JavaType type = JsonSerializer.newInstance().getMapper().getTypeFactory().constructParametricType(clazz1, clazz2);
			return JsonSerializer.newInstance().fromJson(entry, type);
		} catch (Exception ex) {
			log.error("Exception hit trying to transfer entry for key {}", key);
			return entry;
		}
	}

	public Object getObject(String key) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		byte[] entry = null;
		Jedis client = null;
		try {
			client = getJedis();
			entry = client.get(key.getBytes());
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}

		if (entry == null) {
			log.warn("lsp.cache: cache miss for key {}", key);
			return null;
		}

		try {
			Object unserialize = SerializeUtil.unserialize(entry);
			return unserialize;
		} catch (Exception ex) {
			log.error("Exception hit trying to transfer entry for key {}", key);
			return entry;
		}
	}

	public Object remove(String key) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Bailing", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}
		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.del(key);
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to perform delete for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public void clear() {
		// throw new CacheMethodNotImplemented();
		Jedis client = null;
		try {
			client = redisPool.getResource();
			client.flushAll();
		} catch (Exception e) {
			log.error("flush redis fail", e);
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Set<String> keys() {
		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.keys("*");
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to perform return keys. returning null", e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Set<String> keys(String regularStr) {
		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.keys(regularStr);
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to perform return keys. returning null", e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public int size() {
		throw new CacheMethodNotImplemented();
	}

	public Collection values() {
		throw new CacheMethodNotImplemented();
	}


	public void destroy() {
		log.error("destroy redisPool");
		try {
			redisPool.destroy();
		} catch (Exception e) {
			throw PlatformException.asRuntimeException(new CacheException(e));
		}
	}

	@Override
	public String info(String section) {
		Jedis client = null;
		try {
			client = redisPool.getResource();
			String infoStr = client.info(section);
			return infoStr;
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}

	public void invalidate(Tag[] tags) {
		if (ArrayUtils.isEmpty(tags))
			return;

		for (Tag tag : tags) {
			Set<String> keysToInvalidate = (Set<String>) get(tag.toString());
			if (CollectionUtils.isEmpty(keysToInvalidate))
				continue;
			for (String toInvalidate : keysToInvalidate) {
				log.debug("lsp:invalidating {}", toInvalidate);
				remove(toInvalidate);
			}
		}
	}

	public void tagKey(Tag[] tags, String key) {
		if (ArrayUtils.isEmpty(tags))
			return;

		for (Tag tag : tags) {
			Set<String> s = (Set<String>) get(tag.toString());
			if (s == null)
				s = new HashSet<String>();
			s.add(key);
			put(tag.toString(), s);
		}

	}

	private boolean nodesConnected() {
		return !redisPool.isClosed();
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public Object hset(String key, String field, Object value) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}

		Jedis client = null;
		try {
			client = redisPool.getResource();
			if(value instanceof String){
				return client.hset(key,field,(String)value);
			}
			return client.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	@Override
	public Object hset(String key, String field, Object value, int ttl) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}

		Jedis client = null;
		try {
			client = redisPool.getResource();
			if(client.exists(key)){
				if(value instanceof String){
					return client.hset(key,field,(String)value);
				}else{
					return client.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
				}
			}else{
				if(value instanceof String){
					client.hset(key,field,(String)value);
				}else{
					client.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
				}
				return client.expire(key,ttl);
			}
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	@Override
	public void setExpire(String key, int ttl) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return;
		}

		Jedis client = null;
		try {
			client = redisPool.getResource();
			client.expire(key,ttl);
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	@Override
	public Object hsetCoverTtl(String key, String field, Object value, int ttl) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}

		Jedis client = null;
		try {
			client = redisPool.getResource();
			if(value instanceof String){
				client.hset(key,field,(String)value);
			}else{
				client.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
			}
			return client.expire(key,ttl);
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}


	@Override
	public String hgetStr(String key, String field) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		Jedis client = null;
		String str = null;
		try {
			client = redisPool.getResource();
			str = client.hget(key, field);
//			if (str == null) {
//				log.warn("lsp.cache: cache miss for key {}", key);
//			}
			return str;
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object hget(String key, String field) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		byte[] entry = null;
		Jedis client = null;
		long l = System.currentTimeMillis();
		try {
			client = getJedis();
			long l1 = System.currentTimeMillis();
			if((l1-l) > 20){
				log.error("redis get resource time out " + (l1 - l));
			}
			entry = client.hget(key.getBytes(), field.getBytes());
			if (entry == null) {
				log.warn("lsp.cache: cache miss for key {}", key);
				return null;
			} else {
				long l3 = System.currentTimeMillis();
				if((l3-l1) > 20){
					log.error("redis read time out " + (l3 - l1));
				}
				Object object = SerializeUtil.unserialize(entry);
				long l2 = System.currentTimeMillis();
				if((l2 -l3) > 30){
					log.error("serialize time out " + (l2 - l3));
				}
				return object;
			}
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object hmset(String key, Map<byte[], byte[]> value) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Not adding to cache", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}

		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.hmset(key.getBytes(), value);
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to put for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object hdel(String key, String field) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Bailing", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return null;
		}
		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.hdel(key.getBytes(), field.getBytes());
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to perform delete for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object hgetAll(String key) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		Map<byte[], byte[]> entry = null;
		Jedis client = null;
		try {
			client = redisPool.getResource();
			entry = client.hgetAll(key.getBytes());
			if (entry == null) {
				log.warn("lsp.cache: cache miss for key {}", key);
				return null;
			} else {
				return entry;
			}
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public Object hmget(String key, String... fields) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return null;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return null;
		}
		List<byte[]> entry = null;
		Jedis client = null;
		byte[][] fieldBytes = new byte[fields.length][];
		for(int i=0;i<fields.length;i++){
			 String field = fields[i].toLowerCase();
			 fieldBytes[i] = field.getBytes();
		}
		long begin = System.currentTimeMillis();
		try {
			client = redisPool.getResource();
			entry = client.hmget(key.getBytes(), fieldBytes);
			long queryEnd = System.currentTimeMillis();
			long time = queryEnd - begin;
			if(time > 60){
				log.warn("hmget read time out " + time + "," + Tools.strArrayToStr(fields));
			}
			if (entry == null) {
//				log.warn("lsp.cache: cache miss for key {}", key);
				return null;
			}else{
				List<Object> list = new ArrayList<Object>();
				for(byte[] o:entry){
					if(o == null){
						list.add(null);
						continue;
					}
					list.add(SerializeUtil.unserialize(o));
				}
				long serializeEnd = System.currentTimeMillis();
				long time2 = serializeEnd - queryEnd;
				if(time2 > 100){
					log.warn("hmget serialize time out " + time2 + "," + Tools.strArrayToStr(fields));
				}
				return list;
			}
		} catch (Exception e) {
			log.error("Exception hit trying to perform get for key {}. returning null", key, e);
			return null;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	@Override
	public boolean hexists(String key, String field) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Bailing", key);
			return false;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}.", key);
			return false;
		}
		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.hexists(key,field);
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to perform delete for key {}. returning null", key, e);
			return false;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	public boolean exists(String key) {
		key = formatKey(key);
		if (key == null) {
			log.warn("error formatting key {}. Treating as cache miss and returing null", key);
			return false;
		}
		if (!nodesConnected()) {
			log.warn("No nodes found connected for key {}. Treating as cache miss and returing null", key);
			return false;
		}
		Jedis client = null;
		try {
			client = redisPool.getResource();
			return client.exists(key.getBytes());
		} catch (Exception e) {
			log.error("Some sort of exception hit trying to perform return keys. returning null", e);
			return false;
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

}
