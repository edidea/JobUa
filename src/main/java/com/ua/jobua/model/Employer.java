package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "employers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @Column(name = "company_field", nullable = false, length = 255)
    private String companyField;

    private Integer employeeCount;

    @Column(length = 255)
    private String website;

    @Column(columnDefinition = "text")
    private String logo;

    @Column(columnDefinition = "text")
    private String description;
}
