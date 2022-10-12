package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hangnv.surgery.dto.MaterialDto;
import com.hangnv.surgery.entity.Material;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    @Mapping(target = "prescriptions", expression = "java(null)")
    MaterialDto entityToDto(Material material);
    Material dtoToEntity(MaterialDto dto);

}
