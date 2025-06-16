package edu.pnu.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class ResultDTO {
	private String title;
	private String originallink;
	private String description;
	private String link;
	private String pubDate;
}
