package com.venesa.service;

import com.venesa.entity.LogEntity;

import java.util.List;

public interface LogService {
	void save(LogEntity logEntity);
	List<LogEntity> getAll();
	void deleteById(long id);

}
