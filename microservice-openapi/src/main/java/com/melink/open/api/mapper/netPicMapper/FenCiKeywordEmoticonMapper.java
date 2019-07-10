package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.BasicNetPicture;
import com.melink.open.api.model.NetKeyword;
import com.melink.open.api.model.TrendyEmoticon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FenCiKeywordEmoticonMapper {

    @Select("select c.guid,c.net_url,c.store_url,e.width,e.height,c.`dynamic`,c.`name`,c.wt,c.sfrom,c.`level`,c.kind,c.create_time,e.fsize,d.textinfo,a.weight,concat(\"https://sdk-ssl.static.dongtu.com/\",e.thumb) as thumb from net_picture_keyword_copy as a left join  keyword_net_copy as b on a.keyword_id=b.guid left join `basic_net_picture` as c on a.np_id=c.guid left join basic_net_picture_textinfo as d on c.guid=d.guid left join basic_net_picture_quality as e on d.guid=e.`np_id`  where c.level>=10 and e.`quality`=2 and b.text=#{text};")
    List<BasicNetPicture> findNetPictureByKeyword(@Param("text") String text);


    @Select("select c.guid,c.category,c.text from keyword_relation as a inner join keyword_net_copy as b inner join keyword_net_copy as c on a.keyword_id=b.guid and a.`relation_id`=c.guid and b.text=#{text};")
    List<NetKeyword> findRelationWordsByKeyword(@Param("text") String text);



    @Select("select count(guid) from keyword_net_copy where text = #{text}")
    Long checkKeywordCountByText(@Param("text") String text);


}
