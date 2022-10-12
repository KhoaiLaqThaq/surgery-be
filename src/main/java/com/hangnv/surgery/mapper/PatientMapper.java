package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.entity.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

	@Mapping(target = "sessions", expression = "java(null)")
    PatientDto entityToDto(Patient entity);
    Patient dtoToEntity(PatientDto dto);

}
