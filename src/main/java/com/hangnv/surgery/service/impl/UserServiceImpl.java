package com.hangnv.surgery.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.UserDto;
import com.hangnv.surgery.entity.User;
import com.hangnv.surgery.mapper.UserMapper;
import com.hangnv.surgery.repository.UserRepository;
import com.hangnv.surgery.service.IUserService;
import com.hangnv.surgery.specification.UserSpecification;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserSpecification userSpecification;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<UserDto> gets() {
		return userRepository.findAll().stream().map(userMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public UserDto get(Long id) {
		return userRepository.findById(id).map(userMapper::entityToDto).orElse(null);
	}

	@Override
	public PageDto search(UserDto criteria) {
		Page<User> page = userRepository.findAll(userSpecification.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(Sort.DEFAULT_DIRECTION, "id")));
		return PageDto.builder()
				.content(page.getContent().stream().map(userMapper::entityToDto).collect(Collectors.toList()))
				.number(page.getNumber())
				.numberOfElements(page.getNumberOfElements())
				.page(page.getNumber())
				.size(page.getSize())
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.build();
	}

	@Override
	public UserDto save(UserDto userDto) {
		if (userDto != null) {
			User user = Optional.ofNullable(userDto).map(userMapper::dtoToEntity).orElse(new User());
			if (StringUtils.isNoneBlank(userDto.getPassword()))
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			else
				user.setPassword(passwordEncoder.encode("1234567a@"));
			return Optional.ofNullable(userRepository.save(user)).map(userMapper::entityToDto).orElse(null);
		}
		return null;
	}

	
	
	
	
}
