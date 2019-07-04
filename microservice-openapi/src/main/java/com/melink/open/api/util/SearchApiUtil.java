package com.melink.open.api.util;

import com.melink.open.api.model.*;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticonDetail;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by orlan on 2016/12/19.
 */
public class SearchApiUtil {
    public static OpenEmoticion convertNetEmoticion(BasicNetPicture basicEmo) {
        if (basicEmo == null) {
            return null;
        }
        OpenEmoticion emo = new OpenEmoticion();
        if(basicEmo.getName() == null){
            emo.setText("");
        }else{
            emo.setText(basicEmo.getName());
        }
        emo.setMain(basicEmo.getStoreUrl().replace("http://7xl6jm.com2.z0.glb.qiniucdn.com/",""));
        emo.setThumb(basicEmo.getStoreUrl().replace("http://7xl6jm.com2.z0.glb.qiniucdn.com/","") + "?imageMogr2/thumbnail/!80p/format/png");
        emo.setWidth(basicEmo.getWidth());
        emo.setHeight(basicEmo.getHeight());
        emo.setWeight(basicEmo.getWeight());
        emo.setFs(basicEmo.getFsize());
        emo.setIsAnimated(basicEmo.getDynamic() + "");
		emo.setClassify(basicEmo.getClassify() == null ? 0:basicEmo.getClassify());
        emo.setLevel(basicEmo.getLevel());
        emo.setKind(basicEmo.getKind());
        emo.setGuid(basicEmo.getGuid());
        return emo;
    }

    public static OpenEmoticonDetail convert(BasicNetPicture basicNetPicture, List<String> keywords, BasicNetPictureQuality basicNetPictureQuality, List<BasicNetPictureLossy> basicNetPictureLossies) {
        OpenEmoticonDetail emoticionDetail = new OpenEmoticonDetail();
        String less1MUrl = "";
        String less2MUrl = "";
        if(!CollectionUtils.isEmpty(basicNetPictureLossies)){
            for (BasicNetPictureLossy basicNetPictureLossy : basicNetPictureLossies) {
                if(basicNetPictureLossy.getType() == 1 && StringUtils.hasText(basicNetPictureLossy.getLossyUrl())){
                    less1MUrl = basicNetPictureLossy.getLossyUrl();
                }
                if(basicNetPictureLossy.getType() == 2 && StringUtils.hasText(basicNetPictureLossy.getLossyUrl())){
                    less2MUrl = basicNetPictureLossy.getLossyUrl();
                }
            }
        }
        emoticionDetail.setMain(basicNetPicture.getStoreUrl());
        emoticionDetail.setFs(basicNetPicture.getFsize());
        emoticionDetail.setHeight(basicNetPicture.getHeight());
        emoticionDetail.setWidth(basicNetPicture.getWidth());
        emoticionDetail.setLess1M(less1MUrl);
        emoticionDetail.setLess2M(less2MUrl);
        emoticionDetail.setMp4("");
        emoticionDetail.setKeywords(keywords);
        return emoticionDetail;
    }

    public static OpenEmoticion convert(BasicEmoticon basicEmo) {
        if (basicEmo == null) {
            return null;
        }
        OpenEmoticion emo = new OpenEmoticion();
        emo.setText(basicEmo.getEmoText());
        emo.setCode(basicEmo.getEmoCode());
        emo.setThumb(basicEmo.getThumbail());
        emo.setPackageId(basicEmo.getPackageId());
        emo.setTrendLock(basicEmo.getTrendLock());
        emo.setMd5(basicEmo.getMd5());
        emo.setFs(basicEmo.getFsize());
        BasicEmoticonPackage basicEmoticonPackage = basicEmo.getBasicEmoticonPackage();
        if (basicEmo.getIsEmoji()) {
            emo.setWidth(80);
            emo.setHeight(80);
        } else {
            emo.setWidth(240);
            emo.setHeight(240);
        }

        if (basicEmoticonPackage != null) {
            if(basicEmoticonPackage.getType().equals("dynamic")){
                emo.setIsAnimated("1");
                emo.setMain(basicEmo.getMainImage());
            }else{
                emo.setIsAnimated("0");
                emo.setMain(basicEmo.getMainImage()+"?imageMogr2/format/png");
            }

            emo.setCopyright(basicEmoticonPackage.getCopyright());
        }else{
            emo.setMain(basicEmo.getMainImage());
        }

        return emo;
    }

