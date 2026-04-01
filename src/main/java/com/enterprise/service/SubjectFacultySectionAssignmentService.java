package com.enterprise.service;

import java.util.List;

import com.enterprise.dto.request.ReassignFaculty;
import com.enterprise.dto.request.SubjectFacultySectionAssignmentRequestDTO;
import com.enterprise.dto.response.SubjectFacultySectionAssignmentResponse;

public interface SubjectFacultySectionAssignmentService {


    SubjectFacultySectionAssignmentResponse createAssignment(
            SubjectFacultySectionAssignmentRequestDTO dto);

    List<SubjectFacultySectionAssignmentResponse> getAssignments(
            String sectionId,
            Integer semester);

    SubjectFacultySectionAssignmentResponse reassignFaculty(
            ReassignFaculty dto);

    void deleteAssignment(String assignmentId);
    
}
