package com.enterprise.dto.request;

public class ReassignFaculty {

	private String assignmentId;
	private String newFacultyId;

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getNewFacultyId() {
		return newFacultyId;
	}

	public void setNewFacultyId(String newFacultyId) {
		this.newFacultyId = newFacultyId;
	}

}
