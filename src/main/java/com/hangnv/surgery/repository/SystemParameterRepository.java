package com.hangnv.surgery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.SystemParameter;

@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long>, JpaSpecificationExecutor<SystemParameter> {

	Optional<SystemParameter> findByParamName(String paramName);
	
}
