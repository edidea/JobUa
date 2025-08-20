package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(length = 50)
    private String phone;

    @Column(nullable = false, length = 50)
    private String role; // consider enum

    @Column(nullable = false, length = 10)
    private String language; // consider enum like {UK, EN}

    // Saved articles (many-to-many via user_saved_articles)
    @ManyToMany
    @JoinTable(
            name = "user_saved_articles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    @Builder.Default
    private Set<Article> savedArticles = new HashSet<>();
}
