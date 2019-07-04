package com.melink.open.api.service;

import com.melink.open.api.vo.SearchVO;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;

import javax.validation.Valid;
import java.util.List;

public interface StickerSearchService {

    List<OpenEmoticion> searchEmoji(String timestamp, String signature, boolean sslRes, @Valid SearchVO vo);

}
