/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pict.bean;

import java.util.Date;

/**
 *
 * @author DELL_3542
 */
public class IssueBook
{
    int bookCode ; 
    String studId ; 
    Date issueDate, returnDate;

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public void displayIssueBook()
    {
        System.out.println("Book Code : "+bookCode+"\tStudent Id : "+studId+"\tIssue Date : "+issueDate+"\tReturn Date : "+returnDate);
    }
}
