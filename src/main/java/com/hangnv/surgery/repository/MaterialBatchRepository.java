package com.hangnv.surgery.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.MaterialBatch;

@Repository
public interface MaterialBatchRepository extends JpaRepository<MaterialBatch, Long>, JpaSpecificationExecutor<MaterialBatch> {

	@Query("SELECT SUM(mb.price)*mb.amount FROM MaterialBatch mb")
	long calulateTotalReceive();
	
	@Query("SELECT SUM(mb.price)*mb.amount FROM MaterialBatch mb WHERE mb.createdDate BETWEEN ?1 AND ?2")
	long calulateTotalReceiveBetween(LocalDateTime startDate, LocalDateTime endDate);
}
