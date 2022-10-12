package com.hangnv.surgery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hangnv.surgery.common.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SessionDto extends BaseDto {

    private Long id;
    private String code;
    private String diagnosis;
    private String symptom;
    private String treatmentPlan;
    private String note;
    private Integer status;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    private LocalDate nextTime;
    private BigDecimal totalPrice;
    private String bloodPressure;
    private BigDecimal weight;
    private Integer heartbeat;
    private BigDecimal leftEye;
    private BigDecimal rightEye;

    private List<PrescriptionDto> prescriptionDtoList;
    private List<SessionDto> sessionDtoList;

    // for search
    private LocalDateTime searchFromDate;
    private String displaySearchFromDate;
    private LocalDateTime searchToDate;
    private String displaySearchToDate;
    
    private String patientName;

    private PatientDto patient;
}