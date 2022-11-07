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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hangnv.surgery.dto.SystemParameterDto;
import com.hangnv.surgery.service.ISystemParameterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class SystemParamController {

	@Autowired
	private ISystemParameterService iSystemParameterService;
	
	@PostMapping("/systemParam/search")
	public ResponseEntity<?> search(@RequestBody SystemParameterDto criteria) {
		log.info("---->Entering: search={}", criteria);
		return ResponseEntity.ok(iSystemParameterService.search(criteria));
	}
	
	@GetMapping("/systemParam/{id}")
	public ResponseEntity<?> get(@PathVariable String id) {
		log.info("------>Entering: get-by-id={}", id);
		if (StringUtils.isNumeric(id)) {
			return ResponseEntity.ok(iSystemParameterService.get(Long.parseLong(id)));
		} else 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be a numeric!");
	}
	
	@GetMapping("/systemParam")
	public ResponseEntity<?> getByName(@RequestParam(name = "name", required = true) String paramName) {
		log.info("---->Entering: get-by-name={}", paramName);
		return ResponseEntity.ok(iSystemParameterService.getByParamName(paramName));
	}
	
	@PostMapping("/systemParam")
	public ResponseEntity<?> save(@RequestBody SystemParameterDto systemParam) {
		log.info("Entering: save={}", systemParam);
		return ResponseEntity.ok(iSystemParameterService.save(systemParam));
	}
	
}
