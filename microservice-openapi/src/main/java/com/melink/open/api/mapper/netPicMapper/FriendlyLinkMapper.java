package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.model.FriendlyLink;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FriendlyLinkMapper {

    @Select("select * from friendly_link order by display_order desc")
    List<FriendlyLink> findFriendlyLinkList();

}
