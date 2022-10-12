package com.hangnv.surgery.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.dto.PatientInfoDto;
import com.hangnv.surgery.entity.Patient;
import com.hangnv.surgery.entity.PatientInfo;
import com.hangnv.surgery.mapper.PatientMapper;
import com.hangnv.surgery.repository.PatientRepository;
import com.hangnv.surgery.service.IPatientService;
import com.hangnv.surgery.specification.PatientSpecification;

@Service
@Transactional
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PatientMapper patientMapper;
	@Autowired
	private PatientSpecification patientSpecification;
	
	@Override
	public List<PatientDto> gets() {
		return patientRepository.findAll().stream().map(patientMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public PatientDto get(Long id) {
		return patientRepository.findById(id).map(patientMapper::entityToDto).orElse(null);
	}

	@Override
	public PatientDto save(PatientDto patientDto) {
		Patient patient = Optional.ofNullable(patientDto).map(patientMapper::dtoToEntity).orElse(null);
		if (patient != null) {
			return Optional.ofNullable(patientRepository.save(patient)).map(patientMapper::entityToDto).orElse(null);
		}
		return null;
	}

	@Override
	public PageDto search(PatientDto criteria) {
		Page<Patient> page = patientRepository.findAll(patientSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "id")));
		return PageDto.builder()
						.content(page.getContent().stream().map(patientMapper::entityToDto).collect(Collectors.toList()))
						.number(page.getNumber())
						.numberOfElements(page.getNumberOfElements())
						.page(page.getNumber())
						.size(page.getSize())
						.totalPages(page.getTotalPages())
						.totalElements(page.getTotalElements())
						.build();
	}

	@Override
	public PatientInfoDto getPatientInfoByPatientId(Long patientId) {
		PatientDto patientInfo = patientRepository.findById(patientId).map(patientMapper::entityToDto).orElse(null);
		return patientInfo.getPatientInfo();
	}

}
