package com.enterprise.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.AttendanceSession;

public interface AttendanceRepository extends JpaRepository<AttendanceSession, String>{
	
	Optional<AttendanceSession> findByTimetableEntryIdAndDate(String timetableEntryId, LocalDate date);

	boolean existsByTimetableEntryIdAndDate(String timetableEntryId, LocalDate date);

    long countBySectionIdAndSubjectId(String sectionId, String subjectId);

}
