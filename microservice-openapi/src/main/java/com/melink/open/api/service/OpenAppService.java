package com.melink.open.api.service;


import com.melink.open.api.model.OpenApp;

import java.util.List;

public interface OpenAppService{

	List<OpenApp> findAppsByPlatformId(String platformId, int size);

	OpenApp getAppByCache(String appId);

	boolean isTestApp(String appId);



}