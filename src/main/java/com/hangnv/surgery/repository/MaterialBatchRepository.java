package com.hangnv.surgery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.MaterialBatch;

@Repository
public interface MaterialBatchRepository extends JpaRepository<MaterialBatch, Long>, JpaSpecificationExecutor<MaterialBatch> {

}
