package com.hangnv.surgery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.SessionDetail;

@Repository
public interface SessionDetailRepository extends JpaRepository<SessionDetail, Long>{

	Optional<SessionDetail> findBySession_Id(Long sessionId);
	
}
