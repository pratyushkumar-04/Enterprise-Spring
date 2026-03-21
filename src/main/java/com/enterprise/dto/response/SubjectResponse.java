package com.enterprise.dto.response;

import com.enterprise.enums.SubjectStatus;
import com.enterprise.enums.SubjectType;

public class SubjectResponse {

	private String Id;
	private String name;
	private String code;
	private Integer credits;
	private SubjectType type;
	private Integer semester;
	private SubjectStatus status;
	private String branchId;
	private String branchName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	public SubjectType getType() {
		return type;
	}
	public void setType(SubjectType type) {
		this.type = type;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public SubjectStatus getStatus() {
		return status;
	}
	public void setStatus(SubjectStatus status) {
		this.status = status;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	
	
}
