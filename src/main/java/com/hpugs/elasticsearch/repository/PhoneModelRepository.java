package com.hpugs.elasticsearch.repository;

import com.hpugs.elasticsearch.dto.PhoneModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author：xinge
 * @Date：2024/11/28 11:08
 * @Description：
 */
@Repository
public interface PhoneModelRepository extends ElasticsearchRepository<PhoneModel, String> {

    @Override
    Optional<PhoneModel> findById(String id);

}
