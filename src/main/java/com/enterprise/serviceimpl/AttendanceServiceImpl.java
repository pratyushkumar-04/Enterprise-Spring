package com.enterprise.serviceimpl;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.AttendanceSessionRequest;
import com.enterprise.dto.request.MarkAttendanceRequest;
import com.enterprise.dto.request.StudentAttendanceRequest;
import com.enterprise.dto.response.AttedanceSessionResponse;
import com.enterprise.dto.response.AttendanceDateWiseResponse;
import com.enterprise.dto.response.AttendanceOverviewResponse;
import com.enterprise.dto.response.AttendanceStudentResponse;
import com.enterprise.dto.response.AttendanceSubjectWiseResponse;
import com.enterprise.dto.response.AttendanceSummaryDTO;
import com.enterprise.dto.response.CurrentClassResponse;
import com.enterprise.dto.response.FacultySessions;
import com.enterprise.dto.response.StudentAttendanceSummaryDTO;
import com.enterprise.dto.response.SubjectAttendanceDetailResponse;
import com.enterprise.entity.AttendanceSession;
import com.enterprise.entity.Faculty;
import com.enterprise.entity.Section;
import com.enterprise.entity.Student;
import com.enterprise.entity.StudentAttendance;
import com.enterprise.entity.Subject;
import com.enterprise.entity.Timetable;
import com.enterprise.enums.AttendanceStatus;
import com.enterprise.enums.SubjectStatus;
import com.enterprise.repository.AttendanceRepository;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.FacultyRepository;
import com.enterprise.repository.SectionRepository;
import com.enterprise.repository.StudentAttendanceRepository;
import com.enterprise.repository.StudentRepository;
import com.enterprise.repository.SubjectRepository;
import com.enterprise.repository.TimetableRepository;
import com.enterprise.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private BranchRepository branchRepo;
	@Autowired
	private SubjectRepository subjectRepo;
	@Autowired
	private FacultyRepository faculyRepo;
	@Autowired
	private AttendanceRepository sessionRepo;
	@Autowired
	private StudentAttendanceRepository attendanceRepo;
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private SectionRepository sectionRepo;
	@Autowired
	private TimetableRepository timetableRepo;

	AttedanceSessionResponse maptoResponse(AttendanceSession sess) {
		AttedanceSessionResponse resp = new AttedanceSessionResponse();
		resp.setId(sess.getId());
		resp.setBranchId(sess.getBranch().getCode());
		resp.setDate(sess.getDate());
		resp.setSubjectId(sess.getSubject().getId());
		resp.setFacultyId(sess.getFaculty().getId());
		resp.setLecNumber(sess.getLectureNum());
		resp.setSem(sess.getSemester());
		resp.setStart(sess.getStartTime());
		resp.setEnd(sess.getEndTime());
		resp.setSection(sess.getSection().getId());
		return resp;
	}

	@Override
	public AttedanceSessionResponse createSession(AttendanceSessionRequest req) {
		Timetable entry = timetableRepo.findById(req.getTimetableEntryId())
				.orElseThrow(() -> new RuntimeException("No Such Class Exists in Timetable"));
		Faculty faculty = entry.getFaculty();

		AttendanceSession session = new AttendanceSession();
		session.setTimetableEntry(entry);
		session.setDate(req.getDate());
		session.setFaculty(faculty);
		session.setBranch(entry.getBranch());
		session.setSubject(entry.getSubject());
		session.setSection(entry.getSection());
		session.setSemester(entry.getSection().getSemester());
		session.setLectureNum(req.getLectureNum());
		session.setStartTime(entry.getStartTime());
		session.setEndTime(entry.getEndTime());
		session.setAttendanceMarked(false);
		AttendanceSession createdSession = sessionRepo.save(session);
		return maptoResponse(createdSession);
	}

	@Override
	public void markAttendance(MarkAttendanceRequest req) {
		AttendanceSession session = sessionRepo.findById(req.getSessionId())
				.orElseThrow(() -> new RuntimeException("Session not Found or expired"));

		for (StudentAttendanceRequest ar : req.getAttendanceList()) {
			Student student = studentRepo.findById(ar.getStudentId())
					.orElseThrow(() -> new RuntimeException("No such Student Found"));
			if (attendanceRepo.existsBySessionAndStudent(session, student)) {
				continue;
			}
			StudentAttendance sa = new StudentAttendance();
			sa.setSession(session);
			sa.setStatus(ar.getStatus());
			sa.setStudent(student);

			attendanceRepo.save(sa);
		}
		session.setAttendanceMarked(true);
		
		sessionRepo.save(session);

	}

	@Override
	public List<AttendanceStudentResponse> getStudentsForAttendance(String sessionId) {
		AttendanceSession session = sessionRepo.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Attendance session not found"));

		String sectionId = session.getSection().getId();

		List<Student> students = studentRepo.findBySection_IdOrderByRollNumberAsc(sectionId);

		List<StudentAttendance> markedAttendance = attendanceRepo.findBySession(session);

		Map<String, StudentAttendance> attendanceMap = markedAttendance.stream()
				.collect(Collectors.toMap(a -> a.getStudent().getId(), a -> a));

		return students.stream().map(stu -> {
			AttendanceStudentResponse resp = new AttendanceStudentResponse();
			resp.setStudentId(stu.getId());
			resp.setRollNumber(stu.getRollNumber());
			resp.setName(stu.getName());

			resp.setAdmissionNumber(stu.getAdmissionNumber());
			resp.setPhotoUrl(stu.getImgPath());

			StudentAttendance att = attendanceMap.get(stu.getId());

			if (att != null) {
				resp.setAttendanceId(att.getId());
				resp.setPresent(att.getStatus() == AttendanceStatus.PRESENT);
				resp.setEditable(true);
			} else {
				resp.setAttendanceId(null);
				resp.setPresent(null);
				resp.setEditable(true);
			}

			return resp;
		}).toList();
	}

	@Override
	public List<AttendanceSubjectWiseResponse> getSubjectWiseAttendance(String studentId) {
		Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

		Section section = student.getSection();
		String branchId = section.getBranch().getId();
		Integer semester = section.getSemester();

		List<Subject> subjects = subjectRepo.findByBranch_IdAndSemesterAndStatus(branchId, semester,
				SubjectStatus.ACTIVE);

		List<AttendanceSubjectWiseResponse> response = new ArrayList<>();

		for (Subject subject : subjects) {
			long totalSessions = attendanceRepo.countByStudentIdAndSession_Subject_Id(studentId, subject.getId());
			long presentCount = attendanceRepo.countByStudentIdAndSession_Subject_IdAndStatus(studentId,
					subject.getId(), AttendanceStatus.PRESENT);

			long absentCount = totalSessions - presentCount;
			double percentage = totalSessions == 0 ? 0 : (presentCount * 100.0) / totalSessions;
			AttendanceSubjectWiseResponse dto = new AttendanceSubjectWiseResponse();
			dto.setSubjectId(subject.getId());
			dto.setSubjectName(subject.getName());
			dto.setSubjectCode(subject.getCode());
			dto.setTotalSessions(totalSessions);
			dto.setPresentCount(presentCount);
			dto.setAbsentCount(absentCount);
			dto.setPercentage(percentage);
			response.add(dto);
		}

		return response;
	}

	@Override
	public SubjectAttendanceDetailResponse getSubjectAttendanceDetail(String studentId, String subjectId) {
		Student student = studentRepo.findById(studentId)
				.orElseThrow(() -> new RuntimeException("No such Student Exists"));
		Subject subject = subjectRepo.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));
		Section section = student.getSection();

		if (!subject.getBranch().getId().equals(section.getBranch().getId())
				|| !subject.getSemester().equals(section.getSemester())) {
			throw new RuntimeException("Subject not applicable to student");
		}
		List<StudentAttendance> attendanceList = attendanceRepo
				.findByStudent_IdAndSession_Subject_IdOrderBySession_DateAsc(studentId, subjectId);

		List<AttendanceDateWiseResponse> days = attendanceList.stream().map(att -> {

			AttendanceDateWiseResponse day = new AttendanceDateWiseResponse();

			day.setSessionId(att.getSession().getId());
			day.setSessionDate(att.getSession().getDate());
			day.setStatus(att.getStatus());
			return day;

		}).toList();
		SubjectAttendanceDetailResponse response = new SubjectAttendanceDetailResponse();

		response.setSubjectId(subject.getId());
		response.setSubjectName(subject.getName());
		response.setAttendanceList(days);

		return response;
	}

	@Autowired
	private Clock clock;

	@Override
	public CurrentClassResponse getCurrentClass(String facultyId) {
		return getCurrentClassWithClock(facultyId, clock);

	}

	@Override
	public CurrentClassResponse getCurrentClassWithClock(String facultyId, Clock customClock) {

		LocalDate today = LocalDate.now(customClock);
		LocalTime now = LocalTime.now(customClock);
		DayOfWeek day = (DayOfWeek) today.getDayOfWeek();

		List<Timetable> entries = timetableRepo.findByFacultyIdAndDayOfWeekOrderByStartTimeAsc(facultyId, day);

		Timetable current = entries.stream()
				.filter(entry -> !now.isBefore(entry.getStartTime()) && now.isBefore(entry.getEndTime())).findFirst()
				.orElseThrow(() -> new RuntimeException("No current class found"));

		Optional<AttendanceSession> existingSession = sessionRepo.findByTimetableEntryIdAndDate(current.getId(), today);

		Integer lectureNum;

		if (existingSession.isPresent()) {
			lectureNum = existingSession.get().getLectureNum();
		} else {
			long previousLectures = sessionRepo.countBySectionIdAndSubjectId(current.getSection().getId(),
					current.getSubject().getId());

			lectureNum = (int) previousLectures + 1;
		}

		CurrentClassResponse response = new CurrentClassResponse();

		response.setTimetableEntryId(current.getId());
		response.setSubjectName(current.getSubject().getName());
		response.setSectionName(current.getBranch().getName() + " " + current.getSection().getName());
		response.setLectureNum(lectureNum);
		response.setStartTime(current.getStartTime());
		response.setEndTime(current.getEndTime());

		if (existingSession.isPresent()) {
			response.setAttendanceMarked(existingSession.get().getAttendanceMarked());
			response.setSessionId(existingSession.get().getId());
		} else {
			response.setAttendanceMarked(false);
			response.setSessionId(null);
		}

		return response;
	}

	@Override
	public List<CurrentClassResponse> getFacultyClassesForAttendance(String facultyId, LocalDate date) {
		DayOfWeek day = date.getDayOfWeek();

		List<Timetable> classes = timetableRepo.findByFaculty_IdAndDayOfWeekOrderByStartTimeAsc(facultyId, day);

		return classes.stream().map(timetable -> {

			Optional<AttendanceSession> existingSession = sessionRepo.findByTimetableEntryIdAndDate(timetable.getId(),
					date);

			Integer lectureNum;

			if (existingSession.isPresent()) {
				lectureNum = existingSession.get().getLectureNum();
			} else {
				long previousCount = sessionRepo.countBySectionIdAndSubjectId(timetable.getSection().getId(),
						timetable.getSubject().getId());

				lectureNum = (int) previousCount + 1;
			}

			CurrentClassResponse response = new CurrentClassResponse();

			response.setTimetableEntryId(timetable.getId());
			response.setSubjectName(timetable.getSubject().getName());
			response.setSectionName(timetable.getSection().getName());
			response.setStartTime(timetable.getStartTime());
			response.setEndTime(timetable.getEndTime());
			response.setSemester(timetable.getSection().getSemester());
			response.setLectureNum(lectureNum);
			response.setSubjectId(timetable.getSubject().getId());
			response.setSectionId(timetable.getSection().getId());

			if (existingSession.isPresent()) {
				response.setAttendanceMarked(existingSession.get().getAttendanceMarked());
				response.setSessionId(existingSession.get().getId());
			} else {
				response.setAttendanceMarked(false);
				response.setSessionId(null);
			}

			return response;
		}).toList();
	}

	@Override
	public List<FacultySessions> getFacultySessions(String facultyId, LocalDate fromDate, LocalDate toDate) {
		List<AttendanceSession> sessions;

		if (fromDate != null && toDate != null) {
			sessions = sessionRepo.findByFacultyIdAndDateBetweenOrderByDateDesc(facultyId, fromDate, toDate);
		} else {
			sessions = sessionRepo.findByFacultyIdOrderByDateDesc(facultyId);
		}

		return sessions.stream().map(session -> {

			FacultySessions resp = new FacultySessions();

			resp.setSessionId(session.getId());
			resp.setDate(session.getDate());

			resp.setSubjectName(session.getSubject().getName());
			resp.setSectionName(session.getSection().getName());

			resp.setLectureNum(session.getLectureNum());

			resp.setStartTime(session.getStartTime());
			resp.setEndTime(session.getEndTime());

			resp.setAttendanceMarked(session.getAttendanceMarked());
			resp.setSemester(session.getSemester());
			resp.setBranch(session.getBranch().getCode());

			return resp;

		}).toList();

}

	@Override
	public AttendanceOverviewResponse getStudentAttendanceSummary(String subjectId, String sectionId) {
		Subject subject = subjectRepo.findById(subjectId)
	            .orElseThrow(() -> new RuntimeException("Subject not found"));

	    Section section = sectionRepo.findById(sectionId)
	            .orElseThrow(() -> new RuntimeException("Section not found"));

	    List<Student> students = studentRepo
	            .findBySection_IdOrderByRollNumberAsc(sectionId);

	    List<StudentAttendanceSummaryDTO> studentDTOs = new ArrayList<>();

	    long totalClasses = sessionRepo.countBySectionIdAndSubjectId(sectionId, subjectId);

	    long totalPercentageSum = 0;
	    long below75 = 0;

	    for (Student student : students) {

	        long total = attendanceRepo
	                .countByStudentIdAndSession_Subject_Id(
	                        student.getId(), subjectId);

	        long present = attendanceRepo
	                .countByStudentIdAndSession_Subject_IdAndStatus(
	                        student.getId(), subjectId, AttendanceStatus.PRESENT);

	        long absent = total - present;

	        double percentage = total == 0 ? 0 : (present * 100.0) / total;

	        if (percentage < 75) below75++;

	        totalPercentageSum += percentage;

	        StudentAttendanceSummaryDTO dto = new StudentAttendanceSummaryDTO();

	        dto.setStudentId(student.getId());
	        dto.setName(student.getName());
	        dto.setRollNumber(student.getRollNumber());

	        dto.setPresentCount(present);
	        dto.setAbsentCount(absent);
	        dto.setTotalClasses(total);

	        dto.setPercentage(Math.round(percentage * 100.0) / 100.0);

	        studentDTOs.add(dto);
	    }

	    double avgAttendance = students.isEmpty() ? 0 :
	            totalPercentageSum / students.size();

	    AttendanceSummaryDTO summary = new AttendanceSummaryDTO();

	    summary.setSubjectName(subject.getName());
	    summary.setSectionName(section.getName());

	    summary.setTotalClasses(totalClasses);
	    summary.setAverageAttendance(Math.round(avgAttendance * 100.0) / 100.0);
	    summary.setBelow75Count(below75);

	    return new AttendanceOverviewResponse(summary, studentDTOs);
	}
}
