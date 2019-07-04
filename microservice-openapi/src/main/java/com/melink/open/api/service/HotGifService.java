package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;

import java.util.List;

public interface HotGifService {
    List<OpenEmoticion> getHotOpenEmoticion(String p, String size);
}
