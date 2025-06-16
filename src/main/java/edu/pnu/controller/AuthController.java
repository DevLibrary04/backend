package edu.pnu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.dto.MemberDto;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.MemberDetailsService;
import edu.pnu.util.JWTUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private MemberDetailsService memberDetailsService;
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        
        
        Member member = memberRepo.findByUsername(username).orElse(null);

        // 사용자 존재 여부 및 비밀번호 일치 여부 확인
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            return ResponseEntity.status(401).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시, JWT 토큰 발급
        String token = JWTUtil.getJWT(member.getUsername());

        // 응답: React에서 localStorage에 저장할 데이터
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", member.getUsername()
        ));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDto memberDto) {
    	try {
    		if (memberRepo.findByUsername(memberDto.getUsername()).isPresent()) {
    			return ResponseEntity.status(409).body("이미 존재하는 사용자입니다.");
    		}
    		
    		Member member = Member.builder()
    				.username(memberDto.getUsername())
    				.password(passwordEncoder.encode(memberDto.getPassword()))
    				.build();
    		
    		Member savedMember = memberRepo.save(member);
    		
    		MemberDto responseMemberDto = MemberDto.builder()
    				.id(savedMember.getId())
    				.username(savedMember.getUsername())
    				.build();
    		
    		return ResponseEntity.ok(responseMemberDto);
    		
    		
    		
    	} catch(Exception e) {
    		return ResponseEntity.status(500).body("회원가입 중 오류가 발생했습니다.");
    	}
    }
}
