package com.enterprise.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceSessionRequest {
	
	private String facultyId;
	private String branchId;
	private String subjectId;
	private Integer sem;
	private String section;
	private Integer lecNumber;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	
	public String getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getSem() {
		return sem;
	}
	public void setSem(Integer sem) {
		this.sem = sem;
	}
	public Integer getLecNumber() {
		return lecNumber;
	}
	public void setLecNumber(Integer lecNumber) {
		this.lecNumber = lecNumber;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getStart() {
		return start;
	}
	public void setStart(LocalTime start) {
		this.start = start;
	}
	public LocalTime getEnd() {
		return end;
	}
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
	
	
	
	
	
}
