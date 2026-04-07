package com.enterprise.service;

import java.util.List;

import com.enterprise.dto.request.TimetableRequest;
import com.enterprise.dto.response.TimetableResponse;

public interface TimetableService {

	 TimetableResponse createTimetableEntry(TimetableRequest request);

	    List<TimetableResponse> getSectionTimetable(String sectionId);

	    List<TimetableResponse> getFacultyTimetable(String facultyId);

	    void deleteTimetableEntry(String id);
}
