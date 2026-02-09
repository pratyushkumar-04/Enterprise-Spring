package com.enterprise.dto.request;

import com.enterprise.enums.SubjectStatus;

public class SubjectStatusRequest {

	private SubjectStatus status;

	public SubjectStatus getStatus() {
		return status;
	}

	public void setStatus(SubjectStatus status) {
		this.status = status;
	}
	
}
