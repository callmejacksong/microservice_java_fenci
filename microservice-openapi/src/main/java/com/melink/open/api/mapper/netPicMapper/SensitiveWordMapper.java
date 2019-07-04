package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.SensitiveWord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SensitiveWordMapper {


    @Select("select * from sensitive_word where type = 3 and app_id is null")
    List<SensitiveWord> findWhitelistWord();

}
