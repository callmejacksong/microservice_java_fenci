package com.melink.open.api.service.impl;

import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.open.api.mapper.netPicMapper.FriendlyLinkMapper;
import com.melink.open.api.model.FriendlyLink;
import com.melink.open.api.service.FriendlyLinkService;
import com.melink.sop.api.models.QiNiuBucket;
import com.melink.sop.api.models.open.modelinfos.FriendlyLinkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class FriendlyLinkServiceImpl implements FriendlyLinkService {

    @Autowired
    private FriendlyLinkMapper friendlyLinkMapper;

    @Override
    public List<FriendlyLinkInfo> getFriendlyLinkInfoList() {

        List<FriendlyLink> friendlyLinkList = friendlyLinkMapper.findFriendlyLinkList();
        if (CollectionUtils.isEmpty(friendlyLinkList)) {
            return null;
        }
        List<FriendlyLinkInfo> friendlyLinkInfos = new ArrayList<>();
        for (FriendlyLink friendlyLink : friendlyLinkList) {
            FriendlyLinkInfo friendlyLinkInfo = new FriendlyLinkInfo();
            BeanCoperUtils.getInstance().copy(friendlyLink,friendlyLinkInfo);
            friendlyLinkInfo.setImg(friendlyLinkInfo.getImg().replace(QiNiuBucket.BQMM_BUCKET,QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET));
            friendlyLinkInfos.add(friendlyLinkInfo);
        }
        return friendlyLinkInfos;
    }
}
