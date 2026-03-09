package com.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enterprise.dto.request.RollNumrequest;
import com.enterprise.dto.request.SectionAssignRequest;
import com.enterprise.dto.request.StudentRequest;
import com.enterprise.dto.response.StudentResponse;
import com.enterprise.enums.StudentStatus;

@Service
public interface StudentService {

	StudentResponse addStudent( StudentRequest studentReq,
	        MultipartFile image,
	        MultipartFile adhaar,
	        MultipartFile tenth,
	        MultipartFile twelfth);
	List<StudentResponse> getAllstudents();
	StudentResponse getStudentById(String Id);
	StudentResponse getStudentByAdmnum(String admnum);
	List<StudentResponse> getStudentsByBranch(String branchId);
	List<StudentResponse> getStudentBySem(Integer semester);
	List<StudentResponse> getStudentByStatus(StudentStatus status);
	StudentResponse modifyStatus(String Id,StudentStatus status);
	StudentResponse promotion(String Id);
	StudentResponse edit(String Id,StudentRequest sreq);
	void assignRoll(String Id,RollNumrequest req);
	void assignSection(String Id,SectionAssignRequest sec);
	void generateRollNumbersForSection(String sectionId);
	List<StudentResponse> getStudentsBySection(String sectionId);
}
