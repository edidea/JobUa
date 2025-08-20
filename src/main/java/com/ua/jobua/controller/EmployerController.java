package com.ua.jobua.controller;

import com.ua.jobua.dto.EmployerSetupRequest;
import com.ua.jobua.model.Employer;
import com.ua.jobua.model.User;
import com.ua.jobua.repository.EmployerRepository;
import com.ua.jobua.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EmployerController {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

    private User currentUserOrThrow(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) throw new RuntimeException("Unauthenticated");
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> myEmployerProfile(Authentication auth) {
        User user = currentUserOrThrow(auth);
        var emp = employerRepository.findByUser_Id(user.getId()).orElse(null);
        return ResponseEntity.ok(emp);
    }

    @PostMapping("/setup")
    @Transactional
    public ResponseEntity<?> completeEmployerProfile(@RequestBody EmployerSetupRequest r, Authentication auth) {
        User user = currentUserOrThrow(auth);
        if (!"Employer".equalsIgnoreCase(user.getRole()))
            return ResponseEntity.status(403).body("Forbidden (role)");

        user.setPhone(r.getPhone());
        userRepository.save(user);

        Employer employer = employerRepository.findByUser_Id(user.getId()).orElse(
                Employer.builder().user(user).build()
        );
        employer.setCompanyName(r.getCompanyName());
        if (employer.getCompanyField() == null || employer.getCompanyField().isBlank()) {
            employer.setCompanyField("—");
        }
        employerRepository.save(employer);

        return ResponseEntity.ok("Employer profile completed");
    }
}
