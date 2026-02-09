package com.enterprise.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.FacultyRequest;
import com.enterprise.dto.request.FacultySubjectAssignRequest;
import com.enterprise.dto.response.FacultyResponse;
import com.enterprise.entity.Branch;
import com.enterprise.entity.Department;
import com.enterprise.entity.Faculty;
import com.enterprise.entity.FacultySubject;
import com.enterprise.entity.Subject;
import com.enterprise.entity.User;
import com.enterprise.enums.FacultyStatus;
import com.enterprise.enums.Role;
import com.enterprise.repository.BranchRepository;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.repository.FacultyRepository;
import com.enterprise.repository.FacultySubjectRespository;
import com.enterprise.repository.SubjectRepository;
import com.enterprise.repository.UserRepository;
import com.enterprise.service.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService {

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
	
	@Autowired
	private FacultySubjectRespository facultySubjectRepo;

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

	@Override
	public FacultyResponse addFaculty(FacultyRequest facreq) {
		Department dept = deptRepo.findById(facreq.getDepartmentId())
				.orElseThrow(() -> new IllegalArgumentException("No Such Department exists"));
		Branch branch = branchRepo.findById(facreq.getBranchId())
				.orElseThrow(() -> new IllegalArgumentException("No such Branch exists within given Department"));

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

		facultyRepo.save(faculty);
		
		User us = new User();
		us.setUsername(faculty.getFacultyCode());
		us.setEnabled(true);
		us.setPassword(passwordEncoder.encode("password"));
		us.setRole(Role.FACULTY);
		us.setRefId(faculty.getId());
		
		userRepo.save(us);

		FacultyResponse facresp = new FacultyResponse();
		facresp.setId(faculty.getId());
		facresp.setBranchName(branch.getName());
		facresp.setDepartmentName(dept.getName());
		facresp.setDesignation(faculty.getDesignation());
		facresp.setFacultyCode(faculty.getFacultyCode());
		facresp.setName(faculty.getName());
		facresp.setStatus(faculty.getStatus());

		return facresp;
	}

	@Override
	public List<FacultyResponse> getAllFaculties() {
		List<Faculty> faculties = facultyRepo.findAll();

		return faculties.stream().map(faculty -> {
			FacultyResponse facresp = new FacultyResponse();
			facresp.setBranchName(faculty.getBranch().getName());
			facresp.setDepartmentName(faculty.getDepartment().getName());
			facresp.setDesignation(faculty.getDesignation());
			facresp.setFacultyCode(faculty.getFacultyCode());
			facresp.setName(faculty.getName());
			facresp.setStatus(faculty.getStatus());

			return facresp;
		}).toList();
	}

	@Override
	public FacultyResponse getFacultyById(String Id) {
		Faculty faculty = facultyRepo.findById(Id).orElseThrow(() -> new RuntimeException("No Faculty found"));
		FacultyResponse facresp = new FacultyResponse();
		facresp.setBranchName(faculty.getBranch().getName());
		facresp.setDepartmentName(faculty.getDepartment().getName());
		facresp.setDesignation(faculty.getDesignation());
		facresp.setFacultyCode(faculty.getFacultyCode());
		facresp.setName(faculty.getName());
		facresp.setStatus(faculty.getStatus());

		return facresp;
	}

	@Override
	public List<FacultyResponse> getFacultyByDept(String deptId) {
		List<Faculty> faculties = facultyRepo.findByDepartmentId(deptId);
		return faculties.stream().map(faculty -> {
			FacultyResponse facresp = new FacultyResponse();
			facresp.setBranchName(faculty.getBranch().getName());
			facresp.setDepartmentName(faculty.getDepartment().getName());
			facresp.setDesignation(faculty.getDesignation());
			facresp.setFacultyCode(faculty.getFacultyCode());
			facresp.setName(faculty.getName());
			facresp.setStatus(faculty.getStatus());

			return facresp;
		}).toList();

	}

	@Override
	public FacultyResponse changeStatus(String factId, FacultyStatus status) {
		Faculty faculty = facultyRepo.findById(factId).orElseThrow(() -> new RuntimeException("No Faculty found"));
		faculty.setStatus(status);
		
		facultyRepo.save(faculty);

		FacultyResponse facresp = new FacultyResponse();
		facresp.setBranchName(faculty.getBranch().getName());
		facresp.setDepartmentName(faculty.getDepartment().getName());
		facresp.setDesignation(faculty.getDesignation());
		facresp.setFacultyCode(faculty.getFacultyCode());
		facresp.setName(faculty.getName());
		facresp.setStatus(faculty.getStatus());

		return facresp;
	}

	@Override
	public FacultySubject assignSubject(FacultySubjectAssignRequest assignReq) {
		Faculty faculty = facultyRepo.findById(assignReq.getFacultyId())
				.orElseThrow(() -> new RuntimeException("Faculty not found"));

		Subject subject = subjectRepo.findById(assignReq.getSubjectId())
				.orElseThrow(() -> new RuntimeException("Subject not found"));
		
		FacultySubject fs = new FacultySubject();
		fs.setFaculty(faculty);
		fs.setSemester(assignReq.getSemester());
		fs.setSubject(subject);

		return facultySubjectRepo.save(fs);
	}

}
