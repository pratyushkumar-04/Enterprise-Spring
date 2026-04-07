package com.enterprise.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimetableResponse {

	 private String id;

	    private String academicYear;
	    private String department;
	    private String course;
	    private String branch;
	    private String section;

	    private String subjectId;
	    private String subjectName;
	    private String subjectCode;

	    private String facultyId;
	    private String facultyName;
	    private String facultyCode;

	    private DayOfWeek dayOfWeek;
	    private Integer periodNumber;

	    private LocalTime startTime;
	    private LocalTime endTime;

	    private String roomNumber;
	    private Integer semester;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAcademicYear() {
			return academicYear;
		}

		public void setAcademicYear(String academicYear) {
			this.academicYear = academicYear;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getCourse() {
			return course;
		}

		public void setCourse(String course) {
			this.course = course;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

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

		public String getSubjectCode() {
			return subjectCode;
		}

		public void setSubjectCode(String subjectCode) {
			this.subjectCode = subjectCode;
		}

		public String getFacultyId() {
			return facultyId;
		}

		public void setFacultyId(String facultyId) {
			this.facultyId = facultyId;
		}

		public String getFacultyName() {
			return facultyName;
		}

		public void setFacultyName(String facultyName) {
			this.facultyName = facultyName;
		}

		public String getFacultyCode() {
			return facultyCode;
		}

		public void setFacultyCode(String facultyCode) {
			this.facultyCode = facultyCode;
		}

		public DayOfWeek getDayOfWeek() {
			return dayOfWeek;
		}

		public void setDayOfWeek(DayOfWeek dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}

		public Integer getPeriodNumber() {
			return periodNumber;
		}

		public void setPeriodNumber(Integer periodNumber) {
			this.periodNumber = periodNumber;
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

		public String getRoomNumber() {
			return roomNumber;
		}

		public void setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
		}

		public Integer getSemester() {
			return semester;
		}

		public void setSemester(Integer semester) {
			this.semester = semester;
		}
		
	    
	    
}
