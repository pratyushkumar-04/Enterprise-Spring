package com.enterprise.dto.request;

import com.enterprise.enums.SubjectType;

public class SubjectChangeRequest {

	private String name;
	private Integer credits;
	private SubjectType type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	
}
