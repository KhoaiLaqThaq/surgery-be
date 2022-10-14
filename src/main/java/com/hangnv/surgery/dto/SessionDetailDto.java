package com.hangnv.surgery.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SessionDetailDto {

	private Long id;
	private String bloodPressure;
	private BigDecimal weight;
	private Integer heartbeat;
	private BigDecimal leftEye;
	private BigDecimal rightEye;
	private String leftSPH;
	private String rightSPH;
	private String leftCYL;
	private String rightCYL;
	private String leftAXIS;
	private String rightAXIS;
	private String leftADD;
	private String rightADD;
	private String leftPD;
	private String rightPD;
	
}
