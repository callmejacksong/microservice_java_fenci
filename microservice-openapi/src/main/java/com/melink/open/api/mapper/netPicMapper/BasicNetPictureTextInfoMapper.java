package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.BasicNetPictureTextInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface BasicNetPictureTextInfoMapper {

    @Select("select textinfo from basic_net_picture_textinfo where guid = #{guid}")
    BasicNetPictureTextInfo findTextInfoByGuid(@Param("guid")String guid);
}
