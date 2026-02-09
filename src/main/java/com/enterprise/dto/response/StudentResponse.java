package com.enterprise.dto.response;

import java.time.LocalDate;

import com.enterprise.enums.Gender;
import com.enterprise.enums.StudentStatus;

public class StudentResponse {

	private String id;
    private String admissionNumber;

    private String name;
    private String email;
    private String phone;

    private String fatherName;
    private String motherName;

    private Gender gender;
    private LocalDate dateOfBirth;

    private Integer admissionYear;
    private Integer currentSemester;

    private StudentStatus status;

    private String departmentName;
    private String courseName;
    private String branchName;
    private String sectionname;

    private String profileImagePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdmissionNumber() {
		return admissionNumber;
	}

	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

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

	public StudentStatus getStatus() {
		return status;
	}

	public void setStatus(StudentStatus status) {
		this.status = status;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public String getSectionname() {
		return sectionname;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}
    
    
	
}
