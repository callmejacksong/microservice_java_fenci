package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.WhitelistWordRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WhitelistWordRecordMapper {

    @Select("select max(version) from whitelist_word_record")
    String findMaxVersion();

    @Select("select distinct * from whitelist_word_record where version > #{version} order by createtime asc")
    List<WhitelistWordRecord> findVersion(Integer version);

}
