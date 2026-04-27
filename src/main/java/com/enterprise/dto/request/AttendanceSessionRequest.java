package com.enterprise.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceSessionRequest {

	private String timetableEntryId;
	private LocalDate date;
    private Integer lectureNum;

	public String getTimetableEntryId() {
		return timetableEntryId;
	}

	public void setTimetableEntryId(String timetableEntryId) {
		this.timetableEntryId = timetableEntryId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getLectureNum() {
		return lectureNum;
	}

	public void setLectureNum(Integer lectureNum) {
		this.lectureNum = lectureNum;
	}
	

}
