package com.enterprise.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.AttendanceSessionRequest;
import com.enterprise.dto.request.MarkAttendanceRequest;
import com.enterprise.dto.request.StudentAttendanceRequest;
import com.enterprise.dto.response.AttedanceSessionResponse;
import com.enterprise.dto.response.AttendanceDateWiseResponse;
import com.enterprise.dto.response.AttendanceStudentResponse;
import com.enterprise.dto.response.AttendanceSubjectWiseResponse;
import com.enterprise.dto.response.SubjectAttendanceDetailResponse;
import com.enterprise.entity.AttendanceSession;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Faculty;
import com.enterprise.entity.Section;
import com.enterprise.entity.Student;
import com.enterprise.entity.StudentAttendance;
import com.enterprise.entity.Subject;
import com.enterprise.enums.AttendanceStatus;
import com.enterprise.enums.SubjectStatus;
import com.enterprise.repository.AttendanceRepository;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.FacultyRepository;
import com.enterprise.repository.SectionRepository;
import com.enterprise.repository.StudentAttendanceRepository;
import com.enterprise.repository.StudentRepository;
import com.enterprise.repository.SubjectRepository;
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
		Branch branch = branchRepo.findById(req.getBranchId())
				.orElseThrow(() -> new RuntimeException("No Such Branch found"));
		Faculty faculty = faculyRepo.findById(req.getFacultyId())
				.orElseThrow(() -> new RuntimeException("No such Faculty Exists"));
		Subject subject = subjectRepo.findById(req.getSubjectId())
				.orElseThrow(() -> new RuntimeException("No such Subject found"));
		Section section = sectionRepo.findById(req.getSection())
				.orElseThrow(() -> new RuntimeException("No Such Section found"));

		AttendanceSession session = new AttendanceSession();
		session.setBranch(branch);
		session.setSubject(subject);
		session.setFaculty(faculty);
		session.setDate(req.getDate());
		session.setEndTime(req.getEnd());
		session.setLectureNum(req.getLecNumber());
		session.setStartTime(req.getStart());
		session.setSemester(req.getSem());
		session.setSection(section);

		AttendanceSession createdsession = sessionRepo.save(session);

		return maptoResponse(createdsession);
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
		Subject subject = subjectRepo.findById(subjectId)
				.orElseThrow(() -> new RuntimeException("Subject not found"));
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
}
