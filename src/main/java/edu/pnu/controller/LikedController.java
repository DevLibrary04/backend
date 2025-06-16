package edu.pnu.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.pnu.domain.Liked;
import edu.pnu.service.LikedService;
import edu.pnu.util.JWTUtil;

@RestController
@RequestMapping("/api/liked")
public class LikedController {
    
    @Autowired
    private LikedService likedService;
    
    // POST /api/liked/save
    @PostMapping("/save")
    public String saveLiked(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String title = body.get("title");
        String content = body.get("content");
        
        String userId = JWTUtil.getClaim(token, "userId");
        likedService.saveLiked(userId, title, content);
        
        return "ok";
    }
    
    // POST /api/liked/read  
    @PostMapping("/read")
    public ResponseEntity<List<Liked>> getLiked(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String userId = JWTUtil.getClaim(token, "userID");
        List<Liked> list = likedService.getLikedList(userId);
        
        return ResponseEntity.ok(list);
    }
}