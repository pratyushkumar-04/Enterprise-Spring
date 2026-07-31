package com.enterprise.serviceimpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enterprise.dto.request.ChangePasswordRequest;
import com.enterprise.dto.request.FacultyModifyRequest;
import com.enterprise.dto.request.FacultyRequest;
import com.enterprise.dto.request.SubjectFacultySectionAssignmentRequestDTO;
import com.enterprise.dto.response.FacultyResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Course;
import com.enterprise.entity.Department;
import com.enterprise.entity.Faculty;
//import com.enterprise.entity.FacultySubject;
import com.enterprise.entity.Subject;
import com.enterprise.entity.User;
import com.enterprise.enums.FacultyStatus;
import com.enterprise.enums.Role;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.CourseRepository;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.repository.FacultyRepository;
//import com.enterprise.repository.FacultySubjectRespository;
import com.enterprise.repository.SubjectRepository;
import com.enterprise.repository.UserRepository;
import com.enterprise.service.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService {
	
	@Value("${faculty.upload-dir}")
	private String uploadDir;
    private final PasswordEncoder passwordEncoder;

	@Autowired
	private FacultyRepository facultyRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private SubjectRepository subjectRepo;	
	@Autowired
	private BranchRepository branchRepo;
//	@Autowired
//	private FacultySubjectRespository facultySubjectRepo;
	@Autowired
	private CourseRepository courseRepo;

    FacultyServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	private String generateFacultyCode() {

		String year = String.valueOf(LocalDate.now().getYear());
		String prefix = "FAC" + year + "_";

		Faculty lastFaculty = facultyRepo.findTopByOrderByFacultyCodeDesc();

		int nextNumber = 1;

		if (lastFaculty != null && lastFaculty.getFacultyCode().startsWith(prefix)) {
			String lastCode = lastFaculty.getFacultyCode();
			String numberPart = lastCode.substring(prefix.length());
			nextNumber = Integer.parseInt(numberPart) + 1;
		}

		return prefix + String.format("%03d", nextNumber);
	}
	
	public String saveFile(MultipartFile file, String folderPath, String fileType) {
        try {
            if (file == null || file.isEmpty()) return null;

            String extension = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));

            String fileName = fileType + extension;

            Path path = Paths.get(folderPath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Path filePath = path.resolve(fileName);

            Files.write(filePath, file.getBytes());

            return filePath.toString(); // store path

        } catch (IOException e) {
            throw new RuntimeException(fileType + " upload failed", e);
        }
    }

	public FacultyResponse maptoResponse(Faculty faculty) {
		FacultyResponse facresp = new FacultyResponse();
		facresp.setId(faculty.getId());
		facresp.setBranchName(faculty.getBranch().getName());
		facresp.setDepartmentName(faculty.getDepartment().getName());
		facresp.setDesignation(faculty.getDesignation());
		facresp.setFacultyCode(faculty.getFacultyCode());
		facresp.setName(faculty.getName());
		facresp.setStatus(faculty.getStatus());
		facresp.setEmail(faculty.getEmail());
		facresp.setPhone(faculty.getPhone());
		facresp.setCourseName(faculty.getCourse().getName());
		facresp.setQualification(faculty.getQualifications());
		return facresp;
	}
	
	@Override
	public FacultyResponse addFaculty(FacultyRequest facreq, MultipartFile image, MultipartFile cv) {
		Department dept = deptRepo.findById(facreq.getDepartmentId())
				.orElseThrow(() -> new IllegalArgumentException("No Such Department exists"));
		Course course = courseRepo.findById(facreq.getCourseId())
				.orElseThrow(()-> new RuntimeException("No course within that department"));
		Branch branch = branchRepo.findById(facreq.getBranchId())
				.orElseThrow(() -> new IllegalArgumentException("No such Branch exists within given Course"));

		Faculty faculty = new Faculty();

		faculty.setBranch(branch);
		faculty.setDepartment(dept);
		faculty.setDesignation(facreq.getDesignation());
		faculty.setEmail(facreq.getEmail());
		faculty.setName(facreq.getName());
		faculty.setPhone(facreq.getPhone());
		faculty.setStatus(FacultyStatus.ACTIVE);
		faculty.setFacultyCode(generateFacultyCode());
		faculty.setJoiningDate(LocalDate.now());
		faculty.setCourse(course);
		faculty.setQualifications(facreq.getQualification());

		Faculty savedFaculty= facultyRepo.save(faculty);
	    String facultyFolder = uploadDir + savedFaculty.getFacultyCode() + "/";
	    
	    String imgpath = saveFile(image, facultyFolder, "image");
	    String cvpath = saveFile(cv, facultyFolder, "cv");
	    
	    savedFaculty.setCvPath(cvpath);
	    savedFaculty.setImagePath(imgpath);
	    
	    facultyRepo.save(savedFaculty);

		
		User us = new User();
		us.setUsername(faculty.getFacultyCode());
		us.setEnabled(true);
		us.setPassword(passwordEncoder.encode("password"));
		us.setRole(Role.FACULTY);
		us.setRefId(faculty.getId());
		
		userRepo.save(us);

		return maptoResponse(savedFaculty);
	}

	@Override
	public Page<FacultyResponse> getAllFaculties(Pageable pageable) {
		Page<Faculty> faculties = facultyRepo.findAll(pageable);
		return faculties.map(this::maptoResponse);
	}

	@Override
	public FacultyResponse getFacultyById(String Id) {
		Faculty faculty = facultyRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Faculty found"));
		return maptoResponse(faculty);
	}

	@Override
	public List<FacultyResponse> getFacultyByDept(String deptId) {
		List<Faculty> faculties = facultyRepo.findByDepartmentId(deptId);
		return faculties.stream().map(faculty -> {
			return maptoResponse(faculty);
		}).toList();

	}

	@Override
	public FacultyResponse changeStatus(String factId, FacultyStatus status) {
		Faculty faculty = facultyRepo.findById(factId).orElseThrow(() -> new RuntimeException("No Faculty found"));
		faculty.setStatus(status);
		
		facultyRepo.save(faculty);

		return maptoResponse(faculty);
	}

