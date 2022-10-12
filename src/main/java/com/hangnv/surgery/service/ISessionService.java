package com.hangnv.surgery.service;

import java.util.List;

import com.hangnv.surgery.common.PageDto;
import com.hangnv.surgery.dto.SessionDto;

public interface ISessionService {

	List<SessionDto> gets();
	SessionDto get(Long id);
	SessionDto save(SessionDto session);
	PageDto search(SessionDto criteria);
	List<SessionDto> getsByPatientId(Long patientId);
	
}
