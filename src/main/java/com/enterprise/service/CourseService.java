package com.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.dto.request.CourseRequest;
import com.enterprise.dto.response.CourseResponse;
import com.enterprise.entity.Course;

@Service
public interface CourseService {

	CourseResponse addCourse(CourseRequest c);
	List<Course> getCourseByDept(String deptId);
	CourseResponse editCourse(String id,CourseRequest req);
}
