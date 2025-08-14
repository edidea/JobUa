package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "resumes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_card_id")
    private BusinessCard businessCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_id")
    private Education education;

    // Experiences via resume_experiences
    @ManyToMany
    @JoinTable(
            name = "resume_experiences",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id")
    )
    @Builder.Default
    private Set<WorkExperience> experiences = new HashSet<>();

    // Skills via resume_skills
    @ManyToMany
    @JoinTable(
            name = "resume_skills",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();
}
