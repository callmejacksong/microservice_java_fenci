package com.melink.open.api.mapper.netPicMapper.provider;

import com.melink.microservice.utils.DateUtils;
import com.melink.open.api.model.OpenUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class OpenUserProvider {
    private static final String dateStr = "yyyy-MM-dd HH:mm:ss";

    public String update(@Param("user") OpenUser user) {
        return new SQL() {
            {
                String date = DateUtils.getDate(new Date(),dateStr);
                UPDATE("open_user");
                SET("gender = " + user.getGender());
                SET("updatetime ='" + date+"'");
                if (user.getAppId() != null) {
                    SET("app_id ='" + user.getAppId()+"'");
                }
                if (user.getDeviceNo() != null) {
                    SET("device_no ='" + user.getDeviceNo()+"'");
                }
                if (user.getName() != null) {
                    SET("name ='" + user.getName()+"'");
                }
                if (user.getThirdKey() != null) {
                    SET("third_key ='" + user.getThirdKey()+"'");
                }
                if (user.getInfo() != null) {
                    SET("info ='" + user.getInfo()+"'");
                }
                WHERE("guid = '"+user.getGuid()+"'");

            }
        }.toString();
    }

    public String create(@Param("user") OpenUser user) {
        return new SQL() {
            {
                String date = DateUtils.getDate(new Date(),dateStr);
                INSERT_INTO("open_user");
                VALUES("guid","'"+user.getGuid()+"'");
                VALUES("app_id","'"+user.getAppId()+"'");
                VALUES("device_no","'"+user.getDeviceNo()+"'");
                VALUES("name","'"+user.getName()+"'");
                VALUES("gender","'"+String.valueOf(user.getGender())+"'");
                VALUES("third_key","'"+user.getThirdKey()+"'");
                VALUES("createtime", "'"+date+"'");
                VALUES("updatetime", "'"+date+"'");
                VALUES("info","'" +user.getInfo()+"'");
            }
        }.toString();
    }

}
