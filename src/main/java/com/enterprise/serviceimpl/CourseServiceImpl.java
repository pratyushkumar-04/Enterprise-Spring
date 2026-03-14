package com.enterprise.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.CourseRequest;
import com.enterprise.dto.response.CourseResponse;
import com.enterprise.entity.Course;
import com.enterprise.entity.Department;
import com.enterprise.repository.CourseRepository;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private CourseRepository courserepo;

	@Override
	public CourseResponse addCourse(CourseRequest c) {
		String deptId= c.getDeptId();
		Department dept = deptRepo.findById(deptId)
				.orElseThrow(()-> new RuntimeException ("No such Department exist"));
		
		Course cr= new Course();
		cr.setCode(c.getCode());
		cr.setName(c.getName());
		cr.setDurationYears(c.getDurration());
		cr.setDepartment(dept);
		
		dept.getCourses().add(cr);
		
		courserepo.save(cr);
		deptRepo.save(dept);
		
		CourseResponse resp=new CourseResponse();
		resp.setCode(c.getCode());
		resp.setDurationYears(c.getDurration());
		resp.setDepartmentId(deptId);
		resp.setDepartmentName(dept.getName());
		resp.setName(c.getName());
		resp.setId(cr.getId());
		
		return resp;
	}

	@Override
	public List<Course> getCourseByDept(String deptId) {
		Department dept = deptRepo.findById(deptId)
				.orElseThrow(()-> new RuntimeException ("No such Department exist"));
		
		return dept.getCourses();
	}

	@Override
	public CourseResponse editCourse(String id, CourseRequest req) {
		Course cr = courserepo.findById(id)
				.orElseThrow(()-> new RuntimeException("No such Course Exists"));
		cr.setCode(req.getCode());
		cr.setName(req.getName());
		cr.setDurationYears(req.getDurration());
		
		Course c = courserepo.save(cr);
		
		CourseResponse resp=new CourseResponse();
		resp.setCode(c.getCode());
		resp.setDurationYears(req.getDurration());
		resp.setDepartmentId(c.getDepartment().getId());
		resp.setDepartmentName(c.getDepartment().getName());
		resp.setName(c.getName());
		resp.setId(c.getId());
		
		return resp;
	}

}
