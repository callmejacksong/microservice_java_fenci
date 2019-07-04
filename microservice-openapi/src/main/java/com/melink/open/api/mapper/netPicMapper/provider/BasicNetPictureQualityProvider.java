package com.melink.open.api.mapper.netPicMapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class BasicNetPictureQualityProvider {

    public String searchNewestNetPicture(@Param("page") int page) {
        return new SQL() {
            {
                int size = 500;
                int start = (page - 1) * size;
                SELECT("t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t0.width weight,'',t0.lossy_url,t0.lossy_fsize,t0.lossy_md5,t0.thumb,t0.webp,t0.webpsize,t0.webpmd5,t5.classify_man classify,t1.level,t1.kind,group_concat(t7.text) keywords");
                FROM("basic_net_picture_quality t0 " +
                        "left join (select * from basic_net_picture where level in (8,9,10) and sfrom in ('uploadplug','mgc') and kind > 1 order by create_time desc limit "+page+","+size+") t1 on t1.guid = t0.np_id " +
                        "left join basic_net_picture_classify t5 on t1.guid = t5.guid inner join net_picture_keyword t6 on t1.guid = t6.np_id inner join keyword_net t7 on t7.guid = t6.keyword_id");
                WHERE("t0.quality = 2");
                GROUP_BY("t1.guid");
                ORDER_BY("t1.create_time desc");

            }
        }.toString();
    }
    public String searchNetPictureQualityForIpAccount(@Param("ipid") Integer ipid, @Param("p") int p,@Param("size") int size){
        return new SQL() {
            {
                int start = (p - 1) * size;
                SELECT("t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t0.width weight,'',t0.lossy_url,t0.lossy_fsize,t0.lossy_md5,t0.thumb,t0.webp,t0.webpsize,t0.webpmd5,t5.classify_man classify,t1.level,t1.kind,group_concat(t7.text) keywords");
                FROM("basic_net_picture_quality t0 left join basic_net_picture t1 on t1.guid = t0.np_id " +
                        "left join basic_net_picture_classify t5 on t1.guid = t5.guid " +
                        "left join ip_picture t8 on t1.guid = t8.np_id " +
                        "left join net_picture_keyword t6 on t1.guid = t6.np_id " +
                        "left join keyword_net t7 on t7.guid = t6.keyword_id");
                WHERE("t0.quality = 2 and t1.level in (8,10,11) and t8.ip_id = #{ipid}");
                GROUP_BY("t1.guid");
                ORDER_BY("t8.display_order asc limit "+start+","+size);
            }
        }.toString();
    }

    public String findQualityByNpIds(@Param("guids") List<String> guids,@Param("quality") int quality) {
        StringBuilder s = new StringBuilder();
        s.append("select t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t0.height,t0.lossy_url,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5,t0.thumb " +
                        "FROM basic_net_picture_quality t0 WHERE  t0.quality= #{quality} and t0.np_id in(");
        int len = guids.size();
        for (int i = 0 ; i < len; i++) {
            s.append("'" + guids.get(i) + "'");
            if (i < len - 1){
                s.append(",");
            }
        }
        s.append(")");
        return s.toString();
    }
}
