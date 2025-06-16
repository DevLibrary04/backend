package edu.pnu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
//id, username, email, password
	private Long id;
	private String username;
	private String password;
	private String token;
}
