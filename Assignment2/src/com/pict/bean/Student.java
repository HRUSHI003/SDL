package com.pict.bean;

public class Student 
{
	private int noOfBook;
	private String studentName,className;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getNoOfBook() {
		return noOfBook;
	}
	public void setNoOfBook(int noOfBook) {
		this.noOfBook = noOfBook;
	}
	
	public void displayStudent()
	{
		System.out.print("\tName: "+studentName);
		System.out.print("\tClass Name : "+className);
		System.out.print("\tNo of book taken : "+noOfBook);
	}
	
}

