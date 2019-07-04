package com.melink.open.api.controller.website;

import com.melink.microservice.utils.MailUtil;
import com.melink.open.api.constant.WebSiteConsts;
import com.melink.open.api.controller.AbstractDataController;
import com.melink.open.api.model.IpAccount;
import com.melink.open.api.service.IpAccountService;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ipaccount")
public class IpAccountDataController extends AbstractDataController<AccountInfo> {

    @Autowired
    private IpAccountService ipAccountService;


    @GetMapping("/register")
    public OpenApiV2DataResponse<AccountInfo> register(
            @RequestParam(value = "name", required = false, defaultValue = "1") final String name,
            @RequestParam(value = "link", required = false, defaultValue = "1") final String link,
            @RequestParam(value = "registrant_email", required = false, defaultValue = "1") final String registrantEmail,
            @RequestParam(value = "registrant_name", required = false, defaultValue = "1") final String registrantName,
            @RequestParam(value = "telephone", required = false, defaultValue = "1") final String telephone,
            @RequestParam(value = "company", required = false, defaultValue = "1") final String company,
            @RequestParam(value = "description", required = false, defaultValue = "1") final String description
    ) {
        return executeApiCall(() -> {
            MailUtil mailUtil = MailUtil.newInstance();
            mailUtil.sendIpAccountRegister("lin.zhao@dongtu.com", name, WebSiteConsts.DONGTU_IP_DOMAIN + link, registrantEmail, telephone, company, description, registrantName);
            return successResponse(new AccountInfo());
        });
    }

    @GetMapping("/check")
    public OpenApiV2DataResponse<AccountInfo> register(@RequestParam(value = "code") final String linkCode) {
        return executeApiCall(() -> {
            IpAccount account = ipAccountService.findByInnerLinkCode(linkCode);
            if (account == null) {
                return successResponse(new AccountInfo());
            } else {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "ip inner link exists");
            }
        });
    }

    @GetMapping("/detail")
    public OpenApiV2DataResponse<AccountInfo> list(@RequestParam(value = "code") final String linkCode) {
        return executeApiCall(() -> {
            IpAccount account = ipAccountService.findByInnerLinkCode(linkCode);
            if (account == null) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "account not exists!");
            }
            AccountInfo accountInfo = ipAccountService.getAccountAddEmoticion(account);
            if (accountInfo == null) {
                return errorResponse(ErrorCode._INVALID_PARAMETER,"accountInfo not exists!");
            }
            return successResponse(accountInfo);
        });
    }

    @GetMapping("/search")
    public OpenApiV2DataResponse<AccountInfo> search(@RequestParam(value = "q") final String q) {
        return executeApiCall(() -> {
            IpAccount account = ipAccountService.findAccountByName(q);
            if (account == null) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "account not exists!");
            }
            AccountInfo accountInfo = ipAccountService.getAccountSearch(account, 1, 60);
            if (accountInfo == null) {
                return errorResponse(ErrorCode._INVALID_PARAMETER,"accountInfo not exists!");
            }
            return successResponse(accountInfo);
        });
    }
}
