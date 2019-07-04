package com.melink.open.api.mapper.netPicMapper;


import com.melink.open.api.annotation.mybatis.MyResult;
import com.melink.open.api.annotation.mybatis.MyResults;
import com.melink.open.api.model.NetKeywordWeight;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NetKeywordWeightMapper {
	@Select("select distinct * from keyword_net_weight where theme > 0 or chat > 0 or common > 0 or alipay > 0")
	List<NetKeywordWeight> specialWords();

	@Select("select * from keyword_net_weight where keyword_id = #{wordId} limit 1")
	NetKeywordWeight findByWordId(@Param("wordId") String wordId);

	@Select("select * from keyword_net_weight where theme > 0 order by theme desc limit 1")
	NetKeywordWeight getThemeWordMaxOrder();

	@Select("select * from keyword_net_weight where chat > 0 order by chat desc limit 1")
	NetKeywordWeight getChatWordMaxOrder();

	@Select("select * from keyword_net_weight where common > 0 order by common desc limit 1")
	NetKeywordWeight getCommonWordMaxOrder();

	@Select("select * from keyword_net_weight where theme > 0 order by theme asc")
	List<NetKeywordWeight> themeWords();

	@Select("select * from keyword_net_weight where chat > 0 order by chat asc")
	List<NetKeywordWeight> chatWords();

	@Select("select * from keyword_net_weight where common > 0 order by common asc")
	@MyResults(
			value = {
					@MyResult(column = "keywordId",property = "keyword",foreign = "guid",one ="com.melink.open.api.mapper.netPicMapper.KeywordNetMapper.findKeywordByKeywordIds")
			}
	)
	List<NetKeywordWeight> commonWords();

	@Select("select * from keyword_net_weight where alipay > 0 order by alipay desc limit 1")
    NetKeywordWeight getAlipayWordMaxOrder();

	@Select("select * from keyword_net_weight where alipay > 0 order by alipay desc")
	List<NetKeywordWeight> alipayWords();

	@Select("select * from keyword_net_weight where navi > 0 order by navi desc")
    List<NetKeywordWeight> naviWords();

	@Select("select * from keyword_net_weight where category > 0 order by category desc")
	@MyResults(
			value = {
					@MyResult(column = "keywordId",property = "keyword",foreign = "guid",one ="com.melink.open.api.mapper.netPicMapper.KeywordNetMapper.findKeywordByKeywordIds")
			}
	)
	List<NetKeywordWeight> categoryWords();

	@Select("select * from keyword_net_weight where hot > 0 order by hot desc")
	@MyResults(
			value = {
					@MyResult(column = "keywordId",property = "keyword",foreign = "guid",one ="com.melink.open.api.mapper.netPicMapper.KeywordNetMapper.findKeywordByKeywordIds")
			}
	)
    List<NetKeywordWeight> hotWords();

	@Select("select * from keyword_net_weight where hotgiftag > 0 order by hotgiftag desc")
	@MyResults(
			value = {
					@MyResult(column = "keywordId",property = "keyword",foreign = "guid",one ="com.melink.open.api.mapper.netPicMapper.KeywordNetMapper.findKeywordByKeywordIds")
			}
	)
    List<NetKeywordWeight> hotTagWords();
}