package com.hangnv.surgery.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hangnv.surgery.service.IPrescriptionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class PrescriptionController {

	@Autowired
	private IPrescriptionService iPrescriptionService;
	
	@GetMapping("/prescription/session/{id}")
	public ResponseEntity<?> getAllBySession(@PathVariable(name = "id") String sessionId) {
		log.info("------>Entering: get-all-by-session, sessionId={}", sessionId);
		if (StringUtils.isNumeric(sessionId)) {
			return ResponseEntity.ok(iPrescriptionService.getAllBySession(Long.parseLong(sessionId)));
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SessionId must be a numeric!");
	}
	
}
