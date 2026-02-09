package com.enterprise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.entity.AttendanceSession;
import com.enterprise.enums.AttendanceStatus;

public interface AttendanceRepository extends JpaRepository<AttendanceSession, String>{

}
