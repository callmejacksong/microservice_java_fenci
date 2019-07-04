package com.melink.open.api.controller.website;

import com.melink.open.api.controller.AbstractDataController;
import com.melink.open.api.model.BasicNetPicture;
import com.melink.open.api.service.GifDetailService;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticonDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gif**")
public class GifDatailDataController extends AbstractDataController<OpenEmoticonDetail> {
    @Autowired
    private GifDetailService gifDetailService;

    @GetMapping("detail/{id}")
    public OpenApiV2DataResponse<OpenEmoticonDetail> home(@PathVariable("id") final String id,
                                                          @RequestParam(value = "is_mobile", required = false, defaultValue = "false") final boolean isMobiie) {
        return executeApiCall(() -> {

            BasicNetPicture basicNetPicture = gifDetailService.findBasicNetPictureById(id);
            if(basicNetPicture == null){
                return errorResponse("Gif Not Exist");
            }
            OpenEmoticonDetail openEmoticonDetail = gifDetailService.getOpenEmoticonDetail(basicNetPicture, id, isMobiie);
            if (openEmoticonDetail == null) {
                return errorResponse("Gif Not Exist");
            }
            return successResponse(openEmoticonDetail);
        });

    }

}
