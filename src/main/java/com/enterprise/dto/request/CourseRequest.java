package com.enterprise.dto.request;

public class CourseRequest {

	private String name;
	private String code;
	private Integer durration;
	private String deptId;
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
	public Integer getDurration() {
		return durration;
	}
	public void setDurration(Integer durration) {
		this.durration = durration;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}
