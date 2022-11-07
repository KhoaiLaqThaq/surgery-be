package com.hangnv.surgery.controller;

import java.util.List;

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

import com.hangnv.surgery.constant.MessageEnum;
import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.service.IMaterialService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class MaterialController extends BaseController {

	@Autowired
	private IMaterialService iMaterialService;
	
	@GetMapping("/material/list")
	public ResponseEntity<?> gets() {
		log.info("---->Entering: gets-material");
		return ResponseEntity.ok(iMaterialService.gets());
	}
	
	@GetMapping("/material/{id}")
	public ResponseEntity<?> get(@PathVariable String id) {
		log.info("---->Entering: get-by-id={}", id);
		
		if (StringUtils.isNumeric(id)) {
			MaterialDto material = iMaterialService.get(Long.parseLong(id));
			if (material != null)
				return ResponseEntity.ok(material);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material is not found!");
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be a numeric!");
	}
	
	@PostMapping("/material/search")
	public ResponseEntity<?> search(@RequestBody MaterialDto criteria) {
		log.info("---->Entering: search={}", criteria);
		return ResponseEntity.ok(iMaterialService.search(criteria));
	}
	
	@PostMapping("/material")
	public ResponseEntity<?> save(@RequestBody MaterialDto materialDto) {
		log.info("----->Entering: save={}", materialDto);
		try {
			return ResponseEntity.ok(iMaterialService.save(materialDto));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping("/material/name")
	public ResponseEntity<?> getByName(@RequestBody MaterialDto criteria) {
		log.info("------>Entering: material-by-name: {}", criteria);
		try {
			List<MaterialDto> materials = iMaterialService.getsByName(criteria);
			if (materials != null && materials.size() > 0)
				return ResponseEntity.status(HttpStatus.OK).body(materials);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageEnum.INTERNAL_SERVER_ERROR.message);
		}
	}
	
}
