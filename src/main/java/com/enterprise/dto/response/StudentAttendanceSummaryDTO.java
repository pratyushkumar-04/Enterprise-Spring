package com.enterprise.dto.response;

public class StudentAttendanceSummaryDTO {

	private String studentId;
	private String name;
	private Integer rollNumber;

	private long presentCount;
	private long absentCount;
	private long totalClasses;

	private double percentage;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
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

	public long getTotalClasses() {
		return totalClasses;
	}

	public void setTotalClasses(long totalClasses) {
		this.totalClasses = totalClasses;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	

	
}
