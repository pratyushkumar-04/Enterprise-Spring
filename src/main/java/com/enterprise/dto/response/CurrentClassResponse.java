package com.enterprise.dto.response;

import java.time.LocalTime;

public class CurrentClassResponse {

	private String timetableEntryId;
	private String subjectName;
	private String sectionName;
	private Integer lectureNum;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean attendanceMarked;
	private String sessionId;
	private Integer Semester;
	
	

	public Integer getSemester() {
		return Semester;
	}

	public void setSemester(Integer semester) {
		Semester = semester;
	}

	public String getTimetableEntryId() {
		return timetableEntryId;
	}

	public void setTimetableEntryId(String timetableEntryId) {
		this.timetableEntryId = timetableEntryId;
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

	public void setSectionName(String sectionNAme) {
		this.sectionName = sectionNAme;
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

	public boolean isAttendanceMarked() {
		return attendanceMarked;
	}

	public void setAttendanceMarked(boolean attendanceMarked) {
		this.attendanceMarked = attendanceMarked;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
