package com.hangnv.surgery.dto;

import java.time.LocalDate;
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
public class PatientDto extends BaseDto {

    private Long id;
    private String code;
    private String name;
    private LocalDate dob;
    private String gender;
    private String phone;
    private String address;

    private PatientInfoDto patientInfo;
    private List<SessionDto> sessions;

    private String parentName;
}