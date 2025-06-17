package edu.pnu.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class HistoryDTO {
	private String userId;
	private String query;
	private List<ResultDTO> results = new ArrayList<>();
	private String token; 		// JWT 토큰 필드 추가
	private String username;	// username 필드 추가
	
}
