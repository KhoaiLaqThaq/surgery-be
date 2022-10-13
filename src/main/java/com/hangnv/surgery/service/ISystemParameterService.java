package com.hangnv.surgery.service;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.SystemParameterDto;

public interface ISystemParameterService {

	SystemParameterDto get(Long id);
	SystemParameterDto getByParamName(String paramName);
	PageDto search(SystemParameterDto criteria);
	SystemParameterDto save(SystemParameterDto systemParamDto);
	
}
