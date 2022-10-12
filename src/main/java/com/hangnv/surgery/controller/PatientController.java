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

import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.service.IPatientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class PatientController {

	@Autowired
	private IPatientService iPatientService;
	
	@GetMapping("/patient/list")
	public ResponseEntity<?> gets() {
		log.info("------->entering: gets");
		return ResponseEntity.ok(iPatientService.gets());
	}
	
	@GetMapping("/patient/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") String id) {
		if (StringUtils.isNumeric(id)) {
			return ResponseEntity.ok(iPatientService.get(Long.parseLong(id)));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
		}
	}
	
	@PostMapping("/patient/search")
	public ResponseEntity<?> search(@RequestBody PatientDto criteria) {
		log.info("------->entering: search(), {}", criteria);
		
		return ResponseEntity.ok(iPatientService.search(criteria));
	}
	
	@GetMapping("/patientInfo/{id}")
	public ResponseEntity<?> getPatientInfoById(@PathVariable(name = "id", required = true) String patientId) {
		log.info("------>entering: get-patientInfoById, id={}", patientId);
		if (StringUtils.isNumeric(patientId)) {
			return ResponseEntity.ok(iPatientService.getPatientInfoByPatientId(Long.parseLong(patientId)));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Id must be a numeric!");
		}
	}
	
}
