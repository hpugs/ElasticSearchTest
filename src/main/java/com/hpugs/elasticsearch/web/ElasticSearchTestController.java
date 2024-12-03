package com.hpugs.elasticsearch.web;

import com.hpugs.base.common.result.Result;
import com.hpugs.elasticsearch.dto.PhoneModel;
import com.hpugs.elasticsearch.service.ElasticSearchService;
import com.hpugs.elasticsearch.service.PhoneModleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
public class ElasticSearchTestController {

    @Resource
    private ElasticSearchService elasticSearchService;
    @Resource
    private PhoneModleService phoneModleService;

    @GetMapping("/search")
    public Result<List<PhoneModel>> search(@RequestParam(value = "title", required = false) String title) {
        return elasticSearchService.search(title);
    }

    @GetMapping("/all")
    public Result<List<PhoneModel>> all() {
        List<PhoneModel> phoneModels = phoneModleService.queryPhoneModelList();
        return Result.buildSuccess(phoneModels);
    }

    @GetMapping("/findById")
    public Result<PhoneModel> findById(@RequestParam String id) {
        PhoneModel phoneModel = phoneModleService.findById(id);
        return Result.buildSuccess(phoneModel);
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
