package edu.pnu;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.History;
import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.HistoryRepository;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class HistoryInit {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private HistoryRepository historyRepo;

    @Autowired
    private PasswordEncoder encoder;
    
    @Test
    public void memberInit() {
        // 1) 저장된 Member 하나 조회 (없으면 생성)
        Member m = memberRepo.save(Member.builder()
                    .username("member")
                    .password(encoder.encode("abcd"))
                    .enabled(true)
                    .role(Role.ROLE_MEMBER)
                    .build());
     
    }
    
//    @Test
//    public void historyInit() {
//
//    	// 2) 저장 전 count
//    	long before = historyRepo.count();
//    	
//    	// 3) 새 History 생성·저장 (ID는 자동 생성되도록)
//    	History h = History.builder()
//    			.query("테스트키워드")
//    			.timestamp(LocalDateTime.now())
//    			.member(m)
//    			.build();
//    	historyRepo.save(h);
//    	
//    	// 4) 저장 후 count가 +1 됐는지 검증
//    	assertThat(historyRepo.count()).isEqualTo(before + 1);
//    }
}
