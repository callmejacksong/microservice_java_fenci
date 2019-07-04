package com.melink.open.api.controller.website;

import com.melink.microservice.utils.Tools;
import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.model.IpAccount;
import com.melink.open.api.service.IpAccountService;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ipaccount")
public class IpAccountListController extends AbstractListController<AccountInfo> {
    @Autowired
    private IpAccountService ipAccountService;

    @GetMapping("/list")
    public OpenApiV2ListResponse<AccountInfo> list(@RequestParam(value = "p", required = false, defaultValue = "1") final Integer page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "20") final Integer size){
        return executeApiCall(() -> {
            List<IpAccount> ipAccounts = ipAccountService.getIpAccount();
            if (CollectionUtils.isEmpty(ipAccounts)) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "account not exists!");
            }

            List<IpAccount> list = (List<IpAccount>)Tools.subList(ipAccounts, size, page);
            OpenApiPagination openApiPagination = generatePagination(ipAccounts.size(), list.size(), page, size);

            List<AccountInfo> accountInfos = ipAccountService.ipAccountToAccountInfo(list);
            if (CollectionUtils.isEmpty(accountInfos)) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "accountInfo not exists!");
            }
            return successResponse(accountInfos, openApiPagination);
        });
    }


}

