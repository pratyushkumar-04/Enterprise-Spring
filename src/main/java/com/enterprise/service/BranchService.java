package com.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.dto.request.BranchRequest;
import com.enterprise.dto.response.BranchResponse;
import com.enterprise.entity.Branch;


@Service
public interface BranchService {

	BranchResponse addbranch(BranchRequest br);
	List<BranchResponse> getBranchByCourseId(String courseId);
}
