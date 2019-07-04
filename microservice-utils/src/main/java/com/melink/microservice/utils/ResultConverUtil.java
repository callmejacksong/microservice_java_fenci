package com.melink.microservice.utils;


import com.melink.sop.api.models.AppConsts;
import com.melink.sop.api.models.QiNiuBucket;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ResultConverUtil {
    private static final Logger log = LoggerFactory.getLogger(ResultConverUtil.class);

    public Convert ConvertByAppId(boolean sslRes, String appId) {

        List<String> URLs = AppConsts.AppIdDomainUrlMap.get(appId);
        if (CollectionUtils.isEmpty(URLs)) {
            return new Convert(sslRes, QiNiuBucket.BQMM_SOSO_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET);
        }
        return new Convert(sslRes, URLs.get(0), URLs.get(1));
    }

    public class Convert {
        private boolean sslRes;
        private String bucket;
        private String httpsBucket;

        public Convert(boolean sslRes, String bucket, String httpsBucket) {
            this.sslRes = sslRes;
            this.bucket = bucket;
            this.httpsBucket = httpsBucket;
        }

        public <K> Convert converURL(K k, String... strings) throws Exception {
            if (k == null) {
                return this;
            }
            Class<?> aClass = k.getClass();
            for (String str : strings) {
                String v = null;
                Field field = null;
                try {
                    field = aClass.getDeclaredField(str);
                    field.setAccessible(true);
                    v = (String) field.get(k);
                } catch (Exception e) {
                    log.warn("Cannot find the corresponding field",e);
                    continue;
                }
                if (StringUtils.isEmpty(v)) {
                    continue;
                }
                if (sslRes) {
                    if (v.contains(QiNiuBucket.BQMM_BUCKET)) {
                        field.set(k, v.replace(QiNiuBucket.BQMM_BUCKET, httpsBucket));
                    } else {
                        field.set(k, httpsBucket + v);
                    }
                } else {
                    if (v.contains(QiNiuBucket.BQMM_BUCKET)) {
                        if (!v.contains(bucket)) {
                            field.set(k, v.replace(QiNiuBucket.BQMM_BUCKET, bucket));
                        }
                    } else {
                        field.set(k, bucket + v);
                    }
                }
            }
            return this;
        }
    }

}
