package com.pict.handler;
import java.io.*;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;

import com.pict.bean.Book;
public class AdminUser 
{
  public static void main(String[] args) throws Exception
  {
     Socket sock = new Socket("127.0.0.1", 3004);
                               // reading from keyboard (keyRead object)
     BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                              // sending to client (pwrite object)
     OutputStream ostream = sock.getOutputStream(); 
     PrintWriter pwrite = new PrintWriter(ostream, true);
     
     ObjectOutputStream os=new ObjectOutputStream(sock.getOutputStream());

     ObjectInputStream is=new ObjectInputStream(sock.getInputStream());
                              // receiving from server ( receiveRead  object)
     InputStream istream = sock.getInputStream();
     BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

     System.out.println("Start the chitchat, type and press Enter key");
     int x;
     String receiveMessage;               
     do
     {
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
		x=Integer.parseInt(keyRead.readLine().trim());
        pwrite.println(x);       // sending to server
        switch(x)
        {
        case 1:
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
    		os.writeObject(b);
    		os.writeInt(bookCode);
    		break;
        case 2:
        	LMSHandler l=(LMSHandler)is.readObject();
        	l.viewAllBooks();
        	break;
        case 3:
        	l=(LMSHandler)is.readObject();
        	l.viewAllAvailableBook();	
        	break;
        }
        pwrite.flush();                    // flush the data
        if((receiveMessage = receiveRead.readLine()) != null) //receive from server
        {
            System.out.println(receiveMessage); // displaying at DOS prompt
        }         
      }while(x!=9);
      sock.close();
    }                    
}                        