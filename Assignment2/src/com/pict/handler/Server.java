package com.pict.handler;
import java.io.*;
import java.io.Serializable;
import java.net.*;

import com.pict.bean.Book;
public class Server
{
  public static void main(String[] args) throws Exception
  {
      ServerSocket sersock = new ServerSocket(3004);
      System.out.println("Connection established");
      Socket sock = sersock.accept( );                          
                              // reading from keyboard (keyRead object)
      BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
	                      // sending to client (pwrite object)
      OutputStream ostream = sock.getOutputStream(); 
      PrintWriter pwrite = new PrintWriter(ostream, true);

      ObjectInputStream is=new ObjectInputStream(sock.getInputStream());
      ObjectOutputStream os=new ObjectOutputStream(sock.getOutputStream());
                              // receiving from server ( receiveRead  object)
      InputStream istream = sock.getInputStream();
      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
	  LMSHandler l=new LMSHandler();
      String receiveMessage, sendMessage; 
      do 
      {
    	receiveMessage = receiveRead.readLine();	
        if(receiveMessage!= null)  
        {        
        	int x=0;
	        x=Integer.parseInt(receiveMessage);
			switch(x)
			{
			case 1:
				Book b=(Book)is.readObject();
				int bookCode=is.readInt();
				l.addBook(b,bookCode);
				break;
			case 2:
				os.writeObject(l);
				break;
			case 3:
				os.writeObject(l);
				break;
			case 4:
				l.viewAllIssueBooks();
				break;
			case 5:
				System.out.println("Enter the book name : ");
				//String bookName=sc.next();
				//l.checkAvailability(bookName);
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
		}
			             
	    pwrite.flush();    
	   }while(receiveMessage!="9"); 
      sock.close();
    }                    
}                        