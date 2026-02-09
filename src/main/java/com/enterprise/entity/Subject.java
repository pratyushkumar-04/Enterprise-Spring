package com.enterprise.entity;

import com.enterprise.enums.SubjectStatus;
import com.enterprise.enums.SubjectType;

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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "subjects", uniqueConstraints = { @UniqueConstraint(columnNames = { "code", "branch_id", "semester" }) })
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private Integer credits;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubjectType type;

	@Column(nullable = false)
	private Integer semester;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubjectStatus status;

	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

}
