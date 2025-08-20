package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "work_experiences")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String position;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "company_field", nullable = false, length = 255)
    private String companyField;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
