package com.enterprise.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.BranchRequest;
import com.enterprise.dto.response.BranchResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Course;
import com.enterprise.entity.Department;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.CourseRepository;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository branchRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Override
	public BranchResponse addbranch(BranchRequest breq) {
		String cId = breq.getCourseId();
		Course cr = courseRepo.findById(cId).orElseThrow(() -> new RuntimeException("No such Course exists"));
		Department dept = cr.getDepartment();
		Branch br = new Branch();
		br.setCode(breq.getCode());
		br.setName(breq.getName());
		br.setCourse(cr);

		cr.getBranches().add(br);
		Branch savedbranch= branchRepo.save(br);

		BranchResponse bresp = new BranchResponse();

		bresp.setId(savedbranch.getId());
		bresp.setCode(savedbranch.getCode());
		bresp.setCourseId(cId);
		bresp.setCourseName(cr.getName());
		bresp.setName(savedbranch.getName());
		bresp.setDepartmentId(dept.getId());
		bresp.setDepartmentName(dept.getName());
		bresp.setDurationYears(cr.getDurationYears());
		return bresp;
	}

	@Override
	public List<BranchResponse> getBranchByCourseId(String courseId) {
		Course cr = courseRepo.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException("No such Course exists"));

		return cr.getBranches().stream().map(branch -> {

			BranchResponse res = new BranchResponse();
			res.setId(branch.getId());
			res.setName(branch.getName());
			res.setCode(branch.getCode());

			res.setCourseId(cr.getId());
			res.setCourseName(cr.getName());
			res.setDurationYears(cr.getDurationYears());

			res.setDepartmentId(cr.getDepartment().getId());
			res.setDepartmentName(cr.getDepartment().getName());

			return res;
		}).toList();
	}
}