package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.History;
import edu.pnu.domain.Member;
import edu.pnu.domain.Result;
import edu.pnu.dto.HistoryDTO;
import edu.pnu.dto.ResultDTO;
import edu.pnu.persistence.HistoryRepository;
import jakarta.transaction.Transactional;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
    	this.historyRepository = historyRepository;
    }
    
    @Transactional
    public void saveHistoryWithResults(HistoryDTO h, Member m) {
    	
    	History history = new History();
    	history.setQuery(h.getQuery());
    	
    	// timestamp 내용
    	
    	List<Result> results = new ArrayList<>();
    	
    	
    	List<ResultDTO> resultDTO = h.getResults();
    	
    	if(resultDTO != null) {
    		for (ResultDTO r : resultDTO) {
    			Result result = new Result();
    			result.setTitle(r.getTitle());
    			result.setOriginallink(r.getOriginallink());
    			result.setDescription(r.getDescription());
    			result.setLink(r.getLink());
    			result.setPubDate(r.getPubDate());
    			
    			results.add(result);
    		}
    	}
    	
    	
    	history.setResults(results);
    	
    	historyRepository.save(history);
    }
}
