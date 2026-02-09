package com.enterprise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Section;

public interface SectionRepository extends JpaRepository<Section, String>{

	List<Section> findByBranch_IdAndSemester(String branchId, Integer semester);

}
