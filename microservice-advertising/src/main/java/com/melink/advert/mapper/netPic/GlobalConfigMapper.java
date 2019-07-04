package com.melink.advert.mapper.netPic;

import org.apache.ibatis.annotations.Select;

public interface GlobalConfigMapper {


    @Select("select ad_gif_switch from global_config")
    Integer findAdvertGlobal();
}
