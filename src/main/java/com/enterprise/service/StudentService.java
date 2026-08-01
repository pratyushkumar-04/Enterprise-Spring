package com.enterprise.service;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	Page<StudentResponse> getAllstudents(
			Pageable pageable ,
			String search,
			String departmentId,
			String courseId,
			String branchId,
			Integer semester,
			StudentStatus status
	);
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
	List<StudentResponse> getStudentsWithoutRoll(String sectionId);
	Integer getMaxRollNumber(String sectionId);
	Resource getImage(String Id) throws MalformedURLException;
	Resource getDocument(String Id,String type) throws MalformedURLException;
}
