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

import com.hangnv.surgery.dto.MaterialTypeDto;
import com.hangnv.surgery.service.IMaterialTypeService;

@RestController
@RequestMapping("/api")
public class MaterialTypeController {

	@Autowired
	private IMaterialTypeService iMaterialTypeService;
	
	@GetMapping("/materialType/list")
	public ResponseEntity<?> gets() {
		return ResponseEntity.ok(iMaterialTypeService.gets());
	}
	
	@GetMapping("/materialType/{id}")
	public ResponseEntity<?> get(@PathVariable(name = "id") String id) {
		if (StringUtils.isNumeric(id)) {
			MaterialTypeDto materialType = iMaterialTypeService.get(Long.parseLong(id));
			if (materialType != null) 
				return ResponseEntity.ok(materialType);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material is not found!");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
	}
	
	@PostMapping("/materialType/search")
	public ResponseEntity<?> search(@RequestBody MaterialTypeDto criteria) {
		return ResponseEntity.ok(iMaterialTypeService.search(criteria));
	}
	
	@PostMapping("/materialType")
	public ResponseEntity<?> save(@RequestBody MaterialTypeDto materialTypeDto) {
		return ResponseEntity.ok(iMaterialTypeService.save(materialTypeDto));
	}
	
}
