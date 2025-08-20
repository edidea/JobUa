package com.ua.jobua.repository;
import com.ua.jobua.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUser_Id(Long userId);
    Optional<Employer> findByUser_Email(String email);
}
