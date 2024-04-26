package com.ssafy.enjoytrip.domain.like.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeDto {
	private String userId;
	private int contentId;
	
	public LikeDto(String userId, int contentId) {
		this.userId = userId;
		this.contentId = contentId;
	}	
}