    public static OpenEmoticion convertAndMd5(BasicEmoticon basicEmo) {
        if (basicEmo == null) {
            return null;
        }
        URL url = null;
        String md5 = null;
        try {
            String content = "";
            URLConnection urlConnection = new URL(basicEmo.getMainImage() + "?hash/md5").openConnection();
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                content += line;
            }
            in.close();
            String[] split = content.split("\"");
            md5 = split[3];
        } catch (Exception e) {
            e.printStackTrace();
        }
        OpenEmoticion emo = new OpenEmoticion();
        emo.setText(basicEmo.getEmoText());
        emo.setCode(basicEmo.getEmoCode());
        emo.setThumb(basicEmo.getThumbail());
        emo.setPackageId(basicEmo.getPackageId());
        emo.setMd5(md5);
        emo.setTrendLock(basicEmo.getTrendLock());
        BasicEmoticonPackage basicEmoticonPackage = basicEmo.getBasicEmoticonPackage();
        if (basicEmo.getIsEmoji()) {
            emo.setWidth(80);
            emo.setHeight(80);
        } else {
            emo.setWidth(240);
            emo.setHeight(240);
        }

        if (basicEmoticonPackage != null) {
            if(basicEmoticonPackage.getType().equals("dynamic")){
                emo.setIsAnimated("1");
                emo.setMain(basicEmo.getMainImage());
            }else{
                emo.setIsAnimated("0");
                emo.setMain(basicEmo.getMainImage()+"?imageMogr2/format/png");
            }

            emo.setCopyright(basicEmoticonPackage.getCopyright());
        }else{
            emo.setMain(basicEmo.getMainImage());
        }

