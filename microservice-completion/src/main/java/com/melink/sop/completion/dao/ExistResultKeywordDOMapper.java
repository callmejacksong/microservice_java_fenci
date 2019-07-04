package com.melink.sop.completion.dao;

import com.melink.sop.completion.model.ExistResultKeywordDO;
import java.util.List;

public interface ExistResultKeywordDOMapper {
    int deleteByPrimaryKey(String guid);

    int insert(ExistResultKeywordDO record);

    ExistResultKeywordDO selectByPrimaryKey(String guid);

    List<ExistResultKeywordDO> selectAll();

    int updateByPrimaryKey(ExistResultKeywordDO record);
}