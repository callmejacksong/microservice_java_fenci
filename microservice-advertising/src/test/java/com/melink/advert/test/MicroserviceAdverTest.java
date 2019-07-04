package com.melink.advert.test;

import com.melink.advert.mapper.netPic.AdvertMapper;
import com.melink.advert.mapper.netPic.GlobalConfigMapper;
import com.melink.advert.mapper.netPic.PicturePromotionMapper;
import com.melink.advert.service.AdvertService;
import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.api.models.RedisPrefix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroserviceAdverTest {
    @Autowired
    private JedisPool redisPool;

    @Autowired
    private AdvertService advertService;
    @Autowired
    private AdvertMapper advertMapper;
    @Autowired
    private PlatformCache<String, Object> platformCache;
    @Autowired
    private PicturePromotionMapper picturePromotionMapper;
    @Autowired
    private GlobalConfigMapper globalConfigMapper;
    @Test
    public void test1(){
        Object o = platformCache.get(RedisPrefix._ADVERT_GLOBAL_SWITCH, String.class);
        Integer g = null;
        if (o != null) {
            g = Integer.parseInt(o.toString());

        } else {
            g = globalConfigMapper.findAdvertGlobal();
            platformCache.put(RedisPrefix._ADVERT_GLOBAL_SWITCH, g);
        }

    }

    @Test
    public void test2(){

    }


}
