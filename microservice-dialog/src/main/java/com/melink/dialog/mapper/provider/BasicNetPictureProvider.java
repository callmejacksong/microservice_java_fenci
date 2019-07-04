package com.melink.dialog.mapper.provider;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class BasicNetPictureProvider {

    public String searchPictureByMood(@Param("polars") List<Integer> polars,@Param("size")Integer size) {
        StringBuilder s = new StringBuilder();

        s.append("select t2.np_id,t2.width,t2.height,t2.store_url from shenpeitu_emotion_gifs t0 left join basic_net_picture_textinfo t1 on t0.np_id = t1.guid left join basic_net_picture_quality t2 on t0.np_id = t2.np_id where t0.category in(");
        for (int i = 0; i < polars.size(); i++) {

            if (i == polars.size() - 1) {
                s.append(polars.get(i));
            } else {
                s.append(polars.get(i)).append(",");
            }
        }
        s.append(") and t2.quality = 2 order by t0.weight desc limit #{size}");

        return s.toString();
    }
}
