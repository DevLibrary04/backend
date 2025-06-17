package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.History;
import edu.pnu.domain.Member;
import edu.pnu.dto.HistoryDTO;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.HistoryService;
import edu.pnu.util.JWTUtil;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MemberRepository memberRepository;
    // POST /api/history/
    // 1. 사용자의 검색 키워드를 저장
    
    // HistoryDTO의 token에서 사용자 아이디를 추출하고(getclaims), 서비스 계층의 saveHistoryWithResults로 검색 기록을 result 테이블에 저장합니다.
    // 성공 시 200 OK, 실패 시 500 Internal Server Error를 반환합니다.
    @PostMapping
    public ResponseEntity<String> saveSearchKeyword(@RequestHeader("Authorization")String token, @RequestBody HistoryDTO h) {
    	System.out.println("history:" + h);

    	try {
    		String username = JWTUtil.getClaim(token, "username");
    		
    		Member member = memberRepository.findByUsername(username)
    				.orElseThrow(()-> new RuntimeException("유저 안뜸"));
    		
    		historyService.saveHistoryWithResults(h, member);
    		
    		return ResponseEntity.ok("검색 기록이 저장되었습니다.");
    		
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    							.body("저장 실패: " + e.getMessage());
    	}
    }
    
    // GET /api/history?userId=...
    // 2. 쿼리 파라미터로 userId를 받아서 히스토리 조회 (History 엔티티 그대로 반환 // dto 사용 불필요)
//    @GetMapping
//    public ResponseEntity<List<History>> getHistoryByUserId(@RequestParam String userId) {
//        
//        try {
//            List<History> list = historyService.getHistory(userId);
//            return ResponseEntity.ok(list);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
    
    // POST /api/history/read
    // 3. 토큰 기반 조회
//    @PostMapping("/read")
//    public ResponseEntity<List<History>> getHistory(@RequestBody Map<String, String> body) {
//        String token = body.get("token");
//        String userId = JWTUtil.getClaim(token);
//        List<History> list = historyService.getHistory(userId);
//        return ResponseEntity.ok(list);
//    }
}
