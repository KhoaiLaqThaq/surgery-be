package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.dto.PrescriptionDto;

public interface IPrescriptionService {

	PrescriptionDto get(Long id);
	List<PrescriptionDto> getAllBySession(Long sessionId);
	
}
