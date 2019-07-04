package com.melink.open.api.service.impl;

import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.open.api.constant.WebSiteConsts;
import com.melink.open.api.mapper.netPicMapper.*;
import com.melink.open.api.model.*;
import com.melink.open.api.service.GifDetailService;
import com.melink.open.api.util.HttpsResConvertUtils;
import com.melink.open.api.util.SearchApiUtil;
import com.melink.sop.api.models.open.modelinfos.AccountInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticonDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GifDetailServiceImpl implements GifDetailService {
    @Autowired
    private BasicNetPictureMapper basicNetPictureMapper;
    @Autowired
    private IpAccountMapper ipAccountMapper;
    @Autowired
    private BasicNetPictureQualityMapper basicNetPictureQualityMapper;
    @Autowired
    private BasicNetPictureLossyMapper basicNetPictureLossyMapper;
    @Autowired
    private KeywordNetMapper keywordNetMapper;
    @Autowired
    private BasicNetPicturePvMapper basicNetPicturePvMapper;
    @Override

    public BasicNetPicture findBasicNetPictureById(String guid) {
        return basicNetPictureMapper.findById(guid);
    }

    @Override
    public OpenEmoticonDetail getOpenEmoticonDetail(BasicNetPicture basicNetPicture, String id, boolean isMobiie) {
        if (basicNetPicture == null) {
            return null;
        }
        List<IpAccount> ipAccounts = ipAccountMapper.findIpAccountByNpId(basicNetPicture.getGuid());
        BasicNetPictureQuality netPictureQuality = basicNetPictureQualityMapper.findByNpid(basicNetPicture.getGuid(), 2);
        List<BasicNetPictureLossy> lossys = basicNetPictureLossyMapper.findLossyByNpId(id);
        List<NetKeyword> keywordList = keywordNetMapper.findEmoticonKeyword(basicNetPicture.getGuid());
        BasicNetPicturePv picturePv = basicNetPicturePvMapper.findPvByGuid(basicNetPicture.getGuid());
        Integer pvCount = 1;
        if(isMobiie){
            if(picturePv != null){
                pvCount = picturePv.getMobilePv();
                picturePv.setMobilePv(++pvCount);
                basicNetPicturePvMapper.update(picturePv);
            }else{
                picturePv = new BasicNetPicturePv();
                picturePv.setGuid(basicNetPicture.getGuid());
                picturePv.setMobilePv(pvCount);
                picturePv.setPv(0);
                basicNetPicturePvMapper.create(picturePv);
            }
        }else{
            if(picturePv != null){
                pvCount = picturePv.getPv();
                picturePv.setPv(++pvCount);
                basicNetPicturePvMapper.update(picturePv);
            }else{
                picturePv = new BasicNetPicturePv();
                picturePv.setGuid(basicNetPicture.getGuid());
                picturePv.setPv(pvCount);
                picturePv.setMobilePv(0);
                basicNetPicturePvMapper.create(picturePv);
            }
        }
        List<String> keywords = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(keywordList)) {
            for (NetKeyword netKeyword : keywordList) {
                if(keywords.size() > 4){
                    break;
                }
                keywords.add(netKeyword.getText());
            }
        }
        OpenEmoticonDetail emoticionDetail = SearchApiUtil.convert(basicNetPicture,keywords,netPictureQuality,lossys);
        emoticionDetail.setPv(pvCount);
        if(CollectionUtils.isEmpty(ipAccounts)){
            emoticionDetail.setIpList(new ArrayList<AccountInfo>());
        }else{
            List<AccountInfo> ipList = new ArrayList<AccountInfo>();
            for (IpAccount account : ipAccounts) {
                AccountInfo accountInfo = new AccountInfo();
                BeanCoperUtils.getInstance().copy(account,accountInfo);
                accountInfo.setInnerLink(WebSiteConsts.DONGTU_IP_DOMAIN + accountInfo.getInnerLink());
                ipList.add(accountInfo);
            }
            emoticionDetail.setIpList(ipList);
        }
        OpenEmoticonDetail openEmoticonDetail = HttpsResConvertUtils.gifComvert(emoticionDetail);

        return openEmoticonDetail;
    }
}
