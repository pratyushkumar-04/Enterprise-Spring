package com.enterprise.dto.request;

import com.enterprise.enums.Designation;

public class FacultyModifyRequest {

    private String name;
    private String email;
    private String phone;

    private Designation designation;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

    
    
}
