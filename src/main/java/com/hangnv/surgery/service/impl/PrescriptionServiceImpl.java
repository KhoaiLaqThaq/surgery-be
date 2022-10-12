package com.hangnv.surgery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.dto.PrescriptionDto;
import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.entity.Prescription;
import com.hangnv.surgery.entity.Session;
import com.hangnv.surgery.mapper.MaterialMapper;
import com.hangnv.surgery.mapper.PrescriptionMapper;
import com.hangnv.surgery.mapper.SessionMapper;
import com.hangnv.surgery.repository.PrescriptionRepository;
import com.hangnv.surgery.service.IPrescriptionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PrescriptionServiceImpl implements IPrescriptionService {
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	@Autowired
	private PrescriptionMapper prescriptionMapper;
	@Autowired
	private SessionMapper sessionMapper;
	@Autowired
	private MaterialMapper materialMapper;

	@Override
	public PrescriptionDto get(Long id) {
		return prescriptionRepository.findById(id).map(prescriptionMapper::entityToDto).orElse(null);
	}

	@Override
	public List<PrescriptionDto> getAllBySession(Long sessionId) {
		log.info("-------->Entering: getAllBySession...");
		List<PrescriptionDto> prescriptionList = new ArrayList<>();
		List<Prescription> prescriptions = prescriptionRepository.findBySession_Id(sessionId);
		prescriptions.stream().forEach(p -> {
			PrescriptionDto prescription = Optional.ofNullable(p).map(prescriptionMapper::entityToDto).orElse(null);
			if (prescription != null) {
				SessionDto session = Optional.ofNullable(p.getSession()).map(sessionMapper::entityToDto).orElse(null);
				MaterialDto material = Optional.ofNullable(p.getMaterial()).map(materialMapper::entityToDto).orElse(null);
				prescription.setSession(session);
				prescription.setMaterial(material);
			}
			prescriptionList.add(prescription);
		});
		
		return prescriptionList;
	}

}
