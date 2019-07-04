package com.melink.advert.service;

import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;

import java.util.List;
import java.util.Map;

public interface AdvertService {

    Map<String,List<OpenEmoticion>> getAdvertByType(String type);

}
