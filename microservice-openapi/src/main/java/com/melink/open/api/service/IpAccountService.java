package com.melink.open.api.service;

import com.melink.open.api.model.IpAccount;
import com.melink.sop.api.models.open.modelinfos.AccountInfo;

import java.util.List;

public interface IpAccountService {
    List<IpAccount> getIpAccount();

    List<AccountInfo> ipAccountToAccountInfo(List<IpAccount> ipAccounts);

    IpAccount findByInnerLinkCode(String linkCode);

    AccountInfo getAccountAddEmoticion(IpAccount linkCode);

    IpAccount findAccountByName(String q);

    AccountInfo getAccountSearch(IpAccount account, int p, int size);
}
