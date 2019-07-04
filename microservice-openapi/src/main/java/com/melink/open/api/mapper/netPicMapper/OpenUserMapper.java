package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.mapper.netPicMapper.provider.OpenUserProvider;
import com.melink.open.api.model.OpenUser;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

public interface OpenUserMapper {

    @Select("select * from open_user where guid = #{openid}")
    OpenUser findByGuid(@Param("openid") String openid);

    @UpdateProvider(type = OpenUserProvider.class,method = "update")
    void update(@Param("user") OpenUser user);

    @Select("select distinct * from open_user where app_id = #{appId} and third_key = #{thirdKey} limit 1")
    OpenUser selectByThirdKey(@Param("appId") String appId, @Param("thirdKey") String thirdKey);

    @Select("select distinct * from open_user where app_id = #{appId} and device_no = #{deviceNo} limit 1")
    OpenUser selectByDeviceNo(@Param("appId") String appId, @Param("deviceNo") String deviceNo);

    @InsertProvider(type = OpenUserProvider.class,method = "create")
    void create(@Param("user") OpenUser user);

    @Select("select distinct * from open_user where app_id = #{appId} and device_no = #{deviceNo} and third_key = #{thirdKey} limit 1")
    OpenUser selectByThirdKeyAndDeviceNo(@Param("appId") String appId,@Param("thirdKey") String thirdKey, @Param("deviceNo") String deviceNo);
}
