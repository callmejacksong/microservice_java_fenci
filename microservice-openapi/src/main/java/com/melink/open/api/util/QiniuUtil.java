package com.melink.open.api.util;

import com.melink.open.api.model.util.QiniuConfig;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("qiniuUtil")
public class QiniuUtil {

    @Autowired
    private QiniuConfig qiniuConfig;

    /**
     *
     * @param fileKey 原文件key
     * @param targetFileKey 持久化新文件key
     * @param opfs   持久化处理参数
     */
    public void persistence(String fileKey,String targetFileKey,String opfs) {
        String bucket = qiniuConfig.getBucket();
        String accessKey = qiniuConfig.getAccessKey();
        String secret = qiniuConfig.getSecretKey();
        Auth auth = Auth.create(accessKey, secret);
        //数据处理指令，支持多个指令
        String saveEntry = String.format("%s:%s", bucket,targetFileKey);
        String persistentOpfs = String.format("%s|saveas/%s", opfs, UrlSafeBase64.encodeToString(saveEntry));
        //数据处理队列名称，必须
        String persistentPipeline = "test123";

        Configuration cfg = new Configuration(Zone.zone0());

        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth,cfg);
        try {
            String persistentId = operationManager.pfop(bucket, fileKey, persistentOpfs, persistentPipeline, "", true);
            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }
    }

    /**
     * 加水印
     * @param fileKey
     * @param targetFileKey
     * @param waterFileKey
     */
    public void watermark(String fileKey,String targetFileKey,String waterFileKey){
        String waterOpfs = String.format("watermark/1/image/%s/gravity/NorthWest",UrlSafeBase64.encodeToString(waterFileKey));
        persistence(fileKey,targetFileKey,waterOpfs);
    }
}