//	@Override
//	public FacultySubject assignSubject(SubjectFacultySectionAssignmentRequestDTO assignReq) {
//		Faculty faculty = facultyRepo.findById(assignReq.getFacultyId())
//				.orElseThrow(() -> new RuntimeException("Faculty not found"));
//
//		Subject subject = subjectRepo.findById(assignReq.getSubjectId())
//				.orElseThrow(() -> new RuntimeException("Subject not found"));
//		
//		FacultySubject fs = new FacultySubject();
//		fs.setFaculty(faculty);
//		fs.setSemester(assignReq.getSemester());
//		fs.setSubject(subject);
//
//		return facultySubjectRepo.save(fs);
//	}

	@Override
	public Resource getCv(String Id) throws MalformedURLException {
		Faculty fac= facultyRepo.findById(Id)
				.orElseThrow(()->new RuntimeException("No Such Faculty Found"));
		String filePath = fac.getCvPath();
		Path path = Paths.get(filePath);
		Resource resource = new UrlResource(path.toUri());
		return resource;
	}

	@Override
	public FacultyResponse editFaculty(String id, FacultyModifyRequest req) {
		Faculty faculty = facultyRepo.findById(id)
				.orElseThrow(()->new RuntimeException("No Such Faculty Found"));


		
		faculty.setName(req.getName());
		faculty.setEmail(req.getEmail());
		faculty.setPhone(req.getPhone());
		faculty.setDesignation(req.getDesignation());

		facultyRepo.save(faculty);
		
		return maptoResponse(faculty);
	}

	@Override
	public Resource getImage(String Id) throws MalformedURLException {
		Faculty faculty = facultyRepo.findById(Id)
				.orElseThrow(()-> new RuntimeException("No faculty Found"));

	    Path path = Paths.get(faculty.getImagePath());
	    Resource resource = new UrlResource(path.toUri());
	    return resource;
	}

	@Override
	public List<FacultyResponse> getbyBranch(String branchId) {
		Branch branch = branchRepo.findById(branchId)
				.orElseThrow(()-> new RuntimeException("No Such Branch Exists"));
		
		List<Faculty> faculties = facultyRepo.findByBranchId(branchId);
		return faculties.stream().map(faculty -> {
			return maptoResponse(faculty);
		}).toList();
	}

	@Override
	public void changePassword(ChangePasswordRequest req) {
		 User user = userRepo.findByUsername(req.getUsername())
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
		        throw new RuntimeException("Invalid current password");
		    }

		    if (passwordEncoder.matches(req.getNewPassword(), user.getPassword())) {
		        throw new RuntimeException("New password cannot be same as old password");
		    }

		    user.setPassword(passwordEncoder.encode(req.getNewPassword()));
		    userRepo.save(user);
		
	}

}
