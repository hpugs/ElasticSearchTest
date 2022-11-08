package com.hpugs.elasticsearch;

import com.hpugs.base.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebStatus {

    @GetMapping("/webStatus")
    public Result webStatus() {
        return Result.buildSuccess();
    }

}
