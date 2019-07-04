package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.BasicNetPictureInfo;

import java.util.List;

public interface GifsImageService {
    BasicNetPictureInfo searchGifs(String guid);
    List<BasicNetPictureInfo> searchGifSList(List<String> guids);
}
