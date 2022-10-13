package com.hangnv.surgery.dto;



import com.hangnv.surgery.common.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SystemParameterDto extends BaseDto {

	private Long id;
	private String paramName;
	private String paramValue;
	private String description;
	private String type;
	
	private String keyword;
	
}
