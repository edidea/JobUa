package com.ua.jobua.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "articles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String author;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    private String type; // 'стаття' | 'новина' (consider enum)

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(columnDefinition = "text")
    private String image;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;
}
