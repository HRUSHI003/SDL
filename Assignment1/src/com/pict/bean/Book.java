package com.pict.bean;

import java.util.Date;

public class Book 
{
	private String bookName,authorName,publisher; 
	private int price,status;
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public void display() 
	{
		System.out.print("\nbook name : "+bookName+"\tbook author : "+authorName+"\tbook publisher : "+publisher+"\tbook price : "+price);
		if(status==0)
		{
			System.out.print("\tStatus : Book available");
		}
		else
		{
			System.out.print("\tStatus : Book Issued ");
		}
	}
}
