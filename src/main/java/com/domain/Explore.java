package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="explore")
public class Explore {
	
	private String explore;
	
	private String contact;

	public String getExplore() {
		return explore;
	}

	public void setExplore(String explore) {
		this.explore = explore;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	

}
