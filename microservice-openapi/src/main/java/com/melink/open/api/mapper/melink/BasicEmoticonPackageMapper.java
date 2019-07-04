package com.melink.open.api.mapper.melink;

import com.github.pagehelper.Page;
import com.melink.open.api.mapper.melink.provider.BasicEmoticonPackageProvider;
import com.melink.open.api.model.BasicEmoticonPackage;
import org.apache.ibatis.annotations.*;

public interface BasicEmoticonPackageMapper {

    @SelectProvider(type = BasicEmoticonPackageProvider.class, method = "findListByPagination")
    Page<BasicEmoticonPackage> findListByPagination(@Param("p") int p, @Param("size") int size);

    @Select("select t1.guid,t1.name,t1.banner,t1.intro,t1.copyright,t1.cover,t1.is_emoji,count(t2.guid) emoji_count from basic_emoticon_package t1 inner join basic_emoticon t2 on t1.guid = t2.package_id where t1.guid=#{id} group by t1.guid")
    @Results(
            id = "package_detail",
            value = {
                    @Result(column = "guid", property = "basicEmoticons",
                            many = @Many(select = "com.melink.open.api.mapper.melink.BasicEmoticonMapper.findByPackageId"))
            }
    )
    BasicEmoticonPackage findbyId(@Param("id") String id);
}
