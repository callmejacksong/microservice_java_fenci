package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.OpenUserInfo;

public interface UserService {
    OpenUserInfo auth(String appId, String deviceNo, String name, Integer gender, String thirdKey, String openid, String info);
}
