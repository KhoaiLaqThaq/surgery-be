package com.hangnv.surgery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hangnv.surgery.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
}
