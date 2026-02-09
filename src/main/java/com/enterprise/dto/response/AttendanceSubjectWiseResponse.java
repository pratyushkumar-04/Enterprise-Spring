package com.enterprise.dto.response;

public class AttendanceSubjectWiseResponse {

	private String subjectId;
	private String subjectCode;
	private String subjectName;

	private long totalSessions;
	private long presentCount;
	private long absentCount;

	private double percentage;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public long getTotalSessions() {
		return totalSessions;
	}

	public void setTotalSessions(long totalSessions) {
		this.totalSessions = totalSessions;
	}

	public long getPresentCount() {
		return presentCount;
	}

	public void setPresentCount(long presentCount) {
		this.presentCount = presentCount;
	}

	public long getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(long absentCount) {
		this.absentCount = absentCount;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

}
