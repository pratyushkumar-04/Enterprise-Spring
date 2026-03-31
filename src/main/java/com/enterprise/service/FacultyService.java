package com.enterprise.service;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.enterprise.dto.request.FacultyModifyRequest;
import com.enterprise.dto.request.FacultyRequest;
//import com.enterprise.dto.request.SubjectFacultySectionAssignmentRequestDTO;
import com.enterprise.dto.response.FacultyResponse;
//import com.enterprise.entity.FacultySubject;
import com.enterprise.enums.FacultyStatus;

public interface FacultyService {

	FacultyResponse addFaculty(FacultyRequest facreq, MultipartFile image, MultipartFile cv);

	List<FacultyResponse> getAllFaculties();

	FacultyResponse getFacultyById(String Id);

	List<FacultyResponse> getFacultyByDept(String deptId);

	FacultyResponse changeStatus(String factId, FacultyStatus status);

//	FacultySubject assignSubject(SubjectFacultySectionAssignmentRequestDTO assignReq);
	
	FacultyResponse editFaculty(String id,FacultyModifyRequest req);
	
	Resource getCv(String Id) throws MalformedURLException;
	Resource getImage(String Id) throws MalformedURLException;
	
	List<FacultyResponse>  getbyBranch(String branchId);

}
