package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hangnv.surgery.dto.MaterialBatchDto;
import com.hangnv.surgery.entity.MaterialBatch;

@Mapper(componentModel = "spring")
public interface MaterialBatchMapper {

	@Mapping(target = "material", expression = "java(null)")
    MaterialBatchDto entityToDto(MaterialBatch entity);
    MaterialBatch dtoToEntity(MaterialBatchDto dto);

}