package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.PatientDto;
import com.hangnv.surgery.dto.PatientInfoDto;

public interface IPatientService {
	List<PatientDto> gets();
	PatientDto get(Long id);
	PatientDto save(PatientDto patientDto);
	PageDto search(PatientDto criteria);
	PatientInfoDto getPatientInfoByPatientId(Long patientId);
}
