package com.hangnv.surgery.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "session_detail")
public class SessionDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "blood_pressure")
	private String bloodPressure;
	private BigDecimal weight;
	private Integer heartbeat;
	
	@Column(name = "left_eye")
	private BigDecimal leftEye;
	@Column(name = "right_eye")
	private BigDecimal rightEye;
	@Column(name = "left_sph")
	private String leftSPH;
	@Column(name = "right_sph")
	private String rightSPH;
	@Column(name = "left_cyl")
	private String leftCYL;
	@Column(name = "right_cyl")
	private String rightCYL;
	@Column(name = "left_axis")
	private String leftAXIS;
	@Column(name = "right_axis")
	private String rightAXIS;
	@Column(name = "left_add")
	private String leftADD;
	@Column(name = "right_add")
	private String rightADD;
	@Column(name = "left_pd")
	private String leftPD;
	@Column(name = "right_pd")
	private String rightPD;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "session_id")
	private Session session;

}
