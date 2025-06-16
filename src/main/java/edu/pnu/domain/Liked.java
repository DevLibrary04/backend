package edu.pnu.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @Column(nullable = false)
//    private String userId;      // Member와 연관관계 없이 단순 문자열
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Member member;
    
    @Column(nullable = false)
    private String title;       // 스크랩한 콘텐츠 제목
    
    @Column(columnDefinition = "TEXT")
    private String content;     // 스크랩한 콘텐츠 내용
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}