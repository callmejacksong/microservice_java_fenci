package com.melink.open.api.util;

import com.melink.open.api.constant.AppConsts;
import com.melink.sop.api.models.QiNiuBucket;
import com.melink.sop.api.models.open.modelinfos.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public class HttpsResConvertUtils {

    public static OpenEmoticion emoticionConvert(OpenEmoticion emoticion, boolean sslRes) {
        if (emoticion == null) {
            return emoticion;
        }
        if (sslRes) {
            if (emoticion.getMain().contains(QiNiuBucket.BQMM_BUCKET)) {
                emoticion.setMain(emoticion.getMain().replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
                emoticion.setThumb(emoticion.getThumb().replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
            } else {
                emoticion.setMain(QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET + emoticion.getMain());
                emoticion.setThumb(QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET + emoticion.getThumb());
            }
        } else {
            if (!emoticion.getMain().contains(QiNiuBucket.BQMM_BUCKET)) {
                emoticion.setMain(QiNiuBucket.BQMM_SOSO_BUCKET + emoticion.getMain());
                emoticion.setThumb(QiNiuBucket.BQMM_SOSO_BUCKET + emoticion.getThumb());
            }
        }
        return emoticion;
    }

    public static OpenEmoticionShort emoticionConvert(OpenEmoticionShort emoticion, boolean sslRes) {
        if (!sslRes || emoticion == null) {
            return emoticion;
        }
        emoticion.setSslRes(sslRes);
        return emoticion;
    }

    public static List<OpenEmoticion> listEmoticionConvert(List<OpenEmoticion> emoticions, boolean sslRes) {
        if (CollectionUtils.isEmpty(emoticions)) {
            return emoticions;
        }
        listEmoticionReplaceUrl(emoticions, sslRes, QiNiuBucket.BQMM_SOSO_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET);
        return emoticions;
    }


    private static List<OpenEmoticion> listEmoticionReplaceUrl(List<OpenEmoticion> emoticions, boolean sslRes, String bucket, String httpsBucket) {
        for (OpenEmoticion emoticion : emoticions) {
            if (sslRes) {
                if (emoticion.getMain().contains(QiNiuBucket.BQMM_BUCKET)) {
                    emoticion.setMain(emoticion.getMain().replace(QiNiuBucket.BQMM_BUCKET, httpsBucket));
                    emoticion.setThumb(emoticion.getThumb().replace(QiNiuBucket.BQMM_BUCKET, httpsBucket));
                } else {
                    emoticion.setMain(httpsBucket + emoticion.getMain());
                    emoticion.setThumb(httpsBucket + emoticion.getThumb());
                }

                if (emoticion.getGif_thumb() != null) {
                    emoticion.setGif_thumb(httpsBucket + emoticion.getGif_thumb());
                }
                if (emoticion.getWebp() != null) {
                    emoticion.setWebp(httpsBucket + emoticion.getWebp());
                }
            } else {
                if (emoticion.getMain().contains(QiNiuBucket.BQMM_BUCKET)) {
                    if (!emoticion.getMain().contains(bucket)) {
                        emoticion.setMain(emoticion.getMain().replace(QiNiuBucket.BQMM_BUCKET, bucket));
                        emoticion.setThumb(emoticion.getThumb().replace(QiNiuBucket.BQMM_BUCKET, bucket));
                    }
                } else {
                    emoticion.setMain(bucket + emoticion.getMain());
                    emoticion.setThumb(bucket + emoticion.getThumb());
                }

                if (emoticion.getGif_thumb() != null) {
                    emoticion.setGif_thumb(bucket + emoticion.getGif_thumb());
                }
                if (emoticion.getWebp() != null) {
                    emoticion.setWebp(bucket + emoticion.getWebp());
                }
            }
        }
        return emoticions;
    }

    public static OpenEmoticionPackage packageCovert(OpenEmoticionPackage emoticionPackage, boolean sslRes) {
        if (!sslRes || emoticionPackage == null) {
            return emoticionPackage;
        }
        emoticionPackage.setSslRes(sslRes);
        if (CollectionUtils.isEmpty(emoticionPackage.getEmojis())) {
            return emoticionPackage;
        }
        for (OpenEmoticionShort emoji : emoticionPackage.getEmojis()) {
            emoticionConvert(emoji, sslRes);
        }
        return emoticionPackage;
    }

    public static List<OpenEmoticionPackage> listPackageConvert(List<OpenEmoticionPackage> emoticionPackages, boolean sslRes) {
        if (!sslRes || emoticionPackages == null || CollectionUtils.isEmpty(emoticionPackages)) {
            return emoticionPackages;
        }
        for (OpenEmoticionPackage emoticionPackage : emoticionPackages) {
            packageCovert(emoticionPackage, sslRes);
        }
        return emoticionPackages;
    }


    public static List<OpenEmoticonZip> listEmoticonZipCover(List<OpenEmoticonZip> openZips, String app_id) {
        if (CollectionUtils.isEmpty(openZips)) {
            return openZips;
        }
        for (OpenEmoticonZip openZip : openZips) {
            if (app_id.equals(AppConsts.WANGWANG)) {
                emoticonZipCover(openZip, QiNiuBucket.BQMM_WANGWANG_HTTPS_BUCKET);
            }
        }
        return openZips;
    }

    private static void emoticonZipCover(OpenEmoticonZip openZip, String bqmmWangwangHttpsBucket) {
        openZip.setBanner(openZip.getBanner().replace(QiNiuBucket.BQMM_BUCKET, bqmmWangwangHttpsBucket));
        openZip.setChatIcon(openZip.getChatIcon().replace(QiNiuBucket.BQMM_BUCKET, bqmmWangwangHttpsBucket));
        openZip.setCover(openZip.getCover().replace(QiNiuBucket.BQMM_BUCKET, bqmmWangwangHttpsBucket));
        openZip.setZip(openZip.getZip().replace(QiNiuBucket.BQMM_BUCKET, bqmmWangwangHttpsBucket));
    }

    public static void IpResourceConvert(AccountInfo accountInfo) {
        if (StringUtils.hasText(accountInfo.getIcon()))
            accountInfo.setIcon(accountInfo.getIcon().replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
        if (StringUtils.hasText(accountInfo.getBanner()))
            accountInfo.setBanner(accountInfo.getBanner().replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
        if (StringUtils.hasText(accountInfo.getMp4()))
            accountInfo.setMp4(accountInfo.getMp4().replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
        if (StringUtils.hasText(accountInfo.getMiniBanner()))
            accountInfo.setMiniBanner(accountInfo.getMiniBanner().replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
    }

    public static OpenEmoticonDetail gifComvert(OpenEmoticonDetail emoticionDetail) {
        emoticionDetail.setMain(emoticionDetail.getMain().replace(QiNiuBucket.BQMM_BUCKET,QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
        if (StringUtils.hasText(emoticionDetail.getMp4()))
            emoticionDetail.setMp4(QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET + emoticionDetail.getMp4());
        if(StringUtils.hasText(emoticionDetail.getLess1M()))
            emoticionDetail.setLess1M(QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET + emoticionDetail.getLess1M());
        if(StringUtils.hasText(emoticionDetail.getLess2M()))
            emoticionDetail.setLess2M(QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET + emoticionDetail.getLess2M());
        return emoticionDetail;
    }
}