package com.melink.dialog.feignClient;

import com.melink.open.api.searchApi.TrendingApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("openapi-service")
public interface TrendingApiClient extends TrendingApi {

}
