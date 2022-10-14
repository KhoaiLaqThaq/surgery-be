package com.hangnv.surgery.service.impl;

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
import com.hangnv.surgery.constant.SymbolEnum;
import com.hangnv.surgery.dto.MaterialBatchDto;
import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.entity.Material;
import com.hangnv.surgery.entity.MaterialBatch;
import com.hangnv.surgery.entity.MaterialType;
import com.hangnv.surgery.helpers.StringHelper;
import com.hangnv.surgery.mapper.MaterialBatchMapper;
import com.hangnv.surgery.mapper.MaterialMapper;
import com.hangnv.surgery.repository.MaterialBatchRepository;
import com.hangnv.surgery.repository.MaterialRepository;
import com.hangnv.surgery.repository.MaterialTypeRepository;
import com.hangnv.surgery.service.IMaterialBatchService;
import com.hangnv.surgery.specification.MaterialBatchSpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MaterialBatchServiceImpl implements IMaterialBatchService {
	
	@Autowired
	private MaterialBatchRepository materialBatchRepository;
	@Autowired
	private MaterialBatchMapper materialBatchMapper;
	@Autowired
	private MaterialBatchSpecification materialBatchSpecification;
	
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private MaterialMapper materialMapper;
	@Autowired
	private MaterialTypeRepository materialTypeRepository;

	@Override
	public List<MaterialBatchDto> gets() {
		return materialBatchRepository.findAll().stream().map(materialBatchMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public MaterialBatchDto get(Long id) {
		MaterialBatch materialBatch = materialBatchRepository.findById(id).get();
		if (materialBatch != null) {
			MaterialDto material = materialRepository.findById(materialBatch.getMaterial().getId()).map(materialMapper::entityToDto).orElse(new MaterialDto());
			MaterialBatchDto materialBatchDto = Optional.ofNullable(materialBatch).map(materialBatchMapper::entityToDto).orElse(null);
			materialBatchDto.setMaterial(material);
			return materialBatchDto;
		}
		return null;
	}

	@Override
	public MaterialBatchDto save(MaterialBatchDto materialBatchDto) {
		MaterialBatch materialBatch = Optional.ofNullable(materialBatchDto).map(materialBatchMapper::dtoToEntity).orElse(null);
		try {
			if (materialBatchDto != null) {
				if (materialBatchDto.getId() != null) {
					
				} else {
					if (StringUtils.isNoneBlank(materialBatchDto.getMaterialName())) {
						log.info("---> materialName: {}", materialBatchDto.getMaterialName());
						String[] names = StringUtils.split(materialBatchDto.getMaterialName(), SymbolEnum.SPACE.val);

						Material material = new Material();
						material.setName(materialBatchDto.getMaterialName());
						material.setCode(StringHelper.stripAccents(names[0] + new Date().getTime()));
						material.setComposition("Updating...");
						material.setTotal(materialBatchDto.getAmount());
						material.setPrice(materialBatchDto.getPrice());
						material.setSales(materialBatchDto.getSales());
						material.setUnit(materialBatchDto.getUnit());
						if (materialBatchDto.getMaterialTypeId() != null) {
							MaterialType materialType = materialTypeRepository.findById(materialBatchDto.getMaterialTypeId()).orElse(null);
							if (materialType != null) {
								material.setMaterialType(materialType);
							}
						}
						material = materialRepository.save(material);
						materialBatch.setMaterial(material);
						return Optional.ofNullable(materialBatchRepository.save(materialBatch)).map(materialBatchMapper::entityToDto).orElse(null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PageDto search(MaterialBatchDto criteria) {
		log.info("====> criteria: {}", criteria);
		Page<MaterialBatch> page = materialBatchRepository.findAll(materialBatchSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "id")));
		List<MaterialBatchDto> materialBatchs = new ArrayList<MaterialBatchDto>();
		if (page.getContent() != null) {
			page.getContent().stream().forEach(item -> {
				MaterialBatchDto materialBatch = Optional.ofNullable(item).map(materialBatchMapper::entityToDto).orElse(null);
				if (item.getMaterial() != null) {
					MaterialDto material = Optional.ofNullable(item.getMaterial()).map(materialMapper::entityToDto).orElse(null);
					materialBatch.setMaterial(material);
				}
				materialBatchs.add(materialBatch);
			});
		}
		
		
		return PageDto.builder()
				.content(materialBatchs)
				.number(page.getNumber())
				.numberOfElements(page.getNumberOfElements())
				.page(page.getNumber())
				.size(page.getSize())
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.build();
	}
}
