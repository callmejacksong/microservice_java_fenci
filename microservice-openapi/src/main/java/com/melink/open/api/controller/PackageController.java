package com.melink.open.api.controller;

import com.github.pagehelper.Page;
import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.microservice.utils.PlatformUtils;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.model.BasicEmoticonPackage;
import com.melink.open.api.service.BasicEmoticonPackageService;
import com.melink.open.api.util.HttpsMelinkResConvertUtils;
import com.melink.open.api.vo.PackageSearchVO;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.EmoticonPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;


@RequestMapping("/packs")
@RestController
public class PackageController extends AbstractListController<EmoticonPackage> {

    @Autowired
    private BasicEmoticonPackageService basicEmoticonpackageService;

    @RequestMapping("")
    @NewRequiredAuth
    public OpenApiV2ListResponse<EmoticonPackage> searchPackage(@RequestParam("timestamp") final String timestamp,
                                                                @RequestParam("signature") final String signature,
                                                                @RequestParam("app_id") final String appId,
                                                                @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                                                @Valid final PackageSearchVO vo, final BindingResult result) {

        return executeApiCall(() -> {
            if (result.hasErrors()) {
                FieldError fieldError = result.getFieldError();
                return errorResponse(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
            }
            Page<BasicEmoticonPackage> listByPagination = basicEmoticonpackageService.findListByPagination(PlatformUtils.toIntSafely(vo.getP(), 0), PlatformUtils.toIntSafely(vo.getSize(), 20));
            ArrayList<EmoticonPackage> emoticonPackages = new ArrayList<>();
            if (!CollectionUtils.isEmpty(listByPagination)) {
                for (BasicEmoticonPackage basicEmoticonPackage : listByPagination) {
                    EmoticonPackage emoticonPackage = new EmoticonPackage();
                    BeanCoperUtils.getInstance().copy(basicEmoticonPackage, emoticonPackage);
                    emoticonPackages.add(emoticonPackage);
                }
            }
            OpenApiPagination pagination = generatePagination((int) listByPagination.getTotal(), emoticonPackages.size(), PlatformUtils.toIntSafely(vo.getP(), 1), PlatformUtils.toIntSafely(vo.getSize(), 20));
            return successResponse(HttpsMelinkResConvertUtils.listPackageConvert(emoticonPackages,sslRes),pagination);
        });

    }
}
