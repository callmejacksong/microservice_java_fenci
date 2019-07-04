package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.Banner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerMapper {

    @Select("select * from banner where disable = 1 order by position")
    List<Banner> findShowBannerAll();

    @Insert("insert into banner(guid,url,link,position,title,create_time,disable) values(#{banner.guid},#{banner.url},#{banner.link},#{banner.position},#{banner.title},#{banner.createTime},0)")
    Integer addBanner(@Param("banner") Banner banner);
}
