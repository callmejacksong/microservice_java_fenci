package com.melink.open.api.mapper.netPicMapper.provider;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class BasicNetPictureProvider {

    public String findBysicNetPictureByGuids(@Param("guids") List<String> guids) {
        StringBuilder builder = new StringBuilder();
        builder.append("select t0.guid,t0.height,t0.width,t0.store_url,t0.fsize,t0.create_time,t0.copyright,t2.classify,t0.dynamic from basic_net_picture t0 " +
                "left join basic_net_picture_classify t2 on t0.guid = t2.guid where t0.guid in (");
        int len = guids.size();
        for (int i = 0 ; i < len; i++) {
            builder.append("'" + guids.get(i) + "'");
            if (i < len - 1){
                builder.append(",");
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
