package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.BasicNetPicturePv;
import org.apache.ibatis.annotations.*;

public interface BasicNetPicturePvMapper {

    @Select("select guid,moblie_pv mobile_pv,pv from basic_net_picture_pv where guid = #{guid}")
    BasicNetPicturePv findPvByGuid(@Param("guid") String guid);

    @Update("update basic_net_picture_pv set pv = #{picturePv.pv},moblie_pv = #{picturePv.mobilePv} where guid = #{picturePv.guid}")
    void update(@Param("picturePv") BasicNetPicturePv picturePv);

    @Insert("insert into basic_net_picture_pv(guid,pv,moblie_pv) value(#{picturePv.guid},#{picturePv.pv},#{picturePv.mobilePv})")
    void create(@Param("picturePv") BasicNetPicturePv picturePv);

}
