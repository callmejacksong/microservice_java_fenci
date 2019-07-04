package com.melink.open.api.service.impl;

import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.constant.WebSiteConsts;
import com.melink.open.api.mapper.netPicMapper.BasicNetPictureQualityMapper;
import com.melink.open.api.mapper.netPicMapper.IpAccountMapper;
import com.melink.open.api.model.BasicNetPictureQuality;
import com.melink.open.api.model.IpAccount;
import com.melink.open.api.model.IpAccountActivity;
import com.melink.open.api.service.IpAccountService;
import com.melink.open.api.util.HttpsResConvertUtils;
import com.melink.sop.api.constant.ResultConvertUrlConstant.OpenEmoticionUrlConstant;
import com.melink.sop.api.models.open.modelinfos.AccountInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.melink.open.api.util.SearchApiUtil.convertNetEmoticionForQuality;

@Service
public class IpAccountServiceImpl implements IpAccountService {
    private static final Logger log = LoggerFactory.getLogger(IpAccountServiceImpl.class);

    @Autowired
    private IpAccountMapper ipAccountMapper;
    @Autowired
    private BasicNetPictureQualityMapper basicNetPictureQualityMapper;
    @Autowired
    private ResultConverUtil resultConverUtil;

    @Override
    public List<IpAccount> getIpAccount() {
        List<IpAccount> ipAccountList = ipAccountMapper.findAll();
        return ipAccountList;
    }

    public List<AccountInfo> ipAccountToAccountInfo(List<IpAccount> ipAccounts) {
        if (CollectionUtils.isEmpty(ipAccounts)) {
            return null;
        }
        List<AccountInfo> accountInfoList = new ArrayList<>();
        for (IpAccount account : ipAccounts) {
            AccountInfo accountInfo = new AccountInfo();

            BeanCoperUtils.getInstance().copy(account, accountInfo);
            accountInfo.setInnerLink(WebSiteConsts.DONGTU_IP_DOMAIN + accountInfo.getInnerLink());

            List<BasicNetPictureQuality> basicNetPictureQualities = basicNetPictureQualityMapper.searchNetPictureQualityForIpAccount(account.getId(), 1, 1);
            List<OpenEmoticion> emoticions = getEmoticionsByQualitys(basicNetPictureQualities);
            if (!CollectionUtils.isEmpty(emoticions)) {
                accountInfo.setFirstPictureUrl(emoticions.get(0).getMain());
            }
            accountInfoList.add(accountInfo);
        }
        return accountInfoList;
    }

    @Override
    public IpAccount findByInnerLinkCode(String linkCode) {
        return ipAccountMapper.findByInnerLinkCode(linkCode);
    }

    @Override
    public AccountInfo getAccountAddEmoticion(IpAccount account) {
        if (account == null) {
            return null;
        }
        //
        List<BasicNetPictureQuality> basicNetPictureQualities = basicNetPictureQualityMapper.searchNetPictureQualityForIpAccount(account.getId(), 1, 100);
        List<OpenEmoticion> emoticions = getEmoticionsByQualitys(basicNetPictureQualities);
        if (CollectionUtils.isEmpty(emoticions)) {
            return null;
        }
        AccountInfo accountInfo = new AccountInfo();
        BeanCoperUtils.getInstance().copy(account, accountInfo);
        HttpsResConvertUtils.IpResourceConvert(accountInfo);
        accountInfo.setEmoticionList(emoticions);
        accountInfo.setInnerLink(WebSiteConsts.DONGTU_IP_DOMAIN + accountInfo.getInnerLink());
        return accountInfo;
    }

    @Override
    public AccountInfo getAccountSearch(IpAccount account, int p, int size) {
        if (account == null) {
            return null;
        }
        List<BasicNetPictureQuality> basicNetPictureQualities = basicNetPictureQualityMapper.searchNetPictureQualityForIpAccount(account.getId(), p, size);
        List<OpenEmoticion> emoticions = getEmoticionsByQualitys(basicNetPictureQualities);
        if (CollectionUtils.isEmpty(emoticions)) {
            return null;
        }
        AccountInfo accountInfo = new AccountInfo();
        BeanCoperUtils.getInstance().copy(account, accountInfo);
        HttpsResConvertUtils.IpResourceConvert(accountInfo);
        accountInfo.setEmoticionList(emoticions);
        return accountInfo;
    }


    @Override
    public IpAccount findAccountByName(String q) {

        IpAccount ipAccount = ipAccountMapper.findByName(q);
        if (ipAccount == null) {
            return null;
        }
        List<IpAccountActivity> activityList = ipAccountMapper.findActivityByIpId(ipAccount.getId());
        if (!CollectionUtils.isEmpty(activityList)) {
            ipAccount.setIpActivity(true);
        }
        return ipAccount;
    }




    private List<OpenEmoticion> getEmoticionsByQualitys(List<BasicNetPictureQuality> basicNetPictureQualities) {
        if (CollectionUtils.isEmpty(basicNetPictureQualities)) {
            return null;
        }
        List<OpenEmoticion> emoticions = new ArrayList<>();
        for (BasicNetPictureQuality basicEmoticon : basicNetPictureQualities) {
            basicEmoticon.setName("");
            OpenEmoticion emoticion = convertNetEmoticionForQuality(basicEmoticon);
            try {
                resultConverUtil.ConvertByAppId(true, AppConsts.WEB).converURL(emoticion, OpenEmoticionUrlConstant.THUMB_URL,
                        OpenEmoticionUrlConstant.MAIN_URL, OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.WEBP_URL);
            } catch (Exception e) {
                log.warn("OpenEmoticion URL Convert ERROR",e);
            }
            emoticions.add(emoticion);
        }
        return emoticions;
    }

}
