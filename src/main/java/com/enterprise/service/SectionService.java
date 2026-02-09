package com.enterprise.service;

import java.util.List;

import com.enterprise.dto.request.SectionRequest;
import com.enterprise.dto.response.SectionResponse;

public interface SectionService {

	SectionResponse createSection(SectionRequest req);
	List<SectionResponse> getSectionsByBranchSem(String branchId,Integer sem);
}
