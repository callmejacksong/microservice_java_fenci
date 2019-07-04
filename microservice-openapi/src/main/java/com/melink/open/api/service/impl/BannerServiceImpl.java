package com.melink.open.api.service.impl;

import com.melink.microservice.utils.BeanCoperUtils;
import com.melink.open.api.mapper.netPicMapper.BannerMapper;
import com.melink.open.api.model.Banner;
import com.melink.open.api.service.BannerService;
import com.melink.sop.api.models.open.modelinfos.BannerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Override
    public List<BannerInfo> getBanner() {

        List<Banner> banners = bannerMapper.findShowBannerAll();
        if (CollectionUtils.isEmpty(banners)) {
            return null;
        }
        List<BannerInfo> bannerInfos = new ArrayList<>();
        BeanCoperUtils copyUtil = BeanCoperUtils.getInstance();
        for (Banner b : banners) {
            BannerInfo bannerInfo = new BannerInfo();
            copyUtil.copy(b, bannerInfo);
            bannerInfos.add(bannerInfo);
        }
        return bannerInfos;
    }
}
