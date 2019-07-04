package com.melink.advert.api;

import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AdvertApi {

    @GetMapping("obtain/{type}")
    List<OpenEmoticion> getAdvertByType(@PathVariable("type") String type);
}
