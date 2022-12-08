package com.hangnv.surgery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session , Long>, JpaSpecificationExecutor<Session>{

	List<Session> findByPatient_Id(Long id);
	List<Session> findFirst5ByOrderByIdDesc();
	
}
