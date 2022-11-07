package com.hangnv.surgery.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class DashboardController {

	@GetMapping("/previewReport")
	public ResponseEntity<?> getPreviewReport(HttpServletRequest request) {
		log.info("--------->Entering: get-preview-report()");
		
		return ResponseEntity.of(null);
	}
}
