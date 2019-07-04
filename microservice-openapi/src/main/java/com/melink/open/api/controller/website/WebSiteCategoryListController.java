package com.melink.open.api.controller.website;

import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.service.WebSiteCategoryService;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.WebsiteCategoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/website**")
public class WebSiteCategoryListController extends AbstractListController<WebsiteCategoryInfo> {

    @Autowired
    private WebSiteCategoryService webSiteCategoryService;

    @GetMapping("/category")
    public OpenApiV2ListResponse<WebsiteCategoryInfo> category(){
        return executeApiCall(() -> {
            List<WebsiteCategoryInfo> result = webSiteCategoryService.getWebsiteCategoryInfo();
            return successResponse(result);
        });
    }
}
