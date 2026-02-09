package com.enterprise.service;

import java.util.List;

import com.enterprise.dto.request.SubjectChangeRequest;
import com.enterprise.dto.request.SubjectRequest;
import com.enterprise.dto.request.SubjectStatusRequest;
import com.enterprise.dto.response.SubjectResponse;
import com.enterprise.enums.SubjectStatus;

public interface SubjectService {

	SubjectResponse addSubject(SubjectRequest subreq);
	SubjectResponse getSubjectById(String Id);
	List<SubjectResponse> getSubjectByBranchSem(String BranchId,Integer sem);
	SubjectResponse editSubject(String Id,SubjectChangeRequest snewreq);
	SubjectResponse modifyStatus(String Id,SubjectStatusRequest newstatus);
	
}
