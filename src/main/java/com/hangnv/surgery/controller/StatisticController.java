package com.hangnv.surgery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hangnv.surgery.search.StatisticDto;
import com.hangnv.surgery.service.IStatisticService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
	
	@Autowired
	private IStatisticService iStatisticService;

	@PostMapping("/dashboard")
	public ResponseEntity<?> getStatistic(@RequestBody StatisticDto query) {
		log.info("process=get-statistic, query={}", query);
		
		return ResponseEntity.ok(iStatisticService.getStatistic(query));
	}
	
}
