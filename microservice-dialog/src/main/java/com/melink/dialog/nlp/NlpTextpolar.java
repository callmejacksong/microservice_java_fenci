package com.melink.dialog.nlp;

import com.melink.microservice.json.JsonSerializer;
import com.melink.microservice.utils.MD5;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tengxun.nlp")
public class NlpTextpolar {

    private String url;
    private String appId;
    private String appKey;

    public Integer textPolar(String text) throws Exception {
        long start = System.currentTimeMillis();
        String encode = URLEncoder.encode(text,"UTF-8");

        String params = "app_id=" + appId + "&nonce_str=songshijun&text=" + encode + "&time_stamp=" + System.currentTimeMillis() / 1000 + "&app_key=" + appKey;

        String sign = MD5.GetMD5Code(params).toUpperCase();
        params += "&sign=" + sign;


        String url = this.url + "?" + params;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        Integer result = null;
        //得到服务器的状态码
        int httpCode = response.getStatusLine().getStatusCode();
        if (httpCode >= 200 && httpCode < 400) {
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);
            Map<String, Object> map = JsonSerializer.json2map(string);
            Map<String,Object> data  = (Map<String,Object>)map.get("data");
            if (CollectionUtils.isEmpty(data)) {
                return null;
            }
            result = Integer.parseInt(data.get("polar").toString());
        }
        long end = System.currentTimeMillis();
        System.out.println("情感识别Time："+ (end-start));
        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
