package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.MaterialTypeDto;

public interface IMaterialTypeService {
	
	List<MaterialTypeDto> gets();
	MaterialTypeDto get(Long id);
	PageDto search(MaterialTypeDto criteria);
	MaterialTypeDto save(MaterialTypeDto materialType);
	
}
