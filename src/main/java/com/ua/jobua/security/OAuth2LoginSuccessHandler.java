package com.ua.jobua.security;

import com.ua.jobua.model.User;
import com.ua.jobua.repository.EmployeeRepository;
import com.ua.jobua.repository.EmployerRepository;
import com.ua.jobua.repository.UserRepository;
import com.ua.jobua.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String FRONT_BASE = "http://localhost:3000";

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        org.springframework.security.core.Authentication authentication)
            throws IOException {

        if (!(authentication instanceof OAuth2AuthenticationToken oauth2)) {
            response.sendError(500, "Unsupported auth");
            return;
        }

        String registrationId = oauth2.getAuthorizedClientRegistrationId();
        String expectedRole = registrationId != null && registrationId.endsWith("employer")
                ? "Employer" : "Employee";

        OAuth2User principal = oauth2.getPrincipal();
        String email = (String) principal.getAttributes().get("email");
        String name  = (String) principal.getAttributes().getOrDefault("name", email);

        if (email == null || email.isBlank()) {
            response.sendError(400, "Google did not provide email");
            return;
        }

        User user = userRepository.findByEmail(email).orElseGet(() ->
                userRepository.save(User.builder()
                        .email(email)
                        .password(passwordEncoder.encode("oauth2:" + UUID.randomUUID()))
                        .fullName(name)
                        .role(expectedRole)
                        .language("UA")
                        .build())
        );

        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        var newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(newAuth);
        SecurityContextHolder.setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);

        String target;
        if ("Employer".equalsIgnoreCase(user.getRole())) {
            var empOpt = employerRepository.findByUser_Email(user.getEmail());
            boolean hasPhone = user.getPhone() != null && !user.getPhone().isBlank();
            boolean hasCompanyName = empOpt.isPresent()
                    && empOpt.get().getCompanyName() != null
                    && !empOpt.get().getCompanyName().isBlank();

            target = (hasPhone && hasCompanyName)
                    ? "/employer/profile"
                    : "/employer/register/step2";
        } else {
            var empOpt = employeeRepository.findByUser_Email(user.getEmail());
            boolean hasPhone = user.getPhone() != null && !user.getPhone().isBlank();
            boolean hasCity  = empOpt.isPresent() && empOpt.get().getCity() != null;

            target = (hasPhone && hasCity)
                    ? "/employee/profile"
                    : "/employee/register/step2";
        }
        response.sendRedirect(FRONT_BASE + target);
    }
}
