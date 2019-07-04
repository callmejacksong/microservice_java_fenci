package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.mapper.netPicMapper.provider.IpAccountProvider;
import com.melink.open.api.model.IpAccount;
import com.melink.open.api.model.IpAccountActivity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


public interface IpAccountMapper {

    // 使用中通过查询account得到Id查询activity如果有给属性setIpActivity=true;service做set
    //通过name查询ipAccount
    @Select("select * from ip_account where name = #{name} limit 1")
    IpAccount findByName(@Param("name") String name);
    //通过id查询activity
    @Select("select * from ip_activity where begin_time <= now() and end_time >= now() and ip_id=#{ipid}")
    List<IpAccountActivity> findActivityByIpId(@Param("ipid") Integer ipid);

    @SelectProvider(type = IpAccountProvider.class,method = "findByLimit")
    List<IpAccount> findByLimit(@Param("page") Integer page, @Param("size") Integer size);

    @Select("select * from ip_account order by display_order desc")
    List<IpAccount> findAll();

    @Select("select * from ip_account where inner_link = #{linkCode} limit 1")
    IpAccount findByInnerLinkCode(@Param("linkCode") String linkCode);
    @Select("select t1.id,t1.name,t1.icon,t1.inner_link from ip_account t1 left join ip_picture t2 on t1.id = t2.ip_id where t2.np_id = #{npid}")
    List<IpAccount> findIpAccountByNpId(@Param("npid") String npid);




}