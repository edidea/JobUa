package com.ua.jobua.controller;

import com.ua.jobua.dto.*;
import com.ua.jobua.model.User;
import com.ua.jobua.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SecurityContextRepository securityContextRepository;

    private String normalizeRole(String rolePath) {
        if ("employee".equalsIgnoreCase(rolePath)) return "Employee";
        if ("employer".equalsIgnoreCase(rolePath)) return "Employer";
        throw new IllegalArgumentException("Unknown role: " + rolePath);
    }

    @PostMapping("/employee/register-basic")
    public ResponseEntity<?> registerEmployeeStep1(@RequestBody EmployeeRegisterStep1Request r,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        if (userRepository.findByEmail(r.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        var user = User.builder()
                .email(r.getEmail())
                .password(passwordEncoder.encode(r.getPassword()))
                .fullName(r.getFullName())
                .role("Employee")
                .language((r.getLanguage() == null || r.getLanguage().isBlank()) ? "UA" : r.getLanguage())
                .build();
        userRepository.save(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(r.getEmail(), r.getPassword())
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok(new AuthResponse(
                "Step1 complete. Continue to Step2",
                user.getEmail(), user.getRole()
        ));
    }

    @PostMapping("/employer/register-basic")
    public ResponseEntity<?> registerEmployerStep1(@RequestBody EmployerRegisterStep1Request r,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        if (userRepository.findByEmail(r.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        var user = User.builder()
                .email(r.getEmail())
                .password(passwordEncoder.encode(r.getPassword()))
                .fullName(r.getFullName())
                .role("Employer")
                .language((r.getLanguage() == null || r.getLanguage().isBlank()) ? "UA" : r.getLanguage())
                .build();
        userRepository.save(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(r.getEmail(), r.getPassword())
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok(new AuthResponse(
                "Step1 complete. Continue to Step2",
                user.getEmail(), user.getRole()
        ));
    }

    @PostMapping("/{role}/login")
    public ResponseEntity<?> login(@PathVariable String role, @RequestBody LoginRequest r,
                                   HttpServletRequest request, HttpServletResponse response) {
        String expectedRole = normalizeRole(role);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(r.getEmail(), r.getPassword())
        );

        User user = userRepository.findByEmail(r.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!expectedRole.equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Role mismatch");
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok(new AuthResponse("Login successful", user.getEmail(), user.getRole()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthenticated");
        }
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(new UserDto(
                user.getId(), user.getEmail(), user.getFullName(),
                user.getPhone(), user.getRole(), user.getLanguage()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        if (request.getSession(false) != null) request.getSession(false).invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out");
    }
}
