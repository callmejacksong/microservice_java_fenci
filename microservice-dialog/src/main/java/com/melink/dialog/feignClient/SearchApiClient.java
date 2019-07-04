package com.melink.dialog.feignClient;

import com.melink.open.api.searchApi.SearchApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("openapi-service")
public interface SearchApiClient extends SearchApi {

}
