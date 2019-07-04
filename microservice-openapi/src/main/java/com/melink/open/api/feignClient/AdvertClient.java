package com.melink.open.api.feignClient;

import com.melink.advert.api.AdvertApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("advert-service")
public interface AdvertClient extends AdvertApi {

}
