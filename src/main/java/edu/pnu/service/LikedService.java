package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Liked;
import edu.pnu.domain.Member;
import edu.pnu.persistence.LikedRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class LikedService {
    
    @Autowired
    private LikedRepository likedRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    // 스크랩 저장
    public void saveLiked(String userId, String title, String content) {
        
        Member m = memberRepository.findByUsername(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown userId: " + userId));
    	
    	Liked liked = Liked.builder()
                .member(m)
                .title(title)
                .content(content)
                .build();
        likedRepository.save(liked);
    }
    
    // 사용자별 스크랩 조회
    public List<Liked> getLikedList(String userId) {
    	return likedRepository.findByMember_UsernameOrderByTimestampDesc(userId);
    }
}