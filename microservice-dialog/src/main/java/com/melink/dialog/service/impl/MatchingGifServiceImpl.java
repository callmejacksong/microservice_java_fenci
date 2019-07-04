package com.melink.dialog.service.impl;

import com.melink.dialog.mapper.BasicNetPictureMapper;
import com.melink.dialog.service.MatchingGifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingGifServiceImpl implements MatchingGifService {

    @Autowired
    private BasicNetPictureMapper basicNetPictureMapper;
    @Override
    public String getURLByGuid(String guid) {

        String url = basicNetPictureMapper.getURLbyGuid(guid);
        return url;
    }
}
