package com.eureka.zuul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eureka.zuul.entity.LogEntity;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer>{
	

}
