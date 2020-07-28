package com.eureka.zuul.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eureka.zuul.entity.LogEntity;
import com.eureka.zuul.repository.LogRepository;
import com.eureka.zuul.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public void save(LogEntity entity) {
		logRepository.save(entity);
	}
}
