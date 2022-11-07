package com.hangnv.surgery.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.constant.DatePatternEnum;
import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.dto.PrescriptionDto;
import com.hangnv.surgery.dto.SessionDetailDto;
import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.entity.Material;
import com.hangnv.surgery.entity.Patient;
import com.hangnv.surgery.entity.Prescription;
import com.hangnv.surgery.entity.Session;
import com.hangnv.surgery.entity.SessionDetail;
import com.hangnv.surgery.helpers.DateTimeHelper;
import com.hangnv.surgery.helpers.StringHelper;
import com.hangnv.surgery.mapper.MaterialMapper;
import com.hangnv.surgery.mapper.PatientMapper;
import com.hangnv.surgery.mapper.PrescriptionMapper;
import com.hangnv.surgery.mapper.SessionDetailMapper;
import com.hangnv.surgery.mapper.SessionMapper;
import com.hangnv.surgery.repository.MaterialRepository;
import com.hangnv.surgery.repository.PatientRepository;
import com.hangnv.surgery.repository.PrescriptionRepository;
import com.hangnv.surgery.repository.SessionDetailRepository;
import com.hangnv.surgery.repository.SessionRepository;
import com.hangnv.surgery.service.ISessionService;
import com.hangnv.surgery.specification.SessionSpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SessionServiceImpl implements ISessionService {
	
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private SessionMapper sessionMapper;
	@Autowired
	private SessionSpecification sessionSpecification;
	@Autowired
	private SessionDetailRepository sessionDetailRepository;
	@Autowired
	private SessionDetailMapper sessionDetailMapper;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PatientMapper patientMapper;
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	@Autowired
	private PrescriptionMapper prescriptionMapper;
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private MaterialMapper materialMapper;

	@Override
	public List<SessionDto> gets() {
		return sessionRepository.findAll().stream().map(sessionMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public SessionDto get(Long id) {
		Optional<Session> sessionOptional = sessionRepository.findById(id);
		if (sessionOptional.get() != null) {
			Session sessionEntity = sessionOptional.get();
			SessionDto session = sessionOptional.map(sessionMapper::entityToDto).orElse(null);
			if (session != null) {
				SessionDetailDto sessionDetail = sessionDetailRepository.findBySession_Id(session.getId()).map(sessionDetailMapper::entityToDto).orElse(null);
				session.setSessionDetail(sessionDetail);
				if (sessionEntity.getPatient() != null) {
					PatientDto patient = patientRepository.findById(sessionEntity.getPatient().getId()).map(patientMapper::entityToDto).orElse(null);
					session.setPatient(patient);
				}
				// get Prescriptions
				List<PrescriptionDto> prescriptionList = new ArrayList<>();
				List<Prescription> prescriptions = prescriptionRepository.findBySession_Id(id);
				prescriptions.stream().forEach(p -> {
					PrescriptionDto prescription = Optional.ofNullable(p).map(prescriptionMapper::entityToDto).orElse(null);
					if (prescription != null) {
						MaterialDto material = Optional.ofNullable(p.getMaterial()).map(materialMapper::entityToDto).orElse(null);
						prescription.setMaterial(material);
					}
					prescriptionList.add(prescription);
				});
				session.setPrescriptions(prescriptionList);
			}
			return session;
		}
		
		return null;
	}

	@Override
	public SessionDto save(SessionDto sessionDto) {
		Session session = Optional.ofNullable(sessionDto).map(sessionMapper::dtoToEntity).orElse(null);
		if (session != null) {
			// patient
			if (session.getPatient() != null) {
				Patient patient = Optional.ofNullable(sessionDto.getPatient()).map(patientMapper::dtoToEntity).orElse(null);
				if (patient != null) {
					patient.setCode(StringHelper.stripAccents("PP" + new Date().getTime()));
					if (StringUtils.isNoneBlank(sessionDto.getPatient().getDisplayDob())) {
						LocalDate dob = DateTimeHelper.stringToLocalDate(sessionDto.getPatient().getDisplayDob(), DatePatternEnum.DD_MM_YYYY_1.pattern);
						patient.setDob(dob);
					}
				}
				patient = patientRepository.save(patient);
				session.setPatient(patient);
			}
			
			// session
			if (StringUtils.isBlank(session.getCode())) {
				session.setCode(StringHelper.stripAccents("SS" + new Date().getTime()));
			}
			if (StringUtils.isNoneBlank(sessionDto.getDisplayNextTime())) {
				LocalDate nextTime = DateTimeHelper.stringToLocalDate(sessionDto.getDisplayNextTime(), DatePatternEnum.DD_MM_YYYY_1.pattern);
				session.setNextTime(nextTime);
			}
			session = sessionRepository.save(session);
			
			// session_detail
			if (sessionDto.getSessionDetail() != null) {
				SessionDetail sessionDetail = Optional.ofNullable(sessionDto.getSessionDetail()).map(sessionDetailMapper::dtoToEntity).orElse(null);
				sessionDetail.setSession(session);
				sessionDetail = sessionDetailRepository.save(sessionDetail);
			}
			
			List<Prescription> prescriptions = sessionDto.getPrescriptions().stream().map(prescriptionMapper::dtoToEntity).collect(Collectors.toList());
			if (prescriptions != null && prescriptions.size() > 0) {
				for (Prescription p : prescriptions) {
					p.setSession(session);
					Material material = materialRepository.findById(p.getMaterial().getId()).get();
					Integer materialTotal = material.getTotal() - p.getAmount();
					
					// Trường hợp tồn kho nhiều hơn trong đơn thuốc
					if (materialTotal >= 0) {
						material.setTotal(materialTotal);
						material = materialRepository.save(material);
					} else {
						// Trường hợp tồn kho không đủ nhưng trong lô nhập khác còn
						material.setTotal(0);
						material = materialRepository.save(material);
						List<MaterialDto> materials = materialRepository.findByName(material.getName()).stream().map(materialMapper::entityToDto).collect(Collectors.toList());
						if (materials.size() > 1) {
							final Long materialId = material.getId();
							List<MaterialDto> materialList = materials
									.stream()
									.filter(m -> m.getId() != materialId).collect(Collectors.toList());
							log.info("materialFilter: {}", materialList);
							if (materialList != null && materialList.size() > 0) {
								MaterialDto materialDto = materialList.get(0);
								if (materialDto != null && materialDto.getSales() != null) {
									Integer amount = Math.abs(materialTotal);
									Prescription prescription = new Prescription();
									prescription.setAmount(amount);
									prescription.setDosage(p.getDosage());
									prescription.setUnit(materialDto.getUnit());
									prescription.setTotalPrice(materialDto.getSales().multiply(new BigDecimal(amount)));
									prescription.setNote(p.getNote());
									prescription.setMaterial(Optional.ofNullable(materialDto).map(materialMapper::dtoToEntity).orElse(null));
									prescription.setSession(session);
									prescriptions.add(prescription);
								}
							}
						}
						
						// set amount for prescription
						p.setAmount(material.getTotal());
					}
					
				}
				prescriptionRepository.saveAll(prescriptions);
			}
			
			return Optional.ofNullable(session).map(sessionMapper::entityToDto).orElse(null);
		}
		return null;
	}

	@Override
	public PageDto search(SessionDto criteria) {
		if (StringUtils.isNoneBlank(criteria.getDisplaySearchFromDate())) {
			LocalDate fromDate = DateTimeHelper.stringToLocalDate(criteria.getDisplaySearchFromDate(), DatePatternEnum.DD_MM_YYYY_1.pattern);
			criteria.setSearchFromDate(fromDate.atStartOfDay());
		}
		if (StringUtils.isNoneBlank(criteria.getDisplaySearchToDate())) {
			LocalDate toDate = DateTimeHelper.stringToLocalDate(criteria.getDisplaySearchToDate(), DatePatternEnum.DD_MM_YYYY_1.pattern);
			criteria.setSearchToDate(toDate.atTime(LocalTime.MAX));
		}
		Page<Session> page = sessionRepository.findAll(sessionSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.Direction.DESC, "createdDate")));
		return PageDto.builder()
						.content(page.getContent().stream().map(sessionMapper::entityToDto).collect(Collectors.toList()))
						.number(page.getNumber())
						.numberOfElements(page.getNumberOfElements())
						.page(page.getNumber())
						.size(page.getSize())
						.totalPages(page.getTotalPages())
						.totalElements(page.getTotalElements())
						.build();
	}

	@Override
	public List<SessionDto> getsByPatientId(Long patientId) {
		return sessionRepository.findByPatient_Id(patientId).stream().map(sessionMapper::entityToDto).collect(Collectors.toList());
	}

}
