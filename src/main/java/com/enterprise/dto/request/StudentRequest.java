package com.enterprise.dto.request;

import java.time.LocalDate;

import com.enterprise.enums.Gender;

public class StudentRequest {

	private String name;
    private String email;
    private String phone;

    private String fatherName;
    private String motherName;

    private Gender gender;
    private LocalDate dateOfBirth;

    private Integer admissionYear;
    private Integer currentSemester;

    private String departmentId;
    private String courseId;
    private String branchId;
    private String sectionId;

    public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	private StudentAddressRequest address;
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
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Integer getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(Integer admissionYear) {
		this.admissionYear = admissionYear;
	}
	public Integer getCurrentSemester() {
		return currentSemester;
	}
	public void setCurrentSemester(Integer currentSemester) {
		this.currentSemester = currentSemester;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public StudentAddressRequest getAddress() {
		return address;
	}
	public void setAddress(StudentAddressRequest address) {
		this.address = address;
	}
	
    
    
}
