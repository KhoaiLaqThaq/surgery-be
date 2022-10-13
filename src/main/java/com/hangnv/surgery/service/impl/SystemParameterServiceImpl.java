package com.hangnv.surgery.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.SystemParameterDto;
import com.hangnv.surgery.entity.SystemParameter;
import com.hangnv.surgery.mapper.SystemParameterMapper;
import com.hangnv.surgery.repository.SystemParameterRepository;
import com.hangnv.surgery.service.ISystemParameterService;
import com.hangnv.surgery.specification.SystemParamSpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SystemParameterServiceImpl implements ISystemParameterService {

	@Autowired
	private SystemParameterRepository systemParameterRepository;
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private SystemParamSpecification systemParamSpecification;

	@Override
	public SystemParameterDto get(Long id) {
		log.info("------>Entering: get-by-id, id={}", id);
		return systemParameterRepository.findById(id).map(systemParameterMapper::entityToDto).orElse(null);
	}

	@Override
	public SystemParameterDto getByParamName(String paramName) {
		return systemParameterRepository.findByParamName(paramName).map(systemParameterMapper::entityToDto).orElse(null);
	}

	@Override
	public PageDto search(SystemParameterDto criteria) {
		log.info("--->Entering: search={}", criteria);
		Page<SystemParameter> page = systemParameterRepository.findAll(systemParamSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "id")));
		return PageDto.builder()
				.content(page.getContent().stream().map(systemParameterMapper::entityToDto).collect(Collectors.toList()))
				.number(page.getNumber())
				.numberOfElements(page.getNumberOfElements())
				.page(page.getNumber())
				.size(page.getSize())
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.build();
	}

	@Override
	public SystemParameterDto save(SystemParameterDto systemParamDto) {
		SystemParameter systemParam = Optional.ofNullable(systemParamDto).map(systemParameterMapper::dtoToEntity).orElse(null);
		if (systemParam != null) {
			return Optional.ofNullable(systemParameterRepository.save(systemParam)).map(systemParameterMapper::entityToDto).orElse(null);
		}
		return null;
	}
	
	
	
}
