package com.ua.jobua.repository;
import com.ua.jobua.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser_Id(Long userId);
    Optional<Employee> findByUser_Email(String email);
}
