package com.melink.open.api.service;

import com.melink.open.api.model.BasicNetPicture;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticonDetail;

public interface GifDetailService {
    BasicNetPicture findBasicNetPictureById(String guid);

    OpenEmoticonDetail getOpenEmoticonDetail(BasicNetPicture basicNetPicture, String id, boolean isMobiie);

}
