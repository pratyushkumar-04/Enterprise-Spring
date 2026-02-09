package com.enterprise.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.SubjectChangeRequest;
import com.enterprise.dto.request.SubjectRequest;
import com.enterprise.dto.request.SubjectStatusRequest;
import com.enterprise.dto.response.SubjectResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Subject;
import com.enterprise.enums.SubjectStatus;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.SubjectRepository;
import com.enterprise.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{
	
	@Autowired
	private BranchRepository branchRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;

	@Override
	public SubjectResponse addSubject(SubjectRequest subReq) {
		String bId=subReq.getBranchId();
		Branch branch=branchRepo.findById(bId)
				.orElseThrow(()-> new IllegalArgumentException("No Such Branch Found"));
		
		Subject sub = new Subject();
		sub.setBranch(branch);
		sub.setCode(subReq.getCode());
		sub.setCredits(subReq.getCredits());
		sub.setName(subReq.getName());
		sub.setSemester(subReq.getSemester());
		sub.setStatus(SubjectStatus.ACTIVE);
		sub.setType(subReq.getType());
		
		subjectRepo.save(sub);
		
		SubjectResponse subResp=new SubjectResponse();
		subResp.setCode(subReq.getCode());
		subResp.setCredits(subReq.getCredits());
		subResp.setName(subReq.getName());
		subResp.setSemester(subReq.getSemester());
		subResp.setStatus(SubjectStatus.ACTIVE);
		subResp.setType(subReq.getType());
		subResp.setName(sub.getName());
		subResp.setBranchId(bId);
		subResp.setBranchName(branch.getName());
		return subResp;
	}

	@Override
	public SubjectResponse getSubjectById(String Id) {
		Subject sub= subjectRepo.findById(Id)
				.orElseThrow(()-> new IllegalArgumentException("No subject with given Id Exists"));
		
		SubjectResponse subResp=new SubjectResponse();
		subResp.setCode(sub.getCode());
		subResp.setCredits(sub.getCredits());
		subResp.setName(sub.getName());
		subResp.setSemester(sub.getSemester());
		subResp.setStatus(SubjectStatus.ACTIVE);
		subResp.setType(sub.getType());
		subResp.setName(sub.getName());
		subResp.setBranchId(sub.getBranch().getId());
		subResp.setBranchName(sub.getBranch().getName());
		return subResp;
	}

	@Override
	public List<SubjectResponse> getSubjectByBranchSem(String BranchId, Integer sem) {
		
		Branch branch=branchRepo.findById(BranchId)
				.orElseThrow(()-> new IllegalArgumentException("No such Branch Exists"));
		
		List<Subject> sub= subjectRepo.findByBranchIdAndSemester(BranchId, sem);
		
	    return sub.stream()
	            .map(subject -> {

	                SubjectResponse res = new SubjectResponse();

	                res.setName(subject.getName());
	                res.setCode(subject.getCode());
	                res.setCredits(subject.getCredits());
	                res.setType(subject.getType());
	                res.setSemester(subject.getSemester());
	                res.setStatus(subject.getStatus());

	                res.setBranchId(subject.getBranch().getId());
	                res.setBranchName(subject.getBranch().getName());

	                return res;
	            })
	            .toList();
	}

	@Override
	public SubjectResponse editSubject(String Id, SubjectChangeRequest snewreq) {
		Subject sub= subjectRepo.findById(Id)
				.orElseThrow(()-> new IllegalArgumentException("No subject with given Id Exists"));
		
		sub.setName(snewreq.getName());
		sub.setCredits(snewreq.getCredits());
		sub.setType(snewreq.getType());
		
		subjectRepo.save(sub);
		
		SubjectResponse subResp=new SubjectResponse();
		subResp.setCode(sub.getCode());
		subResp.setCredits(sub.getCredits());
		subResp.setName(sub.getName());
		subResp.setSemester(sub.getSemester());
		subResp.setStatus(SubjectStatus.ACTIVE);
		subResp.setType(sub.getType());
		subResp.setName(sub.getName());
		subResp.setBranchId(sub.getBranch().getId());
		subResp.setBranchName(sub.getBranch().getName());
		return subResp;
		
	}

	@Override
	public SubjectResponse modifyStatus(String Id, SubjectStatusRequest newstatus) {
		Subject sub= subjectRepo.findById(Id)
				.orElseThrow(()-> new IllegalArgumentException("No subject with given Id Exists"));
		
		sub.setStatus(newstatus.getStatus());
		
		subjectRepo.save(sub);
		SubjectResponse subResp=new SubjectResponse();
		subResp.setCode(sub.getCode());
		subResp.setCredits(sub.getCredits());
		subResp.setName(sub.getName());
		subResp.setSemester(sub.getSemester());
		subResp.setStatus(sub.getStatus());
		subResp.setType(sub.getType());
		subResp.setName(sub.getName());
		subResp.setBranchId(sub.getBranch().getId());
		subResp.setBranchName(sub.getBranch().getName());
		return subResp;
	}

}
