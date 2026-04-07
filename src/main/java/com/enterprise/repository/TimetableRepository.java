package com.enterprise.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable,String>{
	
	 List<Timetable> findBySectionIdAndActiveTrueOrderByDayOfWeekAscPeriodNumberAsc(
	            String sectionId
	    );

	    List<Timetable> findByFacultyIdAndActiveTrueOrderByDayOfWeekAscPeriodNumberAsc(
	            String facultyId
	    );

	    boolean existsBySectionIdAndAcademicYearAndDayOfWeekAndPeriodNumberAndActiveTrue(
	            String sectionId,
	            String academicYear,
	            DayOfWeek dayOfWeek,
	            Integer periodNumber
	    );

	    boolean existsByFacultyIdAndDayOfWeekAndPeriodNumberAndActiveTrue(
	            String facultyId,
	            DayOfWeek dayOfWeek,
	            Integer periodNumber
	    );

	    boolean existsByRoomNumberAndDayOfWeekAndPeriodNumberAndActiveTrue(
	            String roomNumber,
	            DayOfWeek dayOfWeek,
	            Integer periodNumber
	    );
}
