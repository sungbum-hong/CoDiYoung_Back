package com.cdy.cdy.entity;

import com.cdy.cdy.dto.request.CreateProjectRequest;
import com.cdy.cdy.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "projects")
public class Project  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String description;

    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    private String logoImageUrl; // null 허용 (이미지 나중에 붙일 수 있음)

    @Builder
    private Project(String title, String description, Integer capacity, User manager, String logoImageUrl) {
        this.title = Objects.requireNonNull(title);
        this.description = description;
        this.capacity = capacity;
        this.manager = Objects.requireNonNull(manager);
        this.logoImageUrl = logoImageUrl;
    }

    // 👉 DTO에서 바로 변환할 수 있게 팩토리 메서드
    public static Project from(CreateProjectRequest req, User leader) {
        return Project.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .capacity(req.getCapacity())
                .manager(leader)
                .logoImageUrl(null) // null 가능
                .build();
    }
}
