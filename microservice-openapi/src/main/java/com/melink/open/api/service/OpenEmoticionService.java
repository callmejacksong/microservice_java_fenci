package com.melink.open.api.service;


import com.melink.open.api.vo.SearchVO;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

public interface OpenEmoticionService {

	String newSearchEmoji(String appId, boolean sslRes, String partner, @Valid SearchVO vo, BindingResult result);
	String newSearchEmoji_2(String appId, boolean sslRes, String partner, @Valid SearchVO vo, BindingResult result);
	String newSearchEmoji_3(String appId, boolean sslRes, String partner, @Valid SearchVO vo, BindingResult result);
	String newSearchEmoji_4(String appId, boolean sslRes, String partner, @Valid SearchVO vo, BindingResult result);

    List<OpenEmoticion> getMutliSearchResult(@Valid SearchVO vo, boolean b, List<String> newList);

    List<OpenEmoticion> newGetSearchResult(@Valid SearchVO vo, List<String> allKeyword, boolean allowCopyRight);

    List<OpenEmoticion> onlySearch(String q);

    List<OpenEmoticion> likeSearch(String q);
}
