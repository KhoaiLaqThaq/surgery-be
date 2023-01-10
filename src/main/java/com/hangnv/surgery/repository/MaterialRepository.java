package com.hangnv.surgery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

	List<Material> findByNameContainingIgnoreCaseAndTotalGreaterThan(String name, Integer total);
	List<Material> findByNameContainingIgnoreCase(String name);
	List<Material> findByNameLike(String name);
	
}
