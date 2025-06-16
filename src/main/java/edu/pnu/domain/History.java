package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.dto.ResultDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//@JsonIgnoreProperties("histories")
@Getter
@Setter
@ToString(exclude = "member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  
    private Member member;
    
    private String query;  // 사용자가 검색한 키워드

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results = new ArrayList<>();

    
    
//    private LocalDateTime timestamp;


//    @PrePersist
//    protected void onCreate() {
//        this.timestamp = LocalDateTime.now();
//    }
}
