package edu.pnu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.History;
import edu.pnu.service.HistoryService;
import edu.pnu.util.JWTUtil;
import edu.pnu.dto.HistoryDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@ToString
//class HistoryDTO {
//	private String userId;
//	private String query;
//	private List<ResultDTO> results = new ArrayList<>();
//}
//
//@Getter
//@Setter
//@ToString
//class ResultDTO {
//	private String title;
//	private String originallink;
//	private String description;
//	private String link;
//	private String pubDate;
//}

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // POST /api/history/save
    // 1. 사용자의 검색 키워드를 저장
    @PostMapping
    public ResponseEntity<String> saveSearchKeyword(@RequestBody HistoryDTO h) {
    	System.out.println("history:" + h);

    	try {
    		String token = h.getToken();
    		String username = JWTUtil.getClaim(token, "username");
    		historyService.recordSearch(username, h);	// username, query를 db 테이블에 저장
    		return ResponseEntity.ok("검색 기록이 저장되었습니다.");
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    							.body("저장 실패: " + e.getMessage());
    	}
    }
    
    // GET /api/history?userId=...
    // 2. 쿼리 파라미터로 userId를 받아서 히스토리 조회 (History 엔티티 그대로 반환 // dto 사용 불필요)
    @GetMapping
    public ResponseEntity<List<History>> getHistoryByUserId(@RequestParam String userId) {
        
        try {
            List<History> list = historyService.getHistory(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
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
