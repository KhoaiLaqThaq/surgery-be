package com.hangnv.surgery.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.service.ISessionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class SessionController {

	@Autowired
	private ISessionService iSessionService;
	
	@GetMapping("/session/list")
	public ResponseEntity<?> gets() {
		log.info("------->Entering: gets()");
		
		return ResponseEntity.ok(iSessionService.gets());
	}
	
	@GetMapping("/session/{id}")
	public ResponseEntity<?> get(@PathVariable String id) {
		log.info("------->Entering: get-by-id={}", id);
		if (StringUtils.isNumeric(id)) {
			SessionDto session = iSessionService.get(Long.parseLong(id));
			if (session != null)
				return ResponseEntity.ok(session);
			else 
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session is not found!");
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be a numeric!");
	}
	
	@PostMapping("/session/search")
	public ResponseEntity<?> search(@RequestBody SessionDto criteria) {
		log.info("------->Entering: searching, criteria={}", criteria);
		return ResponseEntity.ok(iSessionService.search(criteria));
	}
	
	@PostMapping("/session")
	public ResponseEntity<?> save(@RequestBody SessionDto sessionDto) {
		log.info("----->Entering: save={}", sessionDto);
		
		try {
			return ResponseEntity.ok(iSessionService.save(sessionDto));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/session/patient/{id}")
	public ResponseEntity<?> getAllSessionByPatientId(@PathVariable(name = "id", required = true) String patientId) {
		log.info("----->Entering: get-all-session-by-patientId, id={}", patientId);
		if (StringUtils.isNumeric(patientId)) {
			return ResponseEntity.ok(iSessionService.getsByPatientId(Long.parseLong(patientId)));
		} else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Id must be a numeric!");
	}
}