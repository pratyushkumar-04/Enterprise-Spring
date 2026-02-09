package com.enterprise.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="courses")
public class Course {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private String id;

	    private String name;     
	    private String code;    
	    private Integer durationYears;

	    @ManyToOne
	    @JoinColumn(name = "department_id")
	    private Department department;

	    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Branch> branches;

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

		public Department getDepartment() {
			return department;
		}

		public void setDepartment(Department department) {
			this.department = department;
		}

		public List<Branch> getBranches() {
			return branches;
		}

		public void setBranches(List<Branch> branches) {
			this.branches = branches;
		}
	    
	    
}
