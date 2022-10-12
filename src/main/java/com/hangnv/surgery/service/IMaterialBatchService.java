package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.MaterialBatchDto;

public interface IMaterialBatchService {

	List<MaterialBatchDto> gets();
	MaterialBatchDto get(Long id);
	MaterialBatchDto save(MaterialBatchDto materialBatch);
	PageDto search(MaterialBatchDto criteria);
	
}
