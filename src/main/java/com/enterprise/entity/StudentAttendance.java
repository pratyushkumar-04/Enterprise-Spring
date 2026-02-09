package com.enterprise.entity;

import com.enterprise.enums.AttendanceStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name = "student_attendance",
uniqueConstraints = @UniqueConstraint(
	      columnNames = {"session_id", "student_id"}
	  )
)
public class StudentAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String Id;
	
	@ManyToOne
	private AttendanceSession session;
	
	@ManyToOne
	private Student student;
	
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public AttendanceSession getSession() {
		return session;
	}

	public void setSession(AttendanceSession session) {
		this.session = session;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}
	
	
}
