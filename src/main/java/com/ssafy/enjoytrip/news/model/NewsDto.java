package com.ssafy.enjoytrip.news.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDto {
	private String title;
	private String originallink;
	private String link;
	private String description;
	private String pubDate;
	
	public NewsDto() {
		
	}
	
	public NewsDto(String title, String originallink, String link, String description, String pubDate) {
		super();
		this.title = title;
		this.originallink = originallink;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "NewsDto [title=" + title + ", originallink=" + originallink + ", link=" + link + ", description="
				+ description + ", pubDate=" + pubDate + "]";
	}
	
	
}
