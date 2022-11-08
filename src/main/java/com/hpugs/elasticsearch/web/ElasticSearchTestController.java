package com.hpugs.elasticsearch.web;

import com.hpugs.base.common.result.Result;
import com.hpugs.elasticsearch.service.ElasticSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ElasticSearchTestController {

    @Resource
    private ElasticSearchService elasticSearchService;

    @GetMapping("/search")
    public Result search(@RequestParam("title") String title) {
        return elasticSearchService.search(title);
    }

}
