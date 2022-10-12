package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hangnv.surgery.dto.PrescriptionDto;
import com.hangnv.surgery.entity.Prescription;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

	@Mapping(target = "material", expression = "java(null)")
	@Mapping(target = "session", expression = "java(null)")
    PrescriptionDto entityToDto(Prescription entity);
    Prescription dtoToEntity(PrescriptionDto dto);

}