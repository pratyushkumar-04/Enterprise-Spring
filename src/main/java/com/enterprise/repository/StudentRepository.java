package com.enterprise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Student;
import com.enterprise.enums.StudentStatus;


public interface StudentRepository extends JpaRepository<Student, String>{

    Student findTopByAdmissionNumberStartingWithOrderByAdmissionNumberDesc(String prefix);
    Optional<Student> findByAdmissionNumber(String admissionNumber);
    List<Student> findByBranchId(String branchId);
    List<Student> findByCurrentSemester(Integer currentSemester);
    List<Student> findByStatus(StudentStatus status);
    List<Student> findBySection_IdOrderByRollNumberAsc(String sectionId);
    List<Student> findBySectionIdOrderByAdmissionNumberAsc(String sectionId);



}
