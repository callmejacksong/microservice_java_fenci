package com.melink.open.api.controller.website;

import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.service.HotNetkeyWordService;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.HotNetKeywordInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 官网热门词
 */
@RequestMapping("")
@RestController
public class HotNetKeywordController extends AbstractListController<HotNetKeywordInfo> {

	@Autowired
	private HotNetkeyWordService hotNetKeyWordService;

	@RequestMapping("/hot/keyword")
	public OpenApiV2ListResponse<HotNetKeywordInfo> hot(@RequestParam("timestamp") final String timestamp,
													@RequestParam("signature") final String signature,
													@RequestParam("app_id") final String appId,
													@RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
													@RequestParam(value = "mutli_emo", required = false, defaultValue = "false") final boolean mutliEmo
													) {
		return executeApiCall(new ApiCallback<HotNetKeywordInfo>() {
			@Override
			public OpenApiV2ListResponse<HotNetKeywordInfo> call() {
				List<HotNetKeywordInfo> list = hotNetKeyWordService.hot(timestamp,signature,appId,sslRes,mutliEmo);

				return successResponse(list);

			}
		});
	}


}