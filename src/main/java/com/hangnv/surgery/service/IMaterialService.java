package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.MaterialDto;

public interface IMaterialService {
	
	List<MaterialDto> gets();
	MaterialDto get(Long id);
	MaterialDto save(MaterialDto material);
	PageDto search(MaterialDto criteria);
	List<MaterialDto> getsByName(MaterialDto criteria);
	
}
