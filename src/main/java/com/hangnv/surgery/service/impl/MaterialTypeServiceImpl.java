package com.hangnv.surgery.service.impl;

import java.text.Normalizer;
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
import com.hangnv.surgery.constant.SymbolEnum;
import com.hangnv.surgery.dto.MaterialTypeDto;
import com.hangnv.surgery.entity.MaterialType;
import com.hangnv.surgery.helpers.StringHelper;
import com.hangnv.surgery.mapper.MaterialTypeMapper;
import com.hangnv.surgery.repository.MaterialTypeRepository;
import com.hangnv.surgery.service.IMaterialTypeService;
import com.hangnv.surgery.specification.MaterialTypeSpecification;

@Service
@Transactional
public class MaterialTypeServiceImpl implements IMaterialTypeService{

	@Autowired
	private MaterialTypeRepository materialTypeRepository;
	@Autowired
	private MaterialTypeMapper materialTypeMapper;
	@Autowired
	private MaterialTypeSpecification materialTypeSpecification;
	
	@Override
	public List<MaterialTypeDto> gets() {
		return materialTypeRepository.findAll().stream().map(materialTypeMapper::entityToDto).collect(Collectors.toList());
	}
	
	@Override
	public MaterialTypeDto get(Long id) {
		return materialTypeRepository.findById(id).map(materialTypeMapper::entityToDto).orElse(null);
	}
	
	@Override
	public PageDto search(MaterialTypeDto criteria) {
		Page<MaterialType> page = materialTypeRepository.findAll(materialTypeSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "id")));
		return PageDto.builder()
				.content(page.getContent().stream().map(materialTypeMapper::entityToDto).collect(Collectors.toList()))
				.number(page.getNumber())
				.numberOfElements(page.getNumberOfElements())
				.page(page.getNumber())
				.size(page.getSize())
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.build();
	}
	
	@Override
	public MaterialTypeDto save(MaterialTypeDto materialTypeDto) {
		MaterialType materialType = Optional.ofNullable(materialTypeDto).map(materialTypeMapper::dtoToEntity).orElse(null);
		if (materialType != null) {
			if (StringUtils.isBlank(materialType.getCode())) {
                String[] names = StringUtils.split(materialType.getName(), SymbolEnum.SPACE.val);
                materialType.setCode(StringHelper.stripAccents(names[0]) + new Date().getTime());
            }
			return Optional.ofNullable(materialTypeRepository.save(materialType)).map(materialTypeMapper::entityToDto).orElse(null);
		}
		return null;
	}
	

}
