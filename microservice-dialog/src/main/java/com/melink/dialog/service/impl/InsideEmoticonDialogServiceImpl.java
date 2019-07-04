package com.melink.dialog.service.impl;

import com.melink.dialog.mapper.BasicNetPictureTextInfoMapper;
import com.melink.dialog.model.BasicNetPictureTextInfo;
import com.melink.dialog.service.InsideEmoticonDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsideEmoticonDialogServiceImpl implements InsideEmoticonDialogService {


    @Autowired
    private BasicNetPictureTextInfoMapper basicNetPictureTextInfoMapper;

    @Override
    public String getEmoticonTextById(String guid) {
        BasicNetPictureTextInfo textinfo = basicNetPictureTextInfoMapper.findTextInfoBySearch(guid);
        if (textinfo != null) {
            return textinfo.getTextinfo();
        }
        return null;
    }




}
