package com.melink.advert.mapper.netPic.provider;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class PicturePromotionProvider {


    public String findPromotionByAppIdAndNpids(@Param("appId") String appId,
                                               @Param("codes") List<String> codes,
                                               @Param("promotionSwitch")Boolean promotionSwitch,
                                               @Param("globalPromotionSwitch")Boolean globalPromotionSwitch) {
        StringBuilder s = new StringBuilder();
        s.append("select * from (select t1.* from app_picture_promotion t0 left join picture_promotion t1 on t0.promotion_id = t1.guid " +
                "where ");
        if (promotionSwitch && !globalPromotionSwitch ) {
            s.append("t0.app_id = #{appId}");
        } else if (!promotionSwitch && globalPromotionSwitch) {
            s.append("t0.app_id = '-1'");
        } else if (promotionSwitch && globalPromotionSwitch ) {
            s.append("(t0.app_id = #{appId} or t0.app_id = '-1')");
        }
        s.append(" and t1.np_id in(");
        for (int i = 0; i < codes.size(); i++) {
            s.append("'"+codes.get(i)+"'");
            if (i < codes.size()-1) {
                s.append(",");
            }
        }
        s.append(") and now() between t1.promotion_begin_time and t1.promotion_end_time order by t0.app_id desc) a group by a.np_id");
        return s.toString();
    }
}
