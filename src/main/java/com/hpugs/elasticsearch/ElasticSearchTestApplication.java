package com.hpugs.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.hpugs.elasticsearch.repository")
public class ElasticSearchTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchTestApplication.class, args);
    }

}
