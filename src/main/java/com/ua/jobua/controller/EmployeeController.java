package com.ua.jobua.controller;

import com.ua.jobua.dto.EmployeeSetupRequest;
import com.ua.jobua.model.City;
import com.ua.jobua.model.Employee;
import com.ua.jobua.model.User;
import com.ua.jobua.repository.CityRepository;
import com.ua.jobua.repository.EmployeeRepository;
import com.ua.jobua.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EmployeeController {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final CityRepository cityRepository;

    private User currentUserOrThrow(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) throw new RuntimeException("Unauthenticated");
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> myEmployeeProfile(Authentication auth) {
        User user = currentUserOrThrow(auth);
        var emp = employeeRepository.findByUser_Id(user.getId()).orElse(null);
        return ResponseEntity.ok(emp);
    }

    @PostMapping("/setup")
    @Transactional
    public ResponseEntity<?> completeProfile(@RequestBody EmployeeSetupRequest r, Authentication auth) {
        User user = currentUserOrThrow(auth);
        if (!"Employee".equalsIgnoreCase(user.getRole()))
            return ResponseEntity.status(403).body("Forbidden (role)");

        user.setPhone(r.getPhone());
        userRepository.save(user);

        City city = null;
        if (r.getCityId() != null) {
            city = cityRepository.findById(r.getCityId())
                    .orElse(null);
        }

        Employee emp = employeeRepository.findByUser_Id(user.getId()).orElse(
                Employee.builder().user(user).build()
        );
        emp.setCity(city);
        emp.setVeteran(r.isVeteran());
        emp.setBirthDate(r.getBirthDate());
        emp.setAddress(r.getAddress());
        employeeRepository.save(emp);

        return ResponseEntity.ok("Employee profile completed");
    }
}
