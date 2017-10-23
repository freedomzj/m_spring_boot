package com.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="crown")
public class Book {
	
	private String key;
	
	private String content;
	
	private String type;
	
	private Date createTime;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
