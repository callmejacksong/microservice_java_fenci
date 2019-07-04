package com.melink.open.api.feignClient;

import com.melink.advert.api.PicturePromotionApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("advert-service")
public interface PicturePromotionClient extends PicturePromotionApi {

}
