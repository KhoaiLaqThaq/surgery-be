package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;

import com.hangnv.surgery.dto.SystemParameterDto;
import com.hangnv.surgery.entity.SystemParameter;

@Mapper(componentModel = "spring")
public interface SystemParameterMapper {
	
	SystemParameterDto entityToDto(SystemParameter entity);
	SystemParameter dtoToEntity(SystemParameterDto dto);

}
