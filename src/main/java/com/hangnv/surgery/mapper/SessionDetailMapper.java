package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;

import com.hangnv.surgery.dto.SessionDetailDto;
import com.hangnv.surgery.entity.SessionDetail;

@Mapper(componentModel = "spring")
public interface SessionDetailMapper {

	SessionDetailDto entityToDto(SessionDetail entity);
	SessionDetail dtoToEntity(SessionDetailDto dto);
	
}
