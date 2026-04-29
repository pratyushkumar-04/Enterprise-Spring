package com.enterprise.dto.response;

public class AttendanceSummaryDTO {

    private String subjectName;
    private String sectionName;

    private long totalClasses;
    private double averageAttendance;

    private long below75Count;

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

	public long getTotalClasses() {
		return totalClasses;
	}

	public void setTotalClasses(long totalClasses) {
		this.totalClasses = totalClasses;
	}

	public double getAverageAttendance() {
		return averageAttendance;
	}

	public void setAverageAttendance(double averageAttendance) {
		this.averageAttendance = averageAttendance;
	}

	public long getBelow75Count() {
		return below75Count;
	}

	public void setBelow75Count(long below75Count) {
		this.below75Count = below75Count;
	}
    
    
}
