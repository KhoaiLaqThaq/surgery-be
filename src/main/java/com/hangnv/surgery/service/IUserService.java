package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.UserDto;

public interface IUserService {

	List<UserDto> gets();
	UserDto get(Long id);
	PageDto search(UserDto criteria);
	
	
	
}
