package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "saved_searches")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SavedSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "search_text", nullable = false, columnDefinition = "text")
    private String searchText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
