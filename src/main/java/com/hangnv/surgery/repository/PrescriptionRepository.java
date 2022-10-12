package com.hangnv.surgery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	List<Prescription> findBySession_Id(Long sessionId);
	
}
