package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Column(name = "cover_letter", columnDefinition = "text")
    private String coverLetter;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;
}
