package com.hpugs.elasticsearch.web;

import com.hpugs.base.common.result.Result;
import com.hpugs.elasticsearch.dto.PhoneModel;
import com.hpugs.elasticsearch.service.ElasticSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class ElasticSearchTestController {

    @Resource
    private ElasticSearchService elasticSearchService;

    @GetMapping("/search")
    public Result search(@RequestParam("title") String title) {
        return elasticSearchService.search(title);
    }

    @PostMapping("/save")
    public Result save(@RequestBody PhoneModel phoneModel) {
        List<PhoneModel> phoneModels = new ArrayList<>();
        phoneModels.add(phoneModel);
        return elasticSearchService.save(phoneModels);
    }

}
