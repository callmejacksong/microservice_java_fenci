package com.melink.open.api.controller.website;

import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.service.FriendlyLinkService;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.FriendlyLinkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * 官网首页导航和分类
 */
@RestController
@RequestMapping("/friendlylink")
public class FriendlyLinkListController extends AbstractListController<FriendlyLinkInfo> {

    @Autowired
    private FriendlyLinkService friendlyLinkService;

    @GetMapping("")
    public OpenApiV2ListResponse<FriendlyLinkInfo> list(){
        return executeApiCall(() -> {

            List<FriendlyLinkInfo> list = friendlyLinkService.getFriendlyLinkInfoList();

            if (CollectionUtils.isEmpty(list)) {
                return errorResponse("Cannot find FriendlyLink");
            }
            return successResponse(list);
        });
    }

}
