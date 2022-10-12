package com.hangnv.surgery.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.hangnv.surgery.dto.MaterialBatchDto;
import com.hangnv.surgery.service.IMaterialBatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class MaterialBatchController extends BaseController {

	@Autowired
	private IMaterialBatchService iMaterialBatchService;
	
	@GetMapping("/materialBatch/list")
	public ResponseEntity<?> gets() {
		log.info("-------->entering: gets-materialBatch()");
		return ResponseEntity.ok(iMaterialBatchService.gets());
	}
	
	@GetMapping("/materialBatch/{id}")
	public ResponseEntity<?> get(@PathVariable String id) {
		log.info("------->Entering: get-by-id={}", id);
		if (StringUtils.isNumeric(id)) {
			return ResponseEntity.ok(iMaterialBatchService.get(Long.parseLong(id)));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be a numeric!");
		}
	}
	
	@PostMapping("/materialBatch/search")
	public ResponseEntity<?> search(@RequestBody MaterialBatchDto criteria) {
		log.info("------>Entering: search={}", criteria);
		return ResponseEntity.ok(iMaterialBatchService.search(criteria));
	}
	
	@PostMapping("/materialBatch")
	public ResponseEntity<?> save(@RequestBody MaterialBatchDto materialBatchDto, HttpServletRequest request) {
		log.info("------->Entering: save={}", materialBatchDto);
		String accessUsername = getUsernameFromJwtToken(getToken(request));
		try {
			if (materialBatchDto.getId() != null) {
				materialBatchDto.setModifiedBy(accessUsername);
			} else {
				materialBatchDto.setCreatedBy(accessUsername);
			}
			return ResponseEntity.ok(iMaterialBatchService.save(materialBatchDto));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
