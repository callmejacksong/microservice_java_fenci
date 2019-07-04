package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.mapper.netPicMapper.provider.BasicNetPictureProvider;
import com.melink.open.api.model.BasicNetPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BasicNetPictureMapper {

    @Select("select t0.guid,t0.height,t0.width,t0.store_url,t0.fsize,t0.create_time,t0.copyright,t2.classify,t0.dynamic from basic_net_picture t0 " +
            "left join basic_net_picture_classify t2 on t0.guid = t2.guid " +
            "where t0.guid = #{guid}")
    @Results(id = "guid", value = {
            @Result(property = "basicNetPictureQualities", column = "guid", many = @Many(select = "com.melink.open.api.mapper.netPicMapper.BasicNetPictureQualityMapper.findQualityByNpId")),
            @Result(property = "netPictureTextInfo",column = "guid",one = @One(select = "com.melink.open.api.mapper.netPicMapper.BasicNetPictureTextInfoMapper.findTextInfoByGuid"))
    })
    BasicNetPicture findBasicNetPictureByGuid(@Param("guid") String guid);

    @SelectProvider(type = BasicNetPictureProvider.class, method = "findBysicNetPictureByGuids")
    @ResultMap(value = "guid")
    List<BasicNetPicture> findBysicNetPictureByGuids(@Param("guids") List<String> guids);

    @Select("select * from basic_net_picture where guid = #{id}")
    BasicNetPicture findById(@Param("id") String id);

}
