package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "vacancies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    // Column name is 'position' in DB; alias to avoid confusion with Position entity
    @Column(name = "position", nullable = false, length = 255)
    private String positionTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "employment_type", nullable = false, length = 50)
    private String employmentType; // consider enum

    @Column(nullable = false)
    private boolean booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "english_level", length = 50)
    private String englishLevel;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    // Skills via vacancy_skills
    @ManyToMany
    @JoinTable(
            name = "vacancy_skills",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();
}
