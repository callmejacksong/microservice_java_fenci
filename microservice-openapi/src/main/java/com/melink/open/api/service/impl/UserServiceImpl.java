package com.melink.open.api.service.impl;

import com.melink.open.api.mapper.netPicMapper.OpenUserMapper;
import com.melink.open.api.model.OpenUser;
import com.melink.open.api.service.UserService;
import com.melink.sop.api.models.open.modelinfos.OpenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private OpenUserMapper openUserMapper;

    @Override
    public OpenUserInfo auth(String appId, String deviceNo, String name, Integer gender, String thirdKey, String openid, String info) {
        OpenUser user;
        if (StringUtils.hasText(openid)) {
            user = openUserMapper.findByGuid(openid);
        } else {
            user = selectOrInsert(appId, deviceNo, thirdKey);
        }
        if(user == null){
            return null;
        }
        if (gender != 0 || StringUtils.hasText(name) || StringUtils.hasText(info)) {
            if (StringUtils.hasText(thirdKey)) {
                user.setThirdKey(thirdKey);
            }
            user.setGender(gender);
            user.setName(name);
            user.setInfo(info);
            openUserMapper.update(user);
        }
        OpenUserInfo openUserInfo = new OpenUserInfo();
        openUserInfo.setOpenid(user.getGuid());
        return openUserInfo;
    }


    public OpenUser selectOrInsert(String appId, String deviceNo, String thirdKey) {
        OpenUser user = null;
//        if(StringUtils.hasText(thirdKey)){
//            user = openUserMapper.selectByThirdKey(appId,thirdKey);
//        }else if(StringUtils.hasText(deviceNo)){
//            user = openUserMapper.selectByDeviceNo(appId,deviceNo);
//        }

        user = openUserMapper.selectByThirdKeyAndDeviceNo(appId,thirdKey,deviceNo);
        if(user == null){
            user = new OpenUser();
            user.setAppId(appId);
            user.setDeviceNo(deviceNo);
            user.setThirdKey(thirdKey);
            openUserMapper.create(user);
        }
        return user;
    }

}
