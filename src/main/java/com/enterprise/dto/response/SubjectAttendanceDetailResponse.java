package com.enterprise.dto.response;

import java.util.List;

public class SubjectAttendanceDetailResponse {
    private String subjectId;
    private String subjectName;

    private List<AttendanceDateWiseResponse> attendanceList;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<AttendanceDateWiseResponse> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<AttendanceDateWiseResponse> attendanceList) {
		this.attendanceList = attendanceList;
	}
    
    
    
}
