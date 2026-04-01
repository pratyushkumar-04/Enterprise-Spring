package com.enterprise.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.dto.request.DepartmentReq;
import com.enterprise.entity.Department;
import com.enterprise.repository.DepartmentRepository;
import com.enterprise.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	
	@Autowired
	private DepartmentRepository Depatrepo;
	
	@Override
	public Department addDepartment(Department dp) {
		if(Depatrepo.existsByName(dp.getName()))
		{
			throw new IllegalArgumentException("Department Already Exists");
		}
		return Depatrepo.save(dp);
	}

	@Override
	public Optional<Department> getDepartment(String id) {
		return Depatrepo.findById(id);
	}

	@Override
	public List<Department> fetchAllDepartments() {
		return Depatrepo.findAll();
	}

	@Override
	public Department editDepartment(String Id,DepartmentReq req) {
		Department dept = Depatrepo.findById(Id).
				orElseThrow(() -> new RuntimeException("No Such Department found"));
		dept.setName(req.getName());
		dept.setCode(req.getCode());
		
		Depatrepo.save(dept);
		return dept;
	}
	

}
