package com.enterprise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"branch_id", "semester", "name"}
    )
)
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String Id;
	
	@ManyToOne(optional = false)
	private Branch branch;
	
	@Column(nullable = false)
	private Integer semester;
	
	@Column(nullable = false)
	private String name;
	
    private Boolean active = true;


	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
	
}
