package com.melink.open.api.mapper.melink;

import com.melink.open.api.model.BasicEmoticon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BasicEmoticonMapper {

    @Select("select guid,thumbail,emo_text,emo_code,main_image,is_emoji,display_order from basic_emoticon where package_id=#{guid}")
    List<BasicEmoticon> findByPackageId(@Param("guid") String guid);

}
