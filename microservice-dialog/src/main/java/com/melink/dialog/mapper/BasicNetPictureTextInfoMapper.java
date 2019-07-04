package com.melink.dialog.mapper;

import com.melink.dialog.model.BasicNetPictureTextInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BasicNetPictureTextInfoMapper {

    @Select("select textinfo from basic_net_picture_textinfo where guid = #{guid}")
    BasicNetPictureTextInfo findTextInfoBySearch(@Param("guid")String guid);

    @Select("select t1.textinfo from trendy_emoticon t0 left join basic_net_picture_textinfo t1 on t0.np_id = t1.guid where t0.guid = #{guid}")
    BasicNetPictureTextInfo findTextInfoByTrending(@Param("guid") String guid);
}
