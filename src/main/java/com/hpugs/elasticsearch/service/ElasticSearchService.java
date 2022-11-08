package com.hpugs.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.hpugs.base.common.result.Result;
import com.hpugs.elasticsearch.dto.PhoneModel;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ElasticSearchService {

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    public Result search(String title) {

        //IndexCoordinates.of("steamed_fish_auction")获取索引定位
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", title))
                .build();

        SearchHits<PhoneModel> searchHits = elasticsearchOperations.search(query, PhoneModel.class, IndexCoordinates.of("search_test"));
        searchHits.getSearchHits().stream().map(SearchHit::getContent).map(JSONObject::toJSONString).forEach(System.out::println);
        return Result.buildSuccess(searchHits.getSearchHits().stream().map(SearchHit::getContent));
    }
}
