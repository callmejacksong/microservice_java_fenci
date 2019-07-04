package com.melink.open.api.mapper.melink.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BasicEmoticonPackageProvider {

    public String findListByPagination(@Param("p") int p, @Param("size") int size) {
        return new SQL() {
            {
                int start = (p - 1) * size;
                SELECT("t1.guid,t1.name,t1.banner,t1.intro,t1.copyright,t1.cover,t1.is_emoji,count(t2.guid) emoji_count");
                FROM("basic_emoticon_package t1 inner join basic_emoticon t2 on t1.guid = t2.package_id");
                GROUP_BY("t1.guid");
                ORDER_BY("t1.createtime desc");
            }
        }.toString();
    }

}
