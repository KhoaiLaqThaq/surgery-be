package com.hangnv.surgery.mapper;

import org.mapstruct.Mapper;

import com.hangnv.surgery.dto.UserDto;
import com.hangnv.surgery.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto entityToDto(User entity);
	User dtoToEntity(UserDto dto);
	
}