        return emo;
    }

    public static OpenEmoticion convert(TrendyEmoticon trendyEmoticon) {
        if (trendyEmoticon == null) {
            return null;
        }
        OpenEmoticion emo = new OpenEmoticion();
        if(StringUtils.hasText(trendyEmoticon.getNpId())){
            emo.setGuid(trendyEmoticon.getNpId());
        }else{
            emo.setGuid(trendyEmoticon.getGuid());
        }
        emo.setText(trendyEmoticon.getEmoText());
//        emo.setCode(trendyEmoticon.getEmoCode());
        emo.setFs(trendyEmoticon.getFsize());
        emo.setMain(trendyEmoticon.getMainImage());
        emo.setThumb(trendyEmoticon.getMainImage() + "?imageMogr2/thumbnail/!80p/format/png");
        if(trendyEmoticon.getThumb() != null){
            if(trendyEmoticon.getIsAnimated() == 1){
                emo.setGif_thumb(trendyEmoticon.getThumb());
            }
        }
//        emo.setPackageId(trendyEmoticon.getPackageId());
        emo.setTrendLock(trendyEmoticon.getTrendLock());
        emo.setHeight(trendyEmoticon.getHeight());
        emo.setWidth(trendyEmoticon.getWidth());
        emo.setMd5(trendyEmoticon.getMd5());
        emo.setIsAnimated(trendyEmoticon.getIsAnimated() + "");
        emo.setCopyright(trendyEmoticon.getCopyright());
        return emo;
    }

    public static OpenEmoticion convertNetEmoticionForQuality(BasicNetPictureQuality basicEmoticon, String hitKeyword) {
//        if (basicEmoticon == null) {
//            return null;
//        }
//        OpenEmoticion emo = new OpenEmoticion();
//        emo.setGuid(basicEmoticon.getnpId());
//        emo.setWidth(basicEmoticon.getWidth());
//        emo.setHeight(basicEmoticon.getHeight());
//        emo.setWeight(basicEmoticon.getWeight());
//        emo.setLevel(basicEmoticon.getLevel());
//        if(basicEmoticon == null){
//            emo.setText("");
//        }else{
//            emo.setText(basicEmoticon.getName());
//        }
//
//        if (StringUtils.hasText(basicEmoticon.getLossyUrl())) {
//            emo.setMain(basicEmoticon.getLossyUrl());
//            emo.setThumb(basicEmoticon.getLossyUrl() + "?imageMogr2/thumbnail/!80p/format/png");
//            emo.setFs(basicEmoticon.getLossyFsize());
//            emo.setMd5(basicEmoticon.getLossyMd5());
//        }else{
//            emo.setMain(basicEmoticon.getStoreUrl());
//            emo.setThumb(basicEmoticon.getStoreUrl() + "?imageMogr2/thumbnail/!80p/format/png");
//            emo.setFs(basicEmoticon.getFsize());
//            emo.setMd5(basicEmoticon.getMd5());
//        }
//        if (basicEmoticon.getStoreUrl().contains(".gif")) {
//            emo.setIsAnimated("1");
//        } else {
//            emo.setIsAnimated("0");
//        }
//        if(basicEmoticon.getThumb() == null){
//            emo.setGif_thumb(basicEmoticon.getLossyUrl());
//        }else{
//            emo.setGif_thumb(basicEmoticon.getThumb());
//        }
//
//        if(StringUtils.hasText(basicEmoticon.getWebp())){
//            emo.setWebp(basicEmoticon.getWebp());
//            emo.setWebpsize(basicEmoticon.getWebpsize());
//            emo.setWebpmd5(basicEmoticon.getWebpmd5());
//        }
//        emo.setClassify(basicEmoticon.getClassify() == null ? 0:basicEmoticon.getClassify());
//        emo.setKind(basicEmoticon.getKind());
        OpenEmoticion emo = convertNetEmoticionForQuality(basicEmoticon);
        if(StringUtils.hasText(basicEmoticon.getKeywords())){
            String[] split = basicEmoticon.getKeywords().split(",");
            List<String> stringList = new ArrayList<String>(Arrays.asList(split));
            if(!CollectionUtils.isEmpty(stringList)) {
                Iterator<String> iterator = stringList.iterator();
                while (iterator.hasNext()) {
                    String kw = iterator.next();
                    if (kw.contains("陌陌分类分类词") || kw.contains("流行表情时间关键词") || kw.contains("探探流行表情时间关键词")) {
                        iterator.remove();
                    }
                }
            }
            emo.setAllKeywords(stringList);
            ArrayList<String> arrayList;
            if(stringList.size() > 3) {
                arrayList = new ArrayList<String>(stringList.subList(0,3));
            }else{
                arrayList = new ArrayList<String>(stringList);
            }
            if(hitKeyword != null && !arrayList.contains(hitKeyword)) arrayList.add(0,hitKeyword);

            emo.setKeywords(arrayList);
        }
        return emo;
    }

    public static OpenEmoticion convertNetEmoticionForQuality(BasicNetPictureQuality basicEmoticon) {
        if (basicEmoticon == null) {
            return null;
        }
        OpenEmoticion emo = new OpenEmoticion();
        emo.setGuid(basicEmoticon.getnpId());
        emo.setWidth(basicEmoticon.getWidth());
        emo.setHeight(basicEmoticon.getHeight());
        if (basicEmoticon.getWeight() != null) {
            emo.setWeight(basicEmoticon.getWeight());
        }
        emo.setLevel(basicEmoticon.getLevel());
        if(basicEmoticon == null){
            emo.setText("");
        }else{
            emo.setText(basicEmoticon.getName());
        }

        if (StringUtils.hasText(basicEmoticon.getLossyUrl())) {
            emo.setMain(basicEmoticon.getLossyUrl());
            emo.setThumb(basicEmoticon.getLossyUrl() + "?imageMogr2/thumbnail/!80p/format/png");
            emo.setFs(basicEmoticon.getLossyFsize());
            emo.setMd5(basicEmoticon.getLossyMd5());
        }else{
            emo.setMain(basicEmoticon.getStoreUrl());
            emo.setThumb(basicEmoticon.getStoreUrl() + "?imageMogr2/thumbnail/!80p/format/png");
            emo.setFs(basicEmoticon.getFsize());
            emo.setMd5(basicEmoticon.getMd5());
        }
        if (basicEmoticon.getStoreUrl().contains(".gif")) {
            emo.setIsAnimated("1");
        } else {
            emo.setIsAnimated("0");
        }
        if(basicEmoticon.getThumb() == null){
            emo.setGif_thumb(basicEmoticon.getLossyUrl());
        }else{
            emo.setGif_thumb(basicEmoticon.getThumb());
        }

        if(StringUtils.hasText(basicEmoticon.getWebp())){
            emo.setWebp(basicEmoticon.getWebp());
            emo.setWebpsize(basicEmoticon.getWebpsize());
            emo.setWebpmd5(basicEmoticon.getWebpmd5());
        }
        emo.setClassify(basicEmoticon.getClassify() == null ? 0:basicEmoticon.getClassify());
        emo.setKind(basicEmoticon.getKind());
        return emo;
    }

    public static List<OpenEmoticion> setKeyWordWeight(List<OpenEmoticion> emoticions, NetKeyword keyword) {
        for (OpenEmoticion emoticion : emoticions) {
            emoticion.setKeyWordWeight(keyword.getWeight());
            emoticion.setHitKeyword(keyword.getText());
        }
        return emoticions;
    }

    public static List<OpenEmoticion> OwReturnValue(List<OpenEmoticion> list) {
        for (OpenEmoticion emoticion : list) {
            emoticion.setText(null);
            emoticion.setClassify(0);
            emoticion.setThumb(null);
            emoticion.setCode(null);
            emoticion.setCopyright(null);
            emoticion.setIsAnimated(null);
        }
        return list;
    }

    public static List<OpenEmoticion> trendReturnValue(List<OpenEmoticion> list) {
        for (OpenEmoticion emoticion : list) {
            emoticion.setCode(null);
            emoticion.setPackageId(null);
        }
        return list;
    }
}
