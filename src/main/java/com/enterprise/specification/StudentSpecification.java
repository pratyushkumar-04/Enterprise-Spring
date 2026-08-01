package com.enterprise.specification;

import com.enterprise.entity.Student;
import com.enterprise.enums.StudentStatus;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasDepartment(String deptId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("department").get("Id"),
                deptId
        );
    }

    public static Specification<Student> hasCourse(String courseId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("course").get("id"),
                courseId
        );
    }

    public static Specification<Student> hasBranch(String branchId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("branch").get("id"),
                branchId
        );
    }

    public static Specification<Student> hasSemester(Integer sem) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("currentSemester"),
                sem
        );
    }

    public static Specification<Student> hasStatus(StudentStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("status"),
                status
        );
    }

    public static Specification<Student> containsSearch(String search) {
        return (root, query, CriteriaBuilder) -> {
            String keyword = "%" + search.toLowerCase() + "%";
            return CriteriaBuilder.or(
                    CriteriaBuilder.like(
                            CriteriaBuilder.lower(root.get("name")),
                            keyword
                    ),
                    CriteriaBuilder.like(
                            CriteriaBuilder.lower(root.get("admissionNumber")),
                            keyword
                    ),
                    CriteriaBuilder.like(
                            CriteriaBuilder.lower(root.get("email")),
                            keyword
                    )
            );
        };
    }
}
