package com.melink.dialog.tuling;

import com.melink.microservice.json.JsonSerializer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tuling.config")
public class Dialog {

    private String uri;
    private String key;
    public Dialog(){

    }

    public String say(String text) throws Exception {
        long start = System.currentTimeMillis();
        String encode = URLEncoder.encode(text,"UTF-8");
        String url = uri + "?key=" + key + "&info=" + encode;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        String result = null;
        //得到服务器的状态码
        int httpCode = response.getStatusLine().getStatusCode();
        if (httpCode >= 200 && httpCode < 400) {
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);
            Map<String, Object> map = JsonSerializer.json2map(string);
            result = (String)map.get("text");
        }
        System.out.println("图灵返回：" + result);
        long end = System.currentTimeMillis();
        System.out.println("图灵使用时间："+ (end-start));
        return result;
    }





    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
