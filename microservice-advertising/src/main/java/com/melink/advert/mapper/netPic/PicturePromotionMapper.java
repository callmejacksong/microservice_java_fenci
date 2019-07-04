package com.melink.advert.mapper.netPic;


import com.melink.advert.mapper.netPic.provider.PicturePromotionProvider;
import com.melink.advert.model.PicturePromotion;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PicturePromotionMapper {

    @Select("select distinct * from picture_promotion where np_id = #{code} limit 1")
    @Results(id = "picture",
            value = {
                    @Result(property = "promotionIconId", column = "promotion_id"),
                    @Result(property = "promotionVideoId", column = "promotion_video"),
            }
    )
    PicturePromotion findByNpId(@Param("code") String code);

    @Select("select t1.* from app_picture_promotion t0 left join picture_promotion t1 on t0.promotion_id = t1.guid " +
            "where t0.app_id = #{appId} and t1.np_id = #{code} and now() between t1.promotion_begin_time and t1.promotion_end_time")
    @ResultMap("picture")
    PicturePromotion findPromotionAllByAppId(@Param("appId") String appId, @Param("code") String code);

//    @Select("select t1.* from app_picture_promotion t0 left join picture_promotion t1 on t0.promotion_id = t1.guid " +
//            "where t0.app_id = #{appId} and t1.np_id = #{code} and now() between t1.promotion_begin_time and t1.promotion_end_time")
//    @ResultMap("picture")
    @SelectProvider(type = PicturePromotionProvider.class,method = "findPromotionByAppIdAndNpids")
    @ResultMap("picture")
    List<PicturePromotion> findPromotionByAppIdAndNpids(@Param("appId") String appId,
                                                        @Param("codes") List<String> codes,
                                                        @Param("promotionSwitch")Boolean promotionSwitch,
                                                        @Param("globalPromotionSwitch")Boolean globalPromotionSwitch);


    @Select("select * from (select t1.*,t2.position from app_picture_promotion t0 " +
            "left join picture_promotion t1 on t0.promotion_id = t1.guid " +
            "left join promotion_order t2 on t0.promotion_id = t2.promotion_id and t2.type = #{type} " +
            "where ((t0.app_id = #{appId} and #{promotionSwitch}) or (t0.app_id = '-1' and #{globalPromotionSwitch})) " +
            "and now() between t1.promotion_begin_time and t1.promotion_end_time order by t0.app_id desc) a group by a.np_id")
    @ResultMap("picture")
    List<PicturePromotion> findPromotionByAppIdAll(@Param("appId") String appId,
                                                   @Param("type") String type,
                                                   @Param("promotionSwitch") Boolean promotionSwitch,
                                                   @Param("globalPromotionSwitch") Boolean globalPromotionSwitch);
}
