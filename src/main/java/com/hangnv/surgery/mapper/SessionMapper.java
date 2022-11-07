package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hangnv.surgery.dto.SessionDto;
import com.hangnv.surgery.entity.Session;

@Mapper(componentModel = "spring")
public interface SessionMapper {

	@Mapping(target = "patient", expression = "java(null)")
	@Mapping(target = "prescriptions", expression = "java(null)")
    SessionDto entityToDto(Session entity);
    Session dtoToEntity(SessionDto dto);

}
