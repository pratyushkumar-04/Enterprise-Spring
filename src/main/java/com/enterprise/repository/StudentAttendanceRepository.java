package com.enterprise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.AttendanceSession;
import com.enterprise.entity.Student;
import com.enterprise.entity.StudentAttendance;
import com.enterprise.enums.AttendanceStatus;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, String>{

	boolean existsBySessionAndStudent(AttendanceSession session,Student student);

	long countByStudentIdAndSession_Subject_Id(String studentId,String subjectId);
	long countByStudentIdAndSession_Subject_IdAndStatus(String studentId,String subjectId,AttendanceStatus status);
	List<StudentAttendance>findByStudent_IdOrderBySession_DateDesc(String studentId);
	List<StudentAttendance> findBySession(AttendanceSession session);
	List<StudentAttendance> findByStudent_IdAndSession_Subject_IdOrderBySession_DateAsc(String studentId,String subjectId);

	
}
