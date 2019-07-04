package com.melink.dialog.ocr;

import com.qcloud.image.ImageClient;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.request.GeneralOcrRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix = "tengxun.ocr")
public class OcrImage {
    private String appId;
    private String secretId;
    private String secretKey;
    private ImageClient imageClient = null;

    public OcrImage(){}

    public String imageToText(String url) throws AbstractImageException {
        ImageClient client = getInstance(appId, secretId, secretKey);
        String resutl = client.generalOcr(new GeneralOcrRequest("ByURL", url));
        return resutl;
    }

    public String imageToText(File image) throws AbstractImageException {
        long start = System.currentTimeMillis();
        ImageClient client = getInstance(appId, secretId, secretKey);
        String resutl = client.generalOcr(new GeneralOcrRequest("ByFile", image));
        long end = System.currentTimeMillis();
        System.out.println("OCR识别文字时间："+(end-start));
        return resutl;
    }

    public ImageClient getInstance(String appId, String secretId, String secretKey) {
        if (imageClient == null) {
            imageClient = new ImageClient(appId, secretId, secretKey, ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
        }
        return imageClient;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
