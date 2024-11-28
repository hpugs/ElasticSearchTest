package com.hpugs.elasticsearch.service;

import com.hpugs.elasticsearch.dto.PhoneModel;
import com.hpugs.elasticsearch.repository.PhoneModelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author：xinge
 * @Date：2024/11/28 11:10
 * @Description：
 */
@Service
public class PhoneModleService {

    @Resource
    private PhoneModelRepository phoneModelRepository;

    public List<PhoneModel> queryPhoneModelList() {
        Iterable<PhoneModel> all = phoneModelRepository.findAll();
        List<PhoneModel> phoneModelList = new ArrayList<>();
        all.forEach(o -> phoneModelList.add(o));
        return phoneModelList;
    }

    public PhoneModel findById(String id) {
        Optional<PhoneModel> optional = phoneModelRepository.findById(id);
        return optional.orElseGet(PhoneModel::new);
    }
}
