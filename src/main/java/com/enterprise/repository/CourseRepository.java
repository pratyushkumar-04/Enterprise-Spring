package com.enterprise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Course;

public interface CourseRepository extends JpaRepository<Course, String>{

}
