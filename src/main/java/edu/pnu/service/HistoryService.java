package edu.pnu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.History;
import edu.pnu.domain.Member;
import edu.pnu.domain.Result;
import edu.pnu.dto.HistoryDTO;
import edu.pnu.persistence.HistoryRepository;
import edu.pnu.persistence.MemberRepository;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepo;
    

    @Autowired
    private MemberRepository memberRepo;

    public void recordSearch(String username, HistoryDTO h) {
        Member member = memberRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + username));

        
        History history = History.builder()
        	    .member(member)
        	    .query(h.getQuery())
        	    .build();

        	List<Result> resultEntities = h.getResults().stream()
        	    .map(dto -> {
        	        Result r = new Result();
        	        r.setTitle(dto.getTitle());
        	        r.setOriginallink(dto.getOriginallink());
        	        r.setDescription(dto.getDescription());
        	        r.setLink(dto.getLink());
        	        r.setPubDate(dto.getPubDate());
        	        r.setHistory(history);
        	        return r;
        	    }).collect(Collectors.toList());

        	history.setResults(resultEntities);
        	historyRepo.save(history);
        
        
        
        
        //리스트에 있는 dto 다 db에 올리기
        
//        History history = History.builder()
//                .member(member)
//                .query(h.getQuery())
//                .results(h.getResults())
//                .build();
//
//        historyRepo.save(history);
    }
    
    public List<History> getHistory(String username) {
    	return historyRepo.findByMemberUsername(username);
    }
}
