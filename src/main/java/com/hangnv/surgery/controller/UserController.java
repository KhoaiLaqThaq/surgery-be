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

import com.hangnv.surgery.constant.MessageEnum;
import com.hangnv.surgery.dto.UserDto;
import com.hangnv.surgery.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService iUserService;
	
	@GetMapping("/user/list")
	public ResponseEntity<?> gets() {
		log.info("Entering: gets()...");
		return ResponseEntity.ok(iUserService.gets());
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> get(@PathVariable String id) {
		log.info("----->Entering: get-by-id={}", id);
		if (StringUtils.isNumeric(id)) {
			UserDto user = iUserService.get(Long.parseLong(id));
			if (user != null)
				return ResponseEntity.ok(user);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageEnum.NOT_FOUND.message);
		} else 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be a numeric!");
	}

	@PostMapping("/user/search")
	public ResponseEntity<?> search(@RequestBody UserDto criteria) {
		log.info("-------->Entering: search={}", criteria);
		return ResponseEntity.ok(iUserService.search(criteria));
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?> register(@RequestBody UserDto userDto, HttpServletRequest request) {
		log.info("--------> Entering: register={}", userDto);
		String accessUsername = getUsernameFromJwtToken(getToken(request));
		try {
			if (userDto.getId() != null)
				userDto.setModifiedBy(accessUsername);
			else
				userDto.setCreatedBy(accessUsername);
			return ResponseEntity.ok(iUserService.save(userDto));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
