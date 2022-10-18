package com.hangnv.surgery.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.hangnv.surgery.common.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto {

	private Long id;
	private String username;
	private String password;
	private String fullname;
	private Boolean enabled;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private String createdBy;
	private LocalDateTime createdDate;
	
	private Set<RoleDto> roles;
}
