package com.melink.dialog.controller.matchingGIF;

import com.aliyuncs.utils.StringUtils;
import com.melink.dialog.controller.AbstractListController;
import com.melink.dialog.service.SearchGifService;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.dialog.mathingGIF.MathingGifsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class SearchGifController extends AbstractListController<MathingGifsInfo> {

    @Autowired
    private SearchGifService searchGifService;
    @Autowired
    private ResultConverUtil resultConverUtil;

    @GetMapping("shenpeitu")
    public OpenApiV2ListResponse<MathingGifsInfo> searchGifs(
            @RequestParam(value = "q", required = false, defaultValue = "") String q
    ) {
        return executeApiCall(() -> {
            List<MathingGifsInfo> result = null;
            if (StringUtils.isEmpty(q)) {
                result = searchGifService.searchGifs();
                return successResponse(result);
            } else {
                result = searchGifService.searchGifsByText(q);

            }
            ResultConverUtil.Convert convert = resultConverUtil.ConvertByAppId(true, "");
            for (MathingGifsInfo info : result) {
                try {
                    convert.converURL(info, "url");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            return successResponse(result,generatePagination(result.size(),0,0,result.size()));
        });

    }
}
