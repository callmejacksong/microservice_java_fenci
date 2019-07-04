package com.melink.open.api.mapper.melink;

import com.melink.open.api.model.PromotionVideo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PromotionVideoMapper {

    @Select("select * from promotion_video where guid = #{promotionVideoId}")
    PromotionVideo findByGuid(@Param("promotionVideoId") String promotionVideoId);
}
