package com.enterprise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enterprise.entity.SubjectFacultySectionAssignment;

public interface SubjectFacultySectionAssignmentRepository
		extends JpaRepository<SubjectFacultySectionAssignment, String> {
	
	boolean existsBySubjectIdAndSectionId(String subjectId, String sectionId);

    Optional<SubjectFacultySectionAssignment>
        findBySubjectIdAndSectionId(String subjectId, String sectionId);

    List<SubjectFacultySectionAssignment>
        findBySectionId(String sectionId);

    List<SubjectFacultySectionAssignment>
        findBySectionIdAndSemester(String sectionId, Integer semester);

    List<SubjectFacultySectionAssignment>
        findByFacultyId(String facultyId);

    void deleteBySubjectIdAndSectionId(String subjectId, String sectionId);
    
    @Query("""
    	    SELECT a
    	    FROM SubjectFacultySectionAssignment a
    	    WHERE a.section.id = :sectionId
    	    AND a.semester = :semester
    	    AND a.active = true
    	""")
    	List<SubjectFacultySectionAssignment> getAssignmentsForSection(
    	        @Param("sectionId") String sectionId,
    	        @Param("semester") Integer semester
    	);

}
