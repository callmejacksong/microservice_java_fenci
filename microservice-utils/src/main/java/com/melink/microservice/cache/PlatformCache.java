package com.melink.microservice.cache;


import com.melink.microservice.cache.tag.Tag;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface PlatformCache<K, V> {

	public V put(K key, V value);
	
	public V put(K key, V value, int ttl);
	
	public V putByte(K key, V value);

	public V putByte(K key, V value, int ttl);

	public V get(K key);

	public V getObject(K key);

	public V get(K key, Class clazz);

	public V get(K key, Class clazz1, Class clazz2);

	public V hset(K key, K field, V value);

	public V hset(K key, K field, V value, int ttl);

	public void setExpire(K key, int ttl);

	public V hsetCoverTtl(K key, K field, V value, int ttl);

	public String hgetStr(K key, K field);

	public V hget(K key, K field);

	public V hgetAll(K key);

	public V hmset(K key, Map<byte[], byte[]> value);

	public V hmget(K key, K... fields);

	public boolean hexists(K key, K field);

	public V hdel(K key, K fields);
	
	public Object remove(K key);
	
	public void clear();
	
	public Set<K> keys();
	
	public boolean exists(K key);
	
	public Set<K> keys(String regularStr);
	
	public int size();
	
	public Collection<V> values();
	
	public String getName();
	
	public void invalidate(Tag[] tags);
	
	public void tagKey(Tag[] tags, String key);
	
	public void destroy();

	public String getPrefix();

	public String info(String section);
	
}
