package com.melink.open.api.controller.apiplus;

import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.feignClient.PicturePromotionClient;
import com.melink.open.api.model.OpenApp;
import com.melink.open.api.service.OpenAppService;
import com.melink.open.api.service.PlusEmoticionPromotionService;
import com.melink.open.api.vo.ApiPlusVO;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.forms.BatchQueryRequest;
import com.melink.sop.api.models.open.modelinfos.EmoticionPromotion;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/emoji")
@RestController
public class PlusEmoticionPromotionController extends AbstractListController<EmoticionPromotion> {

	@Autowired
	private PlusEmoticionPromotionService plusEmoticionPromotionService;
	@Autowired
	private OpenAppService	openAppService;
	@Autowired
	private PicturePromotionClient picturePromotionClient;

	@RequestMapping("/promotion/codes")
	@NewRequiredAuth
	public OpenApiV2ListResponse<EmoticionPromotion> detailPromotionByCodes(
			@RequestBody final BatchQueryRequest queryReq,
			@RequestParam("app_id") String appId,
			@RequestParam(value = "ssl_res",required = false,defaultValue="false") final boolean sslRes,
			final ApiPlusVO apiPlusVO
			) {

		return executeApiCall(()->{
				OpenApp promotionSwitch = openAppService.getAppByCache(appId);

				if (BooleanUtils.isFalse(promotionSwitch.getPromotionSwitch()) && BooleanUtils.isFalse(promotionSwitch.getGlobalPromotionSwitch())) {
					return successResponse(new ArrayList<>());
				}
				List<PicturePromotionInfo> picturePromotion = picturePromotionClient.getPicturePromotion(appId, queryReq.getCodes(), promotionSwitch.getPromotionSwitch(), promotionSwitch.getGlobalPromotionSwitch());
				if (!CollectionUtils.isEmpty(picturePromotion)) {
					List<EmoticionPromotion> result = plusEmoticionPromotionService.getPromotionByInfo(sslRes,picturePromotion);
					if (!CollectionUtils.isEmpty(result)) {
						return successResponse(result);
					}
				}
				return successResponse(new ArrayList<>());
		});
	}
}
