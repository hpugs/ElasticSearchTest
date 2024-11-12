package com.hpugs.elasticsearch.web;

import com.hpugs.base.common.result.Result;
import com.hpugs.elasticsearch.dto.PhoneModel;
import com.hpugs.elasticsearch.service.ElasticSearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;

@RestController
public class ElasticSearchTestController {

    @Resource
    private ElasticSearchService elasticSearchService;

    @GetMapping("/search")
    public Result search(@RequestParam(value = "title", required = false) String title) {
        return elasticSearchService.search(title);
    }

    @PostMapping("/save")
    public Result save(@RequestBody PhoneModel phoneModel) {
        return elasticSearchService.save(Collections.singletonList(phoneModel));
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam String id) {
        return elasticSearchService.delete(id);
    }

    @PostMapping("/update")
    public Result update(@RequestBody PhoneModel phoneModel) {
        return elasticSearchService.update(phoneModel);
    }

}
