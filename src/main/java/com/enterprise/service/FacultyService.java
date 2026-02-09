package com.enterprise.service;

import java.util.List;

import com.enterprise.dto.request.FacultyRequest;
import com.enterprise.dto.request.FacultySubjectAssignRequest;
import com.enterprise.dto.response.FacultyResponse;
import com.enterprise.entity.FacultySubject;
import com.enterprise.enums.FacultyStatus;

public interface FacultyService {

	FacultyResponse addFaculty(FacultyRequest facreq);
	List<FacultyResponse> getAllFaculties();
	FacultyResponse getFacultyById(String Id);
	List<FacultyResponse> getFacultyByDept(String deptId);
	FacultyResponse changeStatus(String factId,FacultyStatus status);
	FacultySubject assignSubject(FacultySubjectAssignRequest assignReq);
	
}
