package com.enterprise.dto.response;

public class CourseResponse {

	private String id;
    private String name;
    private String code;
    private Integer durationYears;
    private String departmentId;
    private String departmentName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Integer getDurationYears() {
		return durationYears;
	}
	public void setDurationYears(Integer durationYears) {
		this.durationYears = durationYears;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

    
}
