package com.melink.open.api.controller;

import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.model.BasicEmoticon;
import com.melink.open.api.model.BasicEmoticonPackage;
import com.melink.open.api.service.BasicEmoticonPackageService;
import com.melink.open.api.util.HttpsMelinkResConvertUtils;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.Emoticon;
import com.melink.sop.api.models.open.modelinfos.EmoticonPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/packs")
@RestController
public class PackageDataController extends AbstractDataController<EmoticonPackage> {

    @Autowired
    private BasicEmoticonPackageService basicEmoticonpackageService;

    @RequestMapping("/{id}")
    @NewRequiredAuth
    public OpenApiV2DataResponse<EmoticonPackage> searchPackageById(
            @PathVariable("id") final String id,
            @RequestParam("timestamp") final String timestamp,
            @RequestParam("signature") final String signature,
            @RequestParam("app_id") final String appId,
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes
            ) {
        return executeApiCall(() -> {
            BasicEmoticonPackage basicEmoticonpackage = basicEmoticonpackageService.findbyId(id);
            if(basicEmoticonpackage == null){
                return errorResponse(ErrorCode._INVALID_PARAMETER,"not exist package");
            }
            EmoticonPackage emoticonPackage = new EmoticonPackage();
            BeanCoperUtils.getInstance().copy(basicEmoticonpackage,emoticonPackage);
            List<Emoticon> emoticonList = new ArrayList<Emoticon>();
            if(!CollectionUtils.isEmpty(basicEmoticonpackage.getBasicEmoticons())){
                for (BasicEmoticon basicEmoticon : basicEmoticonpackage.getBasicEmoticons()) {
                    Emoticon emoticon = new Emoticon();
                    BeanCoperUtils.getInstance().copy(basicEmoticon,emoticon);
                    emoticonList.add(emoticon);
                }
            }
            emoticonPackage.setEmoticons(emoticonList);
            return successResponse(HttpsMelinkResConvertUtils.packageConvert(emoticonPackage,sslRes));
        });

    }
}
