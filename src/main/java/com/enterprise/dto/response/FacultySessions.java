package com.enterprise.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class FacultySessions {

    private String sessionId;
    private LocalDate date;

    private String subjectName;
    private String sectionName;

    private Integer lectureNum;

    private LocalTime startTime;
    private LocalTime endTime;
    private Integer semester;
    private String branch;
    
    

    public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	private Boolean attendanceMarked;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getLectureNum() {
		return lectureNum;
	}

	public void setLectureNum(Integer lectureNum) {
		this.lectureNum = lectureNum;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Boolean getAttendanceMarked() {
		return attendanceMarked;
	}

	public void setAttendanceMarked(Boolean attendanceMarked) {
		this.attendanceMarked = attendanceMarked;
	}
    
    
	
}
