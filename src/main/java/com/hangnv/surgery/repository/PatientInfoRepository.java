package com.hangnv.surgery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.PatientInfo;

@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long> {

}
