package com.enterprise.entity;

import java.time.LocalDate;

import com.enterprise.enums.Gender;
import com.enterprise.enums.StudentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String Id;
	
	private String admissionNumber;  
	
	private String name;
	private String phone;
	private String email;
	private Integer rollNumber;
	
	@ManyToOne
	private Section section;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
	
	private Gender gender;
	private String fatherName;  
	private String motherNAme;
	private Integer admissionYear;
	private Integer currentSemester;
	
	@Enumerated(EnumType.STRING)
    private StudentStatus status;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    
    private String imgPath;
    private String adhaarPath;
	private String tenthPath;
	private String twelthPath;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherNAme() {
		return motherNAme;
	}

	public void setMotherNAme(String motherNAme) {
		this.motherNAme = motherNAme;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Integer getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getAdhaarPath() {
		return adhaarPath;
	}

	public void setAdhaarPath(String adhaarPath) {
		this.adhaarPath = adhaarPath;
	}

	public String getTenthPath() {
		return tenthPath;
	}

	public void setTenthPath(String tenthPath) {
		this.tenthPath = tenthPath;
	}

	public String getTwelthPath() {
		return twelthPath;
	}

	public void setTwelthPath(String twelthPath) {
		this.twelthPath = twelthPath;
	}


	
    
    
	
}
