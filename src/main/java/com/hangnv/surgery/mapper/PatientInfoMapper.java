package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;

import com.hangnv.surgery.dto.PatientInfoDto;
import com.hangnv.surgery.entity.PatientInfo;

@Mapper(componentModel = "spring")
public interface PatientInfoMapper {

    PatientInfoDto entityToDto(PatientInfo entity);
    PatientInfo dtoToEntity(PatientInfoDto dto);

}
