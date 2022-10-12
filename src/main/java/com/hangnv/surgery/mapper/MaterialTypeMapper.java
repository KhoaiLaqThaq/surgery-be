package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;

import com.hangnv.surgery.dto.MaterialTypeDto;
import com.hangnv.surgery.entity.MaterialType;

@Mapper(componentModel = "spring")
public interface MaterialTypeMapper {

    MaterialTypeDto entityToDto(MaterialType entity);
    MaterialType dtoToEntity(MaterialTypeDto dto);

}
