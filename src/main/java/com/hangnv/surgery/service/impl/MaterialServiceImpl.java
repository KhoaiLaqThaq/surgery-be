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
import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.entity.Material;
import com.hangnv.surgery.mapper.MaterialMapper;
import com.hangnv.surgery.repository.MaterialRepository;
import com.hangnv.surgery.service.IMaterialService;
import com.hangnv.surgery.specification.MaterialSpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MaterialServiceImpl implements IMaterialService {
	
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private MaterialMapper materialMapper;
	@Autowired
	private MaterialSpecification materialSpecification;

	@Override
	public List<MaterialDto> gets() {
		return materialRepository.findAll().stream().map(materialMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public MaterialDto get(Long id) {
		return materialRepository.findById(id).map(materialMapper::entityToDto).orElse(null);
	}

	@Override
	public MaterialDto save(MaterialDto materialDto) {
		Material material = Optional.ofNullable(materialDto).map(materialMapper::dtoToEntity).orElse(null);
		if (material != null) {
			return Optional.ofNullable(materialRepository.save(material)).map(materialMapper::entityToDto).orElse(null);
		}
		return null;
	}

	@Override
	public PageDto search(MaterialDto criteria) {
		log.info("----> Entering: search-material, criteria={}", criteria);
		Page<Material> page = materialRepository.findAll(materialSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "id")));
		return PageDto.builder()
				.content(page.getContent().stream().map(materialMapper::entityToDto).collect(Collectors.toList()))
				.number(page.getNumber())
				.numberOfElements(page.getNumberOfElements())
				.page(page.getNumber())
				.size(page.getSize())
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.build();
	}

	@Override
	public List<MaterialDto> getsByName(MaterialDto criteria) {
		log.info("-----> Entering: gets-by-name: {}", criteria);
		return materialRepository.findByNameContainingIgnoreCaseAndTotalGreaterThan(criteria.getName(), 0)
				.stream().map(materialMapper::entityToDto).collect(Collectors.toList());
	}

}
