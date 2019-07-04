package com.melink.open.api.service;

import com.melink.open.api.vo.ValidaterVO;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;

import javax.validation.Valid;
import java.util.List;

public interface TrendingService {

    List<OpenEmoticion> newTrendy(String timestamp, String signature, String appId, boolean sslRes, @Valid ValidaterVO vo);

    List<OpenEmoticion> textInfoTrending(boolean hasTextinfo);
}
