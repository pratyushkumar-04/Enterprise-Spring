package com.enterprise.serviceimpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.enterprise.specification.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enterprise.dto.request.RollNumrequest;
import com.enterprise.dto.request.SectionAssignRequest;
import com.enterprise.dto.request.StudentRequest;
import com.enterprise.dto.response.StudentResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Course;
import com.enterprise.entity.Department;
import com.enterprise.entity.Section;
import com.enterprise.entity.Student;
import com.enterprise.entity.StudentAddress;
import com.enterprise.entity.User;
import com.enterprise.enums.Role;
import com.enterprise.enums.StudentStatus;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.CourseRepository;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.repository.SectionRepository;
import com.enterprise.repository.StudentAddressRepository;
import com.enterprise.repository.StudentRepository;
import com.enterprise.repository.UserRepository;
import com.enterprise.service.StudentService;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

	private final PasswordEncoder passwordEncoder;

	@Value("${student.image.upload-dir}")
	private String uploadDir;

	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private BranchRepository branchRepo;
	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private StudentAddressRepository addressRepo;
	@Autowired
	private SectionRepository sectionRepo;

	StudentServiceImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private void validateSectionBranch(Section section, Branch branch) {
		if (!Objects.equals(section.getBranch().getId(), branch.getId())) {
			throw new RuntimeException("The selected Section does not belong to the selected Branch");
		}
	}

	private StudentResponse mapToResponse(Student student) {
		StudentResponse studResp = new StudentResponse();
		studResp.setId(student.getId());
		studResp.setAdmissionNumber(student.getAdmissionNumber());
		studResp.setAdmissionYear(student.getAdmissionYear());
		studResp.setBranchName(student.getBranch().getName());
		studResp.setCourseName(student.getCourse().getName());
		studResp.setCurrentSemester(student.getCurrentSemester());
		studResp.setDateOfBirth(student.getDateOfBirth());
		studResp.setDepartmentName(student.getDepartment().getName());
		studResp.setEmail(student.getEmail());
		studResp.setFatherName(student.getFatherName());
		studResp.setGender(student.getGender());
		studResp.setMotherName(student.getMotherNAme());
		studResp.setName(student.getName());
		studResp.setPhone(student.getPhone());
		studResp.setProfileImagePath(student.getImgPath());
		studResp.setAdhaarpath(student.getAdhaarPath());
		studResp.setTenthMarksheet(student.getTenthPath());
		studResp.setTwelthMarksheet(student.getTwelthPath());
		studResp.setStatus(student.getStatus());
		studResp.setSectionname(student.getSection().getName());
		studResp.setRollnumber(student.getRollNumber());
		studResp.setDepartmentId(student.getDepartment().getId());
		studResp.setCourseId(student.getCourse().getId());
		studResp.setBranchId(student.getBranch().getId());

		return studResp;
	}

	// unused for new logic
	private String saveStudentImage(MultipartFile image, String username) {

		try {
			Files.createDirectories(Paths.get(uploadDir));

			String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));

			String fileName = username + extension;
			Path filePath = Paths.get(uploadDir + fileName);

			Files.write(filePath, image.getBytes());

			return "/student-images/" + fileName;

		} catch (IOException e) {
			throw new RuntimeException("Image upload Failed", e);
		}
	}

	private String saveFile(MultipartFile file, String folderPath, String fileType) {

		try {
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

			String fileName = fileType + extension;

			Path filePath = Paths.get(folderPath + fileName);

			Files.write(filePath, file.getBytes());

			return folderPath + fileName;

		} catch (IOException e) {
			throw new RuntimeException(fileType + " upload failed", e);
		}
	}

	private String generateAdmissionNumber(String branchCode) {
		String year = String.valueOf(LocalDate.now().getYear());
		String prefix = year + "_" + branchCode + "_";
		Student lastStudent = studentRepo.findTopByAdmissionNumberStartingWithOrderByAdmissionNumberDesc(prefix);
		int nextNumber = 1;
		if (lastStudent != null) {
			String lastCode = lastStudent.getAdmissionNumber();
			String numberPart = lastCode.substring(prefix.length());
			nextNumber = Integer.parseInt(numberPart) + 1;
		}
		return prefix + String.format("%03d", nextNumber);
	}

	@Override
	public StudentResponse addStudent(StudentRequest studentReq, MultipartFile image, MultipartFile adhaar,
			MultipartFile tenth, MultipartFile twelfth) {
		Department dept = deptRepo.findById(studentReq.getDepartmentId())
				.orElseThrow(() -> new RuntimeException("No such Department Found"));
		Course course = courseRepo.findById(studentReq.getCourseId())
				.orElseThrow(() -> new RuntimeException("No such Course Found"));
		Branch branch = branchRepo.findById(studentReq.getBranchId())
				.orElseThrow(() -> new RuntimeException("No Such Branch Found"));
		Section section = sectionRepo.findById(studentReq.getSectionId())
				.orElseThrow(() -> new RuntimeException("No Such Section Exists"));
		Student student = new Student();

		validateSectionBranch(section, branch);

		student.setBranch(branch);
		student.setCourse(course);
		student.setCurrentSemester(studentReq.getCurrentSemester());
		student.setDateOfBirth(studentReq.getDateOfBirth());
		student.setDepartment(dept);
		student.setEmail(studentReq.getEmail());
		student.setFatherName(studentReq.getFatherName());
		student.setGender(studentReq.getGender());
		student.setMotherNAme(studentReq.getMotherName());
		student.setName(studentReq.getName());
		student.setPhone(studentReq.getPhone());
		student.setStatus(StudentStatus.ACTIVE);

		String admissionNum = generateAdmissionNumber(branch.getCode());
//		student.setAdmissionNumber(generateAdmissionNumber(branch.getCode()));
		student.setAdmissionNumber(admissionNum);
		student.setAdmissionYear(studentReq.getAdmissionYear());
		student.setSection(section);

		Student savedStud = studentRepo.save(student);

		// new Section
		String studentFolder = uploadDir + admissionNum + "/";
		try {
			Files.createDirectories(Paths.get(studentFolder));
		} catch (IOException e) {
			throw new RuntimeException("Failed to create student folder", e);
		}

//		if (image != null && !image.isEmpty()) {
//			String path = saveStudentImage(image, savedStud.getAdmissionNumber());
//			savedStud.setImgPath(path);
//			studentRepo.save(savedStud);
//		}

		if (image != null && !image.isEmpty()) {
			String imgpath = saveFile(image, studentFolder, "image");
			savedStud.setImgPath(imgpath);
		}
		if (adhaar != null && !adhaar.isEmpty()) {
			savedStud.setAdhaarPath(saveFile(adhaar, studentFolder, "adhaar"));
		}

		if (tenth != null && !tenth.isEmpty()) {
			savedStud.setTenthPath(saveFile(tenth, studentFolder, "10thMarksheet"));
		}

		if (twelfth != null && !twelfth.isEmpty()) {
			savedStud.setTwelthPath(saveFile(twelfth, studentFolder, "12thMarksheet"));
		}

		studentRepo.save(savedStud);

		StudentAddress studAddress = new StudentAddress();
		studAddress.setAddressLine1(studentReq.getAddress().getAddressLine1());
		studAddress.setAddressLine2(studentReq.getAddress().getAddressLine2());
		studAddress.setCity(studentReq.getAddress().getCity());
		studAddress.setPincode(studentReq.getAddress().getPincode());
		studAddress.setState(studentReq.getAddress().getState());
		studAddress.setStudent(savedStud);

		addressRepo.save(studAddress);

		User us = new User();
		us.setEnabled(true);
		us.setPassword(passwordEncoder.encode("password"));
		us.setUsername(student.getAdmissionNumber());
		us.setRefId(savedStud.getId());
		us.setRole(Role.STUDENT);

		userRepo.save(us);

		return mapToResponse(savedStud);
	}

	@Override
	public Page<StudentResponse> getAllstudents(
			Pageable pageable,
			String search,
			String departmentId,
			String courseId,
			String branchId,
			Integer semester,
			StudentStatus status
	) {
		Specification<Student> spec = (root, query, cb) -> cb.conjunction();

		if (departmentId != null && !departmentId.isBlank()) {
			spec = spec.and(StudentSpecification.hasDepartment(departmentId));
		}
		if (courseId != null && !courseId.isBlank()) {
			spec = spec.and(StudentSpecification.hasCourse(courseId));
		}
		if (branchId != null && !branchId.isBlank()) {
			spec = spec.and(StudentSpecification.hasBranch(branchId));
		}
		if (semester != null) {
			spec = spec.and(StudentSpecification.hasSemester(semester));
		}
		if (status != null) {
			spec = spec.and(StudentSpecification.hasStatus(status));
		}
		if (search != null && !search.isBlank()) {
			spec = spec.and(StudentSpecification.containsSearch(search));
		}
		Page<Student> students = studentRepo.findAll(spec, pageable);

		return students.map(this::mapToResponse);
	}

	@Override
	public StudentResponse getStudentById(String Id) {
		Student student = studentRepo.findById(Id).orElseThrow(() -> new RuntimeException("No such student exists"));
		return mapToResponse(student);
	}

	@Override
	public StudentResponse getStudentByAdmnum(String admnum) {
		Student student = studentRepo.findByAdmissionNumber(admnum)
				.orElseThrow(() -> new RuntimeException("No such student exists"));
		return mapToResponse(student);
	}

	@Override
	public List<StudentResponse> getStudentsByBranch(String branchId) {
		List<Student> students = studentRepo.findByBranchId(branchId);
		return students.stream().map(student -> {
			return mapToResponse(student);
		}).toList();
	}

	@Override
	public List<StudentResponse> getStudentBySem(Integer semester) {
		List<Student> students = studentRepo.findByCurrentSemester(semester);
		return students.stream().map(student -> {
			return mapToResponse(student);
		}).toList();
	}

	@Override
	public List<StudentResponse> getStudentByStatus(StudentStatus status) {
		List<Student> students = studentRepo.findByStatus(status);
		return students.stream().map(student -> {
			return mapToResponse(student);
		}).toList();
	}

	@Override
	public StudentResponse modifyStatus(String Id, StudentStatus status) {
		Student student = studentRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Student Found"));
		student.setStatus(status);
		Student savedstud = studentRepo.save(student);
		return mapToResponse(savedstud);
	}

	@Override
	public StudentResponse promotion(String Id) {
		Student student = studentRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Student Found"));
		student.setCurrentSemester(student.getCurrentSemester() + 1);
		Student savedstud = studentRepo.save(student);
		return mapToResponse(savedstud);
	}

	@Override
	public StudentResponse edit(String Id, StudentRequest studentReq) {
		Student student = studentRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Such Student Exists"));
		Department dept = deptRepo.findById(studentReq.getDepartmentId())
				.orElseThrow(() -> new RuntimeException("No such Department Found"));
		Course course = courseRepo.findById(studentReq.getCourseId())
				.orElseThrow(() -> new RuntimeException("No such Course Found"));
		Branch branch = branchRepo.findById(studentReq.getBranchId())
				.orElseThrow(() -> new RuntimeException("No Such Branch Found"));
		student.setBranch(branch);
		student.setCourse(course);
		student.setCurrentSemester(studentReq.getCurrentSemester());
		student.setDateOfBirth(studentReq.getDateOfBirth());
		student.setDepartment(dept);
		student.setEmail(studentReq.getEmail());
		student.setFatherName(studentReq.getFatherName());
		student.setGender(studentReq.getGender());
		student.setMotherNAme(studentReq.getMotherName());
		student.setName(studentReq.getName());
		student.setPhone(studentReq.getPhone());
		student.setAdmissionYear(student.getAdmissionYear());

		Student Savedstud = studentRepo.save(student);
		return mapToResponse(Savedstud);
	}

	@Override
	public void assignRoll(String Id, RollNumrequest req) {
		Student student = studentRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Such Student Exists"));

		student.setRollNumber(req.getRollnum());
		studentRepo.save(student);
	}

	@Transactional
	@Override
	public void assignSection(String Id, SectionAssignRequest sec) {
		Student student = studentRepo.findById(Id.trim())
				.orElseThrow(() -> new RuntimeException("No Such Student Exists"));

		Section section = sectionRepo.findById(sec.getSectionId())
				.orElseThrow(() -> new RuntimeException("No Such Section Exists"));
		student.setSection(section);
		studentRepo.save(student);

	}

	@Override
	@Transactional
	public void generateRollNumbersForSection(String sectionId) {
		List<Student> students = studentRepo.findBySectionIdOrderByAdmissionNumberAsc(sectionId);

		if (students.isEmpty()) {
			throw new RuntimeException("No students found in this section");
		}

		int rollNumber = 1;

		for (Student student : students) {
			student.setRollNumber(rollNumber++);
		}

		studentRepo.saveAll(students);

	}

	@Override
	public List<StudentResponse> getStudentsBySection(String sectionId) {
		List<Student> students = studentRepo.findBySection_IdOrderByRollNumberAsc(sectionId);
		return students.stream().map(student -> {
			return mapToResponse(student);
		}).toList();
	}

	@Override
	public List<StudentResponse> getStudentsWithoutRoll(String sectionId) {
		List<Student> students = studentRepo.findBySectionIdAndRollNumberIsNull(sectionId);

		return students.stream().map(student -> {
			return mapToResponse(student);
		}).toList();

	}

	@Override
	public Integer getMaxRollNumber(String sectionId) {

		Integer max = studentRepo.findMaxRollNumberBySection(sectionId);
		return max == null ? 0 : max;
	}

	@Override
	public Resource getImage(String Id) throws MalformedURLException {
		Student student = studentRepo.findById(Id)
				.orElseThrow(()-> new RuntimeException("No Student Found"));

	    Path path = Paths.get(student.getImgPath());
	    Resource resource = new UrlResource(path.toUri());
	    return resource;

	}

	@Override
	public Resource getDocument(String Id, String type) throws MalformedURLException {
		Student student = studentRepo.findById(Id)
				.orElseThrow(()-> new RuntimeException("No Such Student Exists"));

		String filePath = switch (type) {
		case "adhaar" -> student.getAdhaarPath();
		case "tenth" -> student.getTenthPath();
		case "twelth" -> student.getTwelthPath();
		default -> throw new RuntimeException("Invalid document type");
		};

		Path path = Paths.get(filePath);
		Resource resource = new UrlResource(path.toUri());
		return resource;
	}
}
