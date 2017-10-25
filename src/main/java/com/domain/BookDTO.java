package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "book")
public class BookDto {

	@JsonIgnore
	public String _id;
	
	@JsonProperty(value = "Key")
	public String key;

	@JsonProperty(value = "BookType")  
	public String bookType;

	@JsonProperty(value = "Category")  
	public String category;

	@JsonProperty(value = "Index")  
	public Integer index;

	@JsonProperty(value = "Isbn")  
	public String isbn;

	@JsonProperty(value = "Title")  
	public String title;

	@JsonProperty(value = "PublishDate")  
	public String publishDate;

	@JsonProperty(value = "Publisher")  
	public String publisher;

	@JsonProperty(value = "Author")  
	public String author;

	@JsonProperty(value = "Prief")  
	public String prief;
	
	

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrief() {
		return prief;
	}

	public void setPrief(String prief) {
		this.prief = prief;
	}

	
	

	
	
	
}
