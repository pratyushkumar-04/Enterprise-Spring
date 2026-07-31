package com.enterprise.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.TimetableRequest;
import com.enterprise.dto.response.TimetableResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Course;
import com.enterprise.entity.Department;
import com.enterprise.entity.Faculty;
import com.enterprise.entity.Section;
import com.enterprise.entity.Subject;
import com.enterprise.entity.SubjectFacultySectionAssignment;
import com.enterprise.entity.Timetable;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.CourseRepository;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.repository.FacultyRepository;
import com.enterprise.repository.SectionRepository;
import com.enterprise.repository.SubjectFacultySectionAssignmentRepository;
import com.enterprise.repository.SubjectRepository;
import com.enterprise.repository.TimetableRepository;
import com.enterprise.service.TimetableService;

@Service
public class TimetableServiceImpl implements TimetableService {

	@Autowired
    private  TimetableRepository timetableRepository;
	@Autowired
	private SubjectFacultySectionAssignmentRepository assignmentRepo;
	@Autowired
    private  CourseRepository courseRepository;
	@Autowired
    private  BranchRepository branchRepository;
	@Autowired
	private  SectionRepository sectionRepository;
	@Autowired
	private  SubjectRepository subjectRepository;
	@Autowired
	private FacultyRepository facultyRepository;

    

    @Override
    public List<TimetableResponse> getSectionTimetable(String sectionId) {
        return timetableRepository
                .findBySectionIdAndActiveTrueOrderByDayOfWeekAscPeriodNumberAsc(sectionId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimetableResponse> getFacultyTimetable(String facultyId) {
        return timetableRepository
                .findByFacultyIdAndActiveTrueOrderByDayOfWeekAscPeriodNumberAsc(facultyId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTimetableEntry(String id) {

        Timetable entry = timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable entry not found"));

        entry.setActive(false);
        timetableRepository.save(entry);
    }

    private TimetableResponse mapToDto(Timetable entry) {

        TimetableResponse dto = new TimetableResponse();

        dto.setId(entry.getId());

        dto.setAcademicYear(entry.getAcademicYear());
        dto.setDepartment(entry.getDepartment().getName());
        dto.setCourse(entry.getCourse().getName());
        dto.setBranch(entry.getBranch().getName());
        dto.setSection(entry.getSection().getName());

        dto.setSubjectId(entry.getSubject().getId());
        dto.setSubjectName(entry.getSubject().getCode());
        dto.setSubjectCode(entry.getSubject().getCode());

        dto.setFacultyId(entry.getFaculty().getId());
        dto.setFacultyName(entry.getFaculty().getName());
        dto.setFacultyCode(entry.getFaculty().getFacultyCode());

        dto.setDayOfWeek(entry.getDayOfWeek());
        dto.setPeriodNumber(entry.getPeriodNumber());
        dto.setStartTime(entry.getStartTime());
        dto.setEndTime(entry.getEndTime());

        dto.setRoomNumber(entry.getRoomNumber());
        dto.setSemester(entry.getSection().getSemester());

        return dto;
    }

    @Override
    public TimetableResponse createTimetableEntry(TimetableRequest request) {

        SubjectFacultySectionAssignment assignment = assignmentRepo
                .findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Faculty assignment not found"));

        if (!assignment.getActive()) {
            throw new RuntimeException("Selected faculty assignment is inactive");
        }

        String sectionId = assignment.getSection().getId();
        String facultyId = assignment.getFaculty().getId();
        String academicYear = assignment.getAcademicYear();

        // Section clash validation
        boolean sectionBusy = timetableRepository
                .existsBySectionIdAndAcademicYearAndDayOfWeekAndPeriodNumberAndActiveTrue(
                        sectionId,
                        academicYear,
                        request.getDayOfWeek(),
                        request.getPeriodNumber()
                );

        if (sectionBusy) {
            throw new RuntimeException(
                    "This section already has a class scheduled for the selected day and period"
            );
        }

        // Faculty clash validation
        boolean facultyBusy = timetableRepository
                .existsByFacultyIdAndDayOfWeekAndPeriodNumberAndActiveTrue(
                        facultyId,
                        request.getDayOfWeek(),
                        request.getPeriodNumber()
                );

        if (facultyBusy) {
            throw new RuntimeException(
                    "Selected faculty already has another class in this time slot"
            );
        }

        // Room clash validation
        if (request.getRoomNumber() != null &&
                !request.getRoomNumber().trim().isEmpty()) {

            boolean roomBusy = timetableRepository
                    .existsByRoomNumberAndDayOfWeekAndPeriodNumberAndActiveTrue(
                            request.getRoomNumber().trim(),
                            request.getDayOfWeek(),
                            request.getPeriodNumber()
                    );

            if (roomBusy) {
                throw new RuntimeException(
                        "Selected room is already occupied in this time slot"
                );
            }
        }

        Timetable entry = new Timetable();

        entry.setAcademicYear(assignment.getAcademicYear());

        entry.setDepartment(
                assignment.getSection().getBranch().getCourse().getDepartment()
        );

        entry.setCourse(
                assignment.getSection().getBranch().getCourse()
        );

        entry.setBranch(
                assignment.getSection().getBranch()
        );

        entry.setSection(
                assignment.getSection()
        );

        entry.setSubject(
                assignment.getSubject()
        );

        entry.setFaculty(
                assignment.getFaculty()
        );

        entry.setDayOfWeek(request.getDayOfWeek());
        entry.setPeriodNumber(request.getPeriodNumber());
        entry.setStartTime(request.getStartTime());
        entry.setEndTime(request.getEndTime());
        entry.setRoomNumber(request.getRoomNumber());
        entry.setActive(true);

        Timetable savedEntry = timetableRepository.save(entry);

        return mapToDto(savedEntry);
    }

}
