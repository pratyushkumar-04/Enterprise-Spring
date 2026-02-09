package com.enterprise.dto.response;

import com.enterprise.enums.Designation;
import com.enterprise.enums.FacultyStatus;

public class FacultyResponse {

	private String id;
	private String facultyCode;
	private String name;
	private Designation designation;
	private String departmentName;
	private String branchName;
	private FacultyStatus status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFacultyCode() {
		return facultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public FacultyStatus getStatus() {
		return status;
	}

	public void setStatus(FacultyStatus status) {
		this.status = status;
	}

}
