package com.venesa.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venesa.entity.LogEntity;
import com.venesa.repository.LogRepository;
import com.venesa.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public void save(LogEntity entity) {
		logRepository.save(entity);
	}
}
