package com.pict.handler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.pict.bean.*;

public class LMSHandler implements Serializable
{
	ArrayList<BookIssueDetails> bookIssueDetails=new ArrayList<BookIssueDetails>();
	HashMap<Integer,Book> bookList=new HashMap<Integer,Book>();
	HashMap<Integer,Student> studentList=new HashMap<Integer,Student>();
	public void addBook()
	{
		Book b=new Book();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the book code : ");
		int bookCode=sc.nextInt();
		System.out.println("book name : ");
		b.setBookName(sc.next());
		System.out.println("book author : ");
		b.setAuthorName(sc.next());
		System.out.println("book publisher : ");
		b.setPublisher(sc.next());
		System.out.println("book price : ");
		b.setPrice(sc.nextInt());
		bookList.put(bookCode,b);
	}
	public void addBook(Book b,int bookCode)
	{
		
		bookList.put(bookCode,b);
	}
	public void viewAllBooks()
	{
		Book b;
		Set<Integer> keySet=bookList.keySet();
		Iterator<Integer> itr=keySet.iterator();
		while(itr.hasNext()) 
		{
			int id=itr.next();
			b=bookList.get(id);
			System.out.print("\nBook code :"+id);
			b.display();
		}
	}

	public void viewAllIssueBooks()
	{
		Book b;
		Set<Integer> keySet=bookList.keySet();
		Iterator<Integer> itr=keySet.iterator();
		while(itr.hasNext()) 
		{
			int id=itr.next();
			b=bookList.get(id);
			if(b.getStatus()!=0)
			{
				System.out.print("\nBook code :"+id);
				b.display();
				for(BookIssueDetails bid:bookIssueDetails)
				{
					if(bid.getBookCode()==id)
					{
						System.out.print("\tBook issue Date :"+bid.getIssueDate()+"\tBook return Date :"+bid.getReturnDate()+"\tStudent id :"+bid.getStudentId());
					}
				}
			}
		}		
	}
	
	public void viewAllAvailableBook()
	{
		Book b;
		Set<Integer> keySet=bookList.keySet();
		Iterator<Integer> itr=keySet.iterator();
		while(itr.hasNext()) 
		{
			int id=itr.next();
			b=bookList.get(id);
			if(b.getStatus()==0)
			{
				System.out.print("\nBook code :"+id);
				b.display();
			}
		}		
	}
	
	public boolean checkAvailability(String bookName)
	{
		Scanner sc=new Scanner(System.in);
		boolean flag=false;
		Book b;
		Set<Integer> keySet=bookList.keySet();
		Iterator<Integer> itr=keySet.iterator();
		while(itr.hasNext()) 
		{
			int id=itr.next();
			b=bookList.get(id);
			if(b.getStatus()==0 && bookName.equals(b.getBookName()))
			{
				flag=true;
				System.out.print("\nBook code :"+id);
				b.display();
			}
		}	
		return flag;
	}
	
	public void issueBook()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the student ID : ");
		int id=sc.nextInt();
		if(studentList.containsKey(id))
		{
			Student s=studentList.get(id);
			if(s.getNoOfBook()<2)
			{
				System.out.println("Enter the book name : ");
				String bookName=sc.next();
				if(checkAvailability(bookName))
				{
					System.out.println("\nEnter the book code : ");
					int bookCode=sc.nextInt();
					Book b=bookList.get(bookCode);
					s.setNoOfBook(s.getNoOfBook()+1);
					b.setStatus(1);
					BookIssueDetails bid=new BookIssueDetails();
					System.out.println("\nEnter the issue Date : ");
					bid.setIssueDate(sc.next());
					System.out.println("\nEnter the return Date : ");
					bid.setReturnDate(sc.next());
					bid.setBookCode(bookCode);
					bid.setStudentId(id);
					bookIssueDetails.add(bid);
					System.out.println("Book issued successfully!");
				}
				else
				{
					System.out.println("\nBook is not available!");
				}
			}
			else
			{
				System.out.println("\nMaximum limit is reached!");
			}
		}
		else
		{
			System.out.println("\nNot a valid student ID");
		}
		
		
	}
	
	public void viewStudentList()
	{
	    Student s;
		Set<Integer> keySet=studentList.keySet();
		Iterator<Integer> itr=keySet.iterator();
		while(itr.hasNext()) 
		{
			int id=itr.next();
			s=studentList.get(id);
			System.out.print("\nStudent id :"+id);
			s.displayStudent();
		}
	}
	
	public void addStudent()
	{
		Student s=new Student();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the student ID : ");
		int id=sc.nextInt();
		System.out.println("Enter the student name : ");
		s.setStudentName(sc.next());
		System.out.println("Enter the student class name : ");
		s.setClassName(sc.next());
		studentList.put(id,s);
	}
	public static void main(String[] args)
	{
		int x;
		Scanner sc=new Scanner(System.in);
		LMSHandler l=new LMSHandler();
		System.out.println("Welcome to PICT Library");
		do {
			System.out.println("\nChoose the option:");
			System.out.println("1.Add Book");
			System.out.println("2.View All books");
			System.out.println("3.View available Books");
			System.out.println("4.View issued Books");
			System.out.println("5.Check availability of Book");
			System.out.println("6.Add student");
			System.out.println("7.View student List");
			System.out.println("8.Issue a book");
			System.out.println("9.Exit");
			
			System.out.println("Enter the choice");
			x=sc.nextInt();
			
			switch(x)
			{
			case 1:
				l.addBook();
				break;
			case 2:
				l.viewAllBooks();
				break;
			case 3:
				l.viewAllAvailableBook();
				break;
			case 4:
				l.viewAllIssueBooks();
				break;
			case 5:
				System.out.println("Enter the book name : ");
				String bookName=sc.next();
				l.checkAvailability(bookName);
				break;
			case 6:
				l.addStudent();
				break;
			case 7:
				l.viewStudentList();
				break;
			case 8:
				l.issueBook();
			case 9:
				System.exit(0);
			}
			
		}while(x>0  && x<8);
		
	}
}
