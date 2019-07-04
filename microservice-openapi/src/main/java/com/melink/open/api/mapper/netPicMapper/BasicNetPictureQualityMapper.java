package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.mapper.netPicMapper.provider.BasicNetPictureQualityProvider;
import com.melink.open.api.model.BasicNetPictureQuality;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BasicNetPictureQualityMapper {

    @Select("select " +
            "t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t2.weight weight,t4.textinfo,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5,(select t.thumb from basic_net_picture_quality t WHERE t.np_id = t0.np_id AND t.quality = 2),t0.webp,t0.webpsize,t0.webpmd5,t5.classify_man,t1.level,t1.kind,group_concat(t7.text) " +
            "from basic_net_picture_quality t0 " +
            "left join basic_net_picture t1 on t1.guid = t0.np_id " +
            "left join net_picture_keyword t2 on t1.guid = t2.np_id " +
            "left join keyword_net t3 on t3.guid = t2.keyword_id " +
            "left join basic_net_picture_textinfo t4 on t1.guid = t4.guid " +
            "left join basic_net_picture_classify t5 on t1.guid = t5.guid " +
            "inner join net_picture_keyword t6 on t1.guid = t6.np_id " +
            "inner join keyword_net t7 on t7.guid = t6.keyword_id " +
            "inner join basic_net_picture_grade t10 on t10.guid = t1.guid " +
            "where t2.status = 1 and t3.text= #{text} and t0.quality = #{quality} and t1.level in (8,10,11) and t10.grade in (-1,0,10)" +
            " group by t1.guid order by t1.level asc,weight desc,t0.np_id desc")
    List<BasicNetPictureQuality> searchNetPictureQuality2(@Param("text") String text, @Param("quality") int quality);

    @Select("select temp.*,group_concat(t7.text) keywords from " +
            "(select t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t9.weight_d2 weight,t4.textinfo,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5," +
            "(select t.thumb from basic_net_picture_quality t WHERE t.np_id = t0.np_id AND t.quality = 2)," +
            "t0.webp,t0.webpsize,t0.webpmd5,t5.classify_man,t1.level,t1.kind,sum(t8.scale) scale " +
            "from basic_net_picture_quality t0 " +
            "left join basic_net_picture t1 on t1.guid = t0.np_id " +
            "left join net_picture_keyword_dension t2 on t1.guid = t2.np_id " +
            "left join keyword_net t3 on t3.guid = t2.keyword_id " +
            "left join basic_net_picture_textinfo t4 on t1.guid = t4.guid " +
            "left join basic_net_picture_classify t5 on t1.guid = t5.guid " +
            "left join basic_dension t8 on t8.dension = t2.dension " +
            "left join net_picture_keyword t9 on t9.np_id = t1.guid and t9.keyword_id = t3.guid " +
            "inner join basic_net_picture_grade t10 on t10.guid = t1.guid " +
            "where t9.status_d2 = 1 and t3.text= #{text} and t0.quality = #{quality} and t1.level in (8,10,11) and t10.grade in (-1,1,0,10) and t1.kind > 1 group by t0.np_id) temp " +
            "inner join net_picture_keyword t6 on temp.np_id = t6.np_id " +
            "inner join keyword_net t7 on t7.guid = t6.keyword_id " +
            "where t6.status_d2 = 1 group by temp.np_id order by ((temp.scale + 10000)/10000 * temp.weight) desc,temp.np_id desc,t6.weight desc")
    List<BasicNetPictureQuality> searchNetPictureQualityForOw(@Param("text") String text, @Param("quality") int quality);

    @Select("select t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t2.weight weight,t4.textinfo,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5," +
            "(select t.thumb from basic_net_picture_quality t WHERE t.np_id = t0.np_id AND t.quality = 2)," +
            "t0.webp,t0.webpsize,t0.webpmd5,t5.classify_man,t1.level,t1.kind,group_concat(t7.text) keywords from basic_net_picture_quality t0 " +
            "left join basic_net_picture t1 on t1.guid = t0.np_id " +
            "left join net_picture_keyword t2 on t1.guid = t2.np_id " +
            "left join keyword_net t3 on t3.guid = t2.keyword_id " +
            "left join basic_net_picture_textinfo t4 on t1.guid = t4.guid "+
            "left join basic_net_picture_classify t5 on t1.guid = t5.guid "+
            "inner join net_picture_keyword t6 on t1.guid = t6.np_id "+
            "inner join keyword_net t7 on t7.guid = t6.keyword_id "+
            "inner join basic_net_picture_grade t10 on t10.guid = t1.guid "+
            "where t2.status_d2 = 1 and t3.text= #{text} and t0.quality = #{quality} and t1.level in (8,10,11) and t10.grade in (-1,1,0,10)  and t1.kind > 1 and t6.status_d2 = 1 " +
            "group by t1.guid order by t1.level asc,weight desc,t0.np_id desc limit 0,800")
    List<BasicNetPictureQuality> searchNetPictureQualityForOwOldRelation(@Param("text") String text, @Param("quality") int quality);

    @Select("select t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t0.height,t0.lossy_url,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5,t0.thumb " +
            "FROM basic_net_picture_quality t0 WHERE  t0.quality= #{quality} and t0.np_id =#{npid}")
    BasicNetPictureQuality findByNpid(@Param("npid") String npid, @Param("quality") int quality);

    @SelectProvider(type = BasicNetPictureQualityProvider.class,method = "searchNewestNetPicture")
    List<BasicNetPictureQuality> searchNewestNetPicture(@Param("page") int page);

    @SelectProvider(type = BasicNetPictureQualityProvider.class, method = "searchNetPictureQualityForIpAccount")
    List<BasicNetPictureQuality> searchNetPictureQualityForIpAccount(@Param("ipid") Integer ipid, @Param("p") int p, @Param("size") int size);

    @Select("select np_id,quality,lossy_url,store_url,width,height,fsize from  basic_net_picture_quality where np_id = #{guids} and quality in (1,2,3)")
    List<BasicNetPictureQuality> findQualityByNpId(@Param("guids") String guids);

    @SelectProvider(type = BasicNetPictureQualityProvider.class,method = "findQualityByNpIds")
    List<BasicNetPictureQuality> findQualityByNpIds(@Param("guids") List<String> guids,@Param("quality") int quality);

    @Select("select t0.np_id,t0.store_url,t0.width,t0.height,t0.fsize,t0.md5,t4.textinfo,t0.lossy_url,t0.lossy_fsize,t0.lossy_md5," +
            "(select t.thumb from basic_net_picture_quality t WHERE t.np_id = t0.np_id AND t.quality = 2),t0.webp,t0.webpsize,t0.webpmd5," +
            "t5.classify_man,t1.level,t1.kind from basic_net_picture_quality t0 " +
            "left join basic_net_picture t1 on t1.guid = t0.np_id " +
            "left join basic_net_picture_textinfo t4 on t1.guid = t4.guid " +
            "left join basic_net_picture_classify t5 on t1.guid = t5.guid " +
            "where t4.textinfo like '%' #{text} '%' and t0.quality = #{quality} and t1.level in (8,10,11) and t1.kind > 1 " +
            "group by t1.guid order by t1.level asc,t0.np_id desc limit 0,5"
    )
    @Results(
            value = @Result(column = "textinfo", property = "name")
    )
    List<BasicNetPictureQuality> searchQualityLikeTextInfo(@Param("text") String text, @Param("quality") int quality);

}
