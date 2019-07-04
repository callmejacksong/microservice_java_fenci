package com.melink.open.api.service.impl;

import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.open.api.mapper.netPicMapper.BasicNetPictureMapper;
import com.melink.open.api.model.BasicNetPicture;
import com.melink.open.api.model.BasicNetPictureQuality;
import com.melink.open.api.service.GifsImageService;
import com.melink.sop.api.models.open.modelinfos.BasicNetPictureImage;
import com.melink.sop.api.models.open.modelinfos.BasicNetPictureInfo;
import com.melink.sop.api.models.open.modelinfos.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GifsImageServiceImpl implements GifsImageService {
    @Autowired
    private BasicNetPictureMapper basicNetPictureMapper;

    @Override
    public BasicNetPictureInfo searchGifs(String guid) {
        BasicNetPicture basicNetPicture = basicNetPictureMapper.findBasicNetPictureByGuid(guid);
        if(basicNetPicture == null){
            return null;
        }
        BasicNetPictureInfo info = new BasicNetPictureInfo();
        copyObject(basicNetPicture, info);
        return info;
    }

    @Override
    public List<BasicNetPictureInfo> searchGifSList(List<String> guids) {
        List<BasicNetPicture> bysicNetPicture = basicNetPictureMapper.findBysicNetPictureByGuids(guids);
        if (CollectionUtils.isEmpty(bysicNetPicture)) {
            return null;
        }
        List<BasicNetPictureInfo> models = new ArrayList<>();
        for (BasicNetPicture b : bysicNetPicture) {
            BasicNetPictureInfo basicNetPictureInfo = new BasicNetPictureInfo();
            copyObject(b, basicNetPictureInfo);
            models.add(basicNetPictureInfo);
        }
        return models;
    }

    private void copyObject(BasicNetPicture b, BasicNetPictureInfo info){
        BeanCoperUtils copyUtil = BeanCoperUtils.getInstance();
        copyUtil.copy(b,info);
        info.setAnimate(b.getDynamic());
        info.setText(b.getNetPictureTextInfo().getTextinfo());
        info.setCreateTime(b.getCreatetime());
        ImageInfo imageInfo = new ImageInfo();
        List<BasicNetPictureQuality> qualitys = b.getBasicNetPictureQualities();
        for (BasicNetPictureQuality q : qualitys) {
            BasicNetPictureImage image = new BasicNetPictureImage();
            copyUtil.copy(q,image);
            image.setUrl(q.getStoreUrl());
            if (q.getLossyUrl() != null) {
                image.setUrl(q.getLossyUrl());
            }
            if (q.getQuality() == (1)) {
                imageInfo.setLarge(image);
            }
            if (q.getQuality() == 2) {
                imageInfo.setMedium(image);
            }
            if (q.getQuality() == 3) {
                imageInfo.setSmall(image);
            }
        }
        BasicNetPictureImage image = new BasicNetPictureImage();
        copyUtil.copy(b,image);
        image.setUrl(b.getStoreUrl());
        imageInfo.setOriginal(image);
//        HttpsResConvertUtils.GifsReplaceUrl(image, sslRes, appId);
        info.setImage(imageInfo);
    }
}
