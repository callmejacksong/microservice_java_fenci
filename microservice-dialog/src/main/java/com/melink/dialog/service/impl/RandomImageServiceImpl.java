package com.melink.dialog.service.impl;

import com.melink.dialog.constant.DialogImageTypeConstant;
import com.melink.dialog.mapper.BasicNetPictureMapper;
import com.melink.dialog.model.BasicNetPictureQuality;
import com.melink.dialog.service.RandomImageService;
import com.melink.sop.api.models.dialog.DialogImageInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;
@Service
public class RandomImageServiceImpl implements RandomImageService {

    @Autowired
    private BasicNetPictureMapper basicNetPictureMapper;

    @Override
    public DialogImageInfo getEmoticonByClassify() {
        List<BasicNetPictureQuality> emoticonByClassify = basicNetPictureMapper.getEmoticonByClassify();
        Random r = new Random();
        int i = r.nextInt(emoticonByClassify.size());
        BasicNetPictureQuality basicNetPicture = emoticonByClassify.get(i);
        OpenEmoticion openEmoticion = convertNetEmoticionForQuality(basicNetPicture);
        DialogImageInfo imageInfo = new DialogImageInfo();
        imageInfo.setType(DialogImageTypeConstant.IMAGE_TYPE_BY_RANDOM);
        imageInfo.setUrl(openEmoticion.getMain());
        imageInfo.setRobotReq(openEmoticion.getText());
        return imageInfo;
    }


    public static OpenEmoticion convertNetEmoticionForQuality(BasicNetPictureQuality basicEmoticon) {
        if (basicEmoticon == null) {
            return null;
        }
        OpenEmoticion emo = new OpenEmoticion();
        emo.setGuid(basicEmoticon.getnpId());
        emo.setWidth(basicEmoticon.getWidth());
        emo.setHeight(basicEmoticon.getHeight());
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
            emo.setMain(basicEmoticon.getStoreUrl());//没加上
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
}
