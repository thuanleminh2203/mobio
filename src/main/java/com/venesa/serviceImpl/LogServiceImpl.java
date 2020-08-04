package com.venesa.serviceImpl;

import com.venesa.entity.LogEntity;
import com.venesa.repository.LogRepository;
import com.venesa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public void save(LogEntity entity) {
        logRepository.save(entity);
    }

    @Override
    @Cacheable(cacheNames = { "longCache" })
    public List<LogEntity> getAll() {
        System.out.println("=====getAlll Log============");
        return logRepository.findAll();
    }

    @Override
    @CacheEvict(value = "longCache" , allEntries = true)
    public void deleteById(long id) {
        logRepository.deleteById(id);
    }
}
