package com.enterprise.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.ReassignFaculty;
import com.enterprise.dto.request.SubjectFacultySectionAssignmentRequestDTO;
import com.enterprise.dto.response.SubjectFacultySectionAssignmentResponse;
import com.enterprise.entity.Faculty;
import com.enterprise.entity.Section;
import com.enterprise.entity.Subject;
import com.enterprise.entity.SubjectFacultySectionAssignment;
import com.enterprise.repository.FacultyRepository;
import com.enterprise.repository.SectionRepository;
import com.enterprise.repository.SubjectFacultySectionAssignmentRepository;
import com.enterprise.repository.SubjectRepository;
import com.enterprise.service.SubjectFacultySectionAssignmentService;


@Service
public class SubjectFacultySectionAssignmentServiceImpl implements SubjectFacultySectionAssignmentService {

	@Autowired
	private SubjectFacultySectionAssignmentRepository assignmentRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private SectionRepository sectionRepository;
	@Autowired
	private FacultyRepository facultyRepository;
	
	private SubjectFacultySectionAssignmentResponse mapToDTO(SubjectFacultySectionAssignment assignment) {

		SubjectFacultySectionAssignmentResponse response = new SubjectFacultySectionAssignmentResponse();

		response.setId(assignment.getId());

		response.setSubjectId(assignment.getSubject().getId());
		response.setSubjectCode(assignment.getSubject().getCode());
		response.setSubjectName(assignment.getSubject().getName());

		response.setSectionId(assignment.getSection().getId());
		response.setSectionName(assignment.getSection().getName());

		response.setFacultyId(assignment.getFaculty().getId());
		response.setFacultyCode(assignment.getFaculty().getFacultyCode());
		response.setFacultyName(assignment.getFaculty().getName());

		response.setSemester(assignment.getSemester());
		response.setAcademicYear(assignment.getAcademicYear());
		response.setActive(assignment.getActive());
		response.setBranch(assignment.getSection().getBranch().getName());

		return response;
	}

	@Override
	public SubjectFacultySectionAssignmentResponse createAssignment(SubjectFacultySectionAssignmentRequestDTO dto) {

		if (assignmentRepository.existsBySubjectIdAndSectionId(dto.getSubjectId(), dto.getSectionId())) {

			throw new RuntimeException("Subject is already assigned to this section");
		}

		Subject subject = subjectRepository.findById(dto.getSubjectId())
				.orElseThrow(() -> new RuntimeException("Subject not found"));

		Section section = sectionRepository.findById(dto.getSectionId())
				.orElseThrow(() -> new RuntimeException("Section not found"));

		Faculty faculty = facultyRepository.findById(dto.getFacultyId())
				.orElseThrow(() -> new RuntimeException("Faculty not found"));

		SubjectFacultySectionAssignment assignment = new SubjectFacultySectionAssignment();

		assignment.setSubject(subject);
		assignment.setSection(section);
		assignment.setFaculty(faculty);
		assignment.setSemester(dto.getSemester());
		assignment.setAcademicYear(dto.getAcademicYear());
		assignment.setActive(true);

		SubjectFacultySectionAssignment saved = assignmentRepository.save(assignment);

		return mapToDTO(saved);
	}

	@Override
	public List<SubjectFacultySectionAssignmentResponse> getAssignments(String sectionId, Integer semester) {

		return assignmentRepository.getAssignmentsForSection(sectionId, semester)
				.stream().map(this::mapToDTO).toList();
	}

	@Override
	public SubjectFacultySectionAssignmentResponse reassignFaculty(ReassignFaculty dto) {

		SubjectFacultySectionAssignment assignment = assignmentRepository.findById(dto.getAssignmentId())
				.orElseThrow(() -> new RuntimeException("Assignment not found"));

		Faculty newFaculty = facultyRepository.findById(dto.getNewFacultyId())
				.orElseThrow(() -> new RuntimeException("Faculty not found"));

		assignment.setFaculty(newFaculty);

		SubjectFacultySectionAssignment updated = assignmentRepository.save(assignment);

		return mapToDTO(updated);
	}

	@Override
	public void deleteAssignment(String assignmentId) {

		SubjectFacultySectionAssignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new RuntimeException("Assignment not found"));

		assignmentRepository.delete(assignment);
	}


	@Override
	public List<SubjectFacultySectionAssignmentResponse> getAssignmentsByFacultyId(String id) {
		Faculty faculty = facultyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Faculty not found"));
		
		List<SubjectFacultySectionAssignment> assignments = assignmentRepository.findByFacultyId(id);
		return assignments.stream().map(assignment -> {
			return mapToDTO(assignment);
		}).toList();
		
	}

}
