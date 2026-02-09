package com.enterprise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Subject;
import com.enterprise.enums.SubjectStatus;

public interface SubjectRepository extends JpaRepository<Subject, String> {

	List<Subject> findByBranchIdAndSemester(String branchId, Integer semester);
	List<Subject> findByBranch_IdAndSemesterAndStatus(
	        String branchId,
	        Integer semester,
	        SubjectStatus status
	);
}
