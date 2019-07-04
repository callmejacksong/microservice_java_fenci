package com.melink.advert.mapper.netPic;

import com.melink.advert.model.BasicNetPictureQuality;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface AdvertMapper {

    //t0.webpmd5,,t0.webp,t0.webpsize   暂不返回字段
    @Select("  select t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t0.thumb,t4.textinfo,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5,(select t.thumb from basic_net_picture_quality t WHERE t.np_id = t0.np_id AND t.quality = 2),t5.classify_man,t1.level,t1.kind,t6.position,t6.start_time,t6.end_time " +
            "from tmp_ad_gif t6 left join basic_net_picture t1 on t6.np_id = t1.guid " +
            "left join basic_net_picture_quality t0  on t1.guid = t0.np_id " +
            "left join basic_net_picture_textinfo t4 on t1.guid = t4.guid  " +
            "left join basic_net_picture_classify t5 on t1.guid = t5.guid  " +
            "where t0.quality = 2 and t6.end_time > now() and (t6.type = #{type} or t6.type = 'both') order by t6.position")
    @Results(id = "advert",value = {
            @Result(property = "name",column = "textinfo"),
            @Result(property = "advertPosition",column = "position")
    })
    List<BasicNetPictureQuality> findGifByAdvertType(@Param("type")String type);

    @Select("select min(end_time) from tmp_ad_gif where start_time < now() and end_time > now() and (type = #{type} or type = 'both')")
    Date findEndTimeMin(@Param("type")String type);
}
