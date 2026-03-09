package com.enterprise.service;

import java.util.List;

import com.enterprise.dto.request.SectionRequest;
import com.enterprise.dto.response.SectionResponse;
import com.enterprise.entity.Section;

public interface SectionService {

	SectionResponse createSection(SectionRequest req);
	List<SectionResponse> getSectionsByBranchSem(String branchId,Integer sem);
	List<Section> getAllSections();
}
