package com.hangnv.surgery.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.dto.SessionDetailDto;
import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.entity.Patient;
import com.hangnv.surgery.entity.Session;
import com.hangnv.surgery.entity.SessionDetail;
import com.hangnv.surgery.helpers.DateTimeHelper;
import com.hangnv.surgery.helpers.StringHelper;
import com.hangnv.surgery.mapper.PatientMapper;
import com.hangnv.surgery.mapper.SessionDetailMapper;
import com.hangnv.surgery.mapper.SessionMapper;
import com.hangnv.surgery.repository.PatientRepository;
import com.hangnv.surgery.repository.SessionDetailRepository;
import com.hangnv.surgery.repository.SessionRepository;
import com.hangnv.surgery.service.ISessionService;
import com.hangnv.surgery.specification.SessionSpecification;

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
		Page<Session> page = sessionRepository.findAll(sessionSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "createdDate")));
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
