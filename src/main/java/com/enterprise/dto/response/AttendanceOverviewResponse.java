package com.enterprise.dto.response;

import java.util.List;

public class AttendanceOverviewResponse {


    private AttendanceSummaryDTO summary;
    private List<StudentAttendanceSummaryDTO> students;
	public AttendanceSummaryDTO getSummary() {
		return summary;
	}
	public void setSummary(AttendanceSummaryDTO summary) {
		this.summary = summary;
	}
	public List<StudentAttendanceSummaryDTO> getStudents() {
		return students;
	}
	public void setStudents(List<StudentAttendanceSummaryDTO> students) {
		this.students = students;
	}
	public AttendanceOverviewResponse(AttendanceSummaryDTO summary, List<StudentAttendanceSummaryDTO> students) {
		super();
		this.summary = summary;
		this.students = students;
	}
    
	
    
	
}
