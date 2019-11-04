package com.pict.bean;

import java.io.Serializable;

public class Student implements Serializable
{
    private int noOfBook;
    private String studentName,password,email,studId;

    public int getNoOfBook() {
        return noOfBook;
    }

    public void setNoOfBook(int noOfBook) {
        this.noOfBook = noOfBook;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }
	
    public void displayStudent()
    {
        System.out.print("\tName: "+this.studentName);
        System.out.print("\tStudent Id : "+this.studId);
        System.out.print("\tPassword: "+this.password);
        System.out.print("\tEmail : "+this.email);
        System.out.println("\tNo. of books : "+this.noOfBook);
    }	
}

