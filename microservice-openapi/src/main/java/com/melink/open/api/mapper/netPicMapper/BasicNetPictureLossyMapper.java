package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.BasicNetPictureLossy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BasicNetPictureLossyMapper {

    @Select("select t0.np_id,t0.type,t0.fsize,t0.md5,t0.create_time,t0.lossy_url FROM basic_net_picture_lossy2 t0 WHERE t0.np_id = #{npid}")
    List<BasicNetPictureLossy> findLossyByNpId(@Param("npid") String npid);
}
