package com.hpugs.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.hpugs.base.common.result.EnumError;
import com.hpugs.base.common.result.Result;
import com.hpugs.base.common.utils.CollectionUtil;
import com.hpugs.elasticsearch.EnumEsConst;
import com.hpugs.elasticsearch.dto.PhoneModel;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ElasticSearchService {

    @Resource
    private ElasticsearchOperations elasticsearchTemplate;

    public Result search(String title) {

        //IndexCoordinates.of("steamed_fish_auction")获取索引定位
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", title))
                .build();

        SearchHits<PhoneModel> searchHits = elasticsearchTemplate.search(query, PhoneModel.class, IndexCoordinates.of(EnumEsConst.PHONE_INDEX_NAME));
        searchHits.getSearchHits().stream().map(SearchHit::getContent).map(JSONObject::toJSONString).forEach(System.out::println);
        return Result.buildSuccess(searchHits.getSearchHits().stream().map(SearchHit::getContent));
    }

    public Result save(List<PhoneModel> list) {
        try{
            if(!CollectionUtil.isEmpty(list)){
                if(!elasticsearchTemplate.indexOps(PhoneModel.class).exists()){
                    IndexOperations indexOps = elasticsearchTemplate.indexOps(PhoneModel.class);
                    indexOps.create();
                    Document document= indexOps.createMapping(PhoneModel.class);
                    indexOps.putMapping(document);
                }
                elasticsearchTemplate.save(list);
            }
        }catch (Exception e){
            return Result.buildFail(EnumError.PARAMS_ERROR);
        }
        return Result.buildSuccess();
    }
}
