package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.mapper.netPicMapper.provider.KeywordNetProvider;
import com.melink.open.api.model.NetKeyword;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface KeywordNetMapper {

    @SelectProvider(type = KeywordNetProvider.class,method = "findKeywordByKeywordIds")
    List<NetKeyword> findKeywordByKeywordIds(@Param("ids") List<String> ids);

    @Select("select k.*,d.* from keyword_net k left join net_picture_keyword n on k.guid = n.keyword_id " +
            "left join net_picture_keyword_dension d on d.np_id = n.np_id and  d.keyword_id = k.guid  where n.np_id = #{npid} and n.status_d2 = 1 and (d.dension != 10002 or d.dension is null)")
    List<NetKeyword> findEmoticonKeyword(@Param("npid") String npid);
}
