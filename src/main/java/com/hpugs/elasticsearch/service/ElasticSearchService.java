package com.hpugs.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.hpugs.base.common.result.EnumError;
import com.hpugs.base.common.result.Result;
import com.hpugs.base.common.utils.CollectionUtil;
import com.hpugs.elasticsearch.dto.PhoneModel;
import com.hpugs.elasticsearch.enums.EnumEsConst;
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
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
        if (CollectionUtil.isEmpty(list)) {
            return Result.buildFail("参数错误");
        }

        try {
            // 检查索引是否创建
            if (!elasticsearchTemplate.indexOps(PhoneModel.class).exists()) {
                IndexOperations indexOps = elasticsearchTemplate.indexOps(PhoneModel.class);
                indexOps.create();
                Document document = indexOps.createMapping(PhoneModel.class);
                indexOps.putMapping(document);
            }
            elasticsearchTemplate.save(list);
        } catch (Exception e) {
            return Result.buildFail(EnumError.PARAMS_ERROR);
        }
        return Result.buildSuccess();
    }

    public Result delete(String id) {
        if (elasticsearchTemplate.exists(id, IndexCoordinates.of(EnumEsConst.PHONE_INDEX_NAME))) {
            String result = elasticsearchTemplate.delete(id, IndexCoordinates.of(EnumEsConst.PHONE_INDEX_NAME));
            return Result.buildSuccess(result);
        }
        return Result.buildSuccess();
    }

    public Result update(PhoneModel phoneModel) {
        String id = phoneModel.getId();
        if (ObjectUtils.isEmpty(id)) {
            return Result.buildFail(EnumError.PARAMS_ERROR);
        }
        if (!elasticsearchTemplate.exists(id, IndexCoordinates.of(EnumEsConst.PHONE_INDEX_NAME))) {
            return Result.buildFail(EnumError.DATA_NOT_EXIST);
        }

        PhoneModel result = elasticsearchTemplate.save(phoneModel);
        return Result.buildSuccess(JSONObject.toJSONString(result));
    }
}
