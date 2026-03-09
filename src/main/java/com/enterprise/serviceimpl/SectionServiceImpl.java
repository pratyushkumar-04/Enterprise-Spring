package com.enterprise.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.SectionRequest;
import com.enterprise.dto.response.SectionResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Section;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.SectionRepository;
import com.enterprise.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService{

	@Autowired
	private SectionRepository sectionRepo;
	
	@Autowired
	private BranchRepository branchRepo;
	
	private SectionResponse mapToResponse(Section section) {
		
		SectionResponse resp = new SectionResponse();

		resp.setId(section.getId());
		resp.setName(section.getName());
		
		return resp;
	}
	
	@Override
	public SectionResponse createSection(SectionRequest req) {
		Branch branch = branchRepo.findById(req.getBranchId())
				.orElseThrow(()-> new RuntimeException("No Such Branch found"));
		Section sec = new Section();
		SectionResponse resp = new SectionResponse();
		
		sec.setBranch(branch);
		sec.setName(req.getName());
		sec.setSemester(req.getSemester());
		
		Section saved = sectionRepo.save(sec);
		
		return mapToResponse(saved);
	}

	@Override
	public List<SectionResponse> getSectionsByBranchSem(String branchId, Integer sem) {
		
		List<Section> sections = sectionRepo.findByBranch_IdAndSemester(branchId, sem);
		
		return sections.stream().map(section -> {
			return mapToResponse(section);
		}).toList();
	}

	@Override
	public List<Section> getAllSections() {
		List<Section> sections = sectionRepo.findAll();
		return sections;
	}

}
