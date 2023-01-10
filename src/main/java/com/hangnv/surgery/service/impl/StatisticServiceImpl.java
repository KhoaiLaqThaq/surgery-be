package com.hangnv.surgery.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.dto.StatisticChartDto;
import com.hangnv.surgery.entity.Material;
import com.hangnv.surgery.entity.Prescription;
import com.hangnv.surgery.mapper.SessionMapper;
import com.hangnv.surgery.repository.MaterialBatchRepository;
import com.hangnv.surgery.repository.PrescriptionRepository;
import com.hangnv.surgery.repository.SessionRepository;
import com.hangnv.surgery.search.StatisticDto;
import com.hangnv.surgery.service.IStatisticService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class StatisticServiceImpl implements IStatisticService {
	
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private SessionMapper sessionMapper;
	@Autowired
	private MaterialBatchRepository materialBatchRepository;
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Override
	public StatisticDto getStatistic(StatisticDto query) {
		log.info("process:get-statistic, query={}", query);
		if (query != null) {
			List<LocalDateTime> range = query.getRange();
			long totalSession = 0;
			long totalReceive = 0;
			StatisticDto responseData = new StatisticDto();
			responseData.setTotalRevenue(BigDecimal.ZERO);
			List<SessionDto> sessions = new ArrayList<>();
			
			if (range != null && range.size() > 1) {
				log.info("filter range");
				LocalDateTime startDate = range.get(0);
				LocalDateTime endDate = range.get(1);
				sessions = sessionRepository
						.findByCreatedDateBetween(startDate, endDate)
						.stream().map(sessionMapper::entityToDto).collect(Collectors.toList());
				// xem lai cho nay
				try {
					totalReceive = materialBatchRepository.calulateTotalReceiveBetween(startDate, endDate);
				} catch (Exception e) {
					totalReceive = 0;
				}
			} else {
				log.info("default filter statistic data");
				// default value
				sessions = sessionRepository
						.findAll().stream().map(sessionMapper::entityToDto).collect(Collectors.toList());
				totalReceive = materialBatchRepository.calulateTotalReceive();
			}
			if (sessions != null) {
				sessions.stream().forEach(item -> {
					List<Prescription> prescriptions = prescriptionRepository.findBySession_Id(item.getId());
					if (prescriptions != null) {
						prescriptions.stream().forEach(p -> {
							Material material = p.getMaterial();
							if (material != null) {
								// TODO: Calculate revenue per material
								BigDecimal revenue = material.getSales().subtract(material.getPrice());
								// TODO: Add revenue in totalRevenue
								BigDecimal totalRevenue = responseData.getTotalRevenue().add(revenue);
								responseData.setTotalRevenue(totalRevenue);
							}
						});
					}
				});
			}
			totalSession = sessions.size();
			// calculate outcome
			// TODO: Cause just only exist a patient per session
			responseData.setTotalPatient(totalSession);
			responseData.setTotalSession(totalSession);
			responseData.setTotalReceive(new BigDecimal(totalReceive));
			return responseData;
		}
		
		return new StatisticDto();
	}

	@Override
	public StatisticChartDto getStatisticChart() {
		List<Integer> months = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		
		return null;
	}
}
