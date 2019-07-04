package com.melink.dialog.mapper;

import com.melink.dialog.mapper.provider.BasicNetPictureProvider;
import com.melink.dialog.model.BasicNetPicture;
import com.melink.dialog.model.BasicNetPictureQuality;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BasicNetPictureMapper {

    @Select("select * from basic_net_picture t0  where t0.guid='00000d6a8d5311e7bd957cd30abeadec'")
    @Results(id = "guid", value = {
            @Result(property = "netPictureTextInfo", column = "guid", one = @One(select = "com.melink.open.api.mapper.netPicMapper.BasicNetPictureTextInfoMapper.findTextInfoByGuid"))
    })
    BasicNetPicture getEmoticonTextById(@Param("guid") String guid);

    @Select("select * from basic_net_picture t0 left join basic_net_picture_classify t1 on t0.guid=t1.guid where t1.classify=1 limit 100")
    List<BasicNetPictureQuality> getEmoticonByClassify();

        @Select("select t4.np_id,t4.store_url,t3.sfrom from keyword_net t0 " +
                "left join net_picture_keyword t1 on t0.guid = t1.keyword_id " +
                "left join basic_net_picture_textinfo t2 on t1.np_id = t2.guid " +
                "left join basic_net_picture t3 on t2.guid = t3.guid left " +
                "join basic_net_picture_quality t4 on t3.guid = t4.np_id " +
                "where t0.text = #{q} and t2.textinfo = '' and t3.level>=10 and t4.quality = 2 group by t3.guid order by t1.weight desc limit #{size}")
    @Results(id = "picture",
            value= {
            @Result(column = "np_id", property = "guid")
    }
    )
    List<BasicNetPicture> searchPictureLikeText(@Param("q") String q, @Param("size") Integer size);

    @Select("select t5.guid,t5.store_url,t3.textinfo,t5.sfrom from keyword_net t0 " +
            "left join net_picture_keyword_dension t1 on t0.guid = t1.keyword_id " +
            "left join basic_net_picture_textinfo t3 on t1.np_id = t3.guid " +
            "left join basic_net_picture t5 on t1.np_id = t5.guid " +
            "where t0.text = #{q} and t1.dension = 9999 and t5.level >= 10 order by t1.weight desc limit #{size}")
    @Results(
            value = {
                    @Result(column = "textinfo", property = "name")
            }
    )
    List<BasicNetPicture> searchPictureDensionByText(@Param("q") String q, @Param("size") Integer size);

    @SelectProvider(type = BasicNetPictureProvider.class, method = "searchPictureByMood")
    @ResultMap("picture")
    List<BasicNetPicture> searchPictureByMood(@Param("polars") List<Integer> polars, @Param("size") Integer size);

    @Select("select store_url from basic_net_picture_quality where np_id = #{guid} and quality = 2")
    String getURLbyGuid(@Param("guid") String guid);



}
