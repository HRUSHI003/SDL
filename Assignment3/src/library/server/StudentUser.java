package library.server;
import java.io.*;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;

import com.pict.bean.Book;
import com.pict.bean.Student;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class StudentUser 
{
    
  public static void main(String[] args) throws Exception
  {
    Socket sock = new Socket("127.0.0.1", 3012);
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
    Scanner  sc=new Scanner(System.in);
    Object obj;
    String studentId = null;
    Student stud = null;
    
    do
    {
        //Student Application    
        System.out.println("\nChoose the option:");
        System.out.println("1.View All books");
        System.out.println("2.View available Books");
        System.out.println("3.View issued Books");
        System.out.println("4.Check availability of Book");
        System.out.println("5.Issue a book");
        System.out.println("6.Return a book");
        System.out.println("7.view student profile");
        System.out.println("8.Exit");
        System.out.println("Enter the choice");
        x=Integer.parseInt(keyRead.readLine().trim());
    //pwrite.println(x);       // sending to server
        switch(x)
        {
        case 1:
            //View All books
            os.writeObject("viewBook");
            os.writeObject("select * from books");
            obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set set=map.keySet();
                Iterator<Integer> itr=set.iterator();
                while(itr.hasNext())
                {
                    int i=itr.next();
                    System.out.print(i+"  ");
                    Book book=map.get(i);
                    book.display();
                }
            }
            else
            {
                System.out.println("not Available");
            }
            break;
        case 2:    
        //View available Books"
            os.writeObject("viewBook");
            os.writeObject("select * from books where status='no'");
            obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set set=map.keySet();
                Iterator<Integer> itr=set.iterator();
                while(itr.hasNext())
                {
                    int i=itr.next();
                    System.out.print(i+"  ");
                    Book book=map.get(i);
                    book.display();
                }
            }
            else
            {
                System.out.println("not Available");
            }
            break;
        case 3:
        //View issued Books"
            os.writeObject("viewBook");
            os.writeObject("select * from books where status='yes'");
            obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set set=map.keySet();
                Iterator<Integer> itr=set.iterator();
                while(itr.hasNext())
                {
                    int i=itr.next();
                    System.out.print(i+"  ");
                    Book book=map.get(i);
                    book.display();
                }
            }
            else
            {
                System.out.println("not Available");
            }
            break;
        case 4:
        //Check availability of Book"
            System.out.println("Enter the book code : ");
            sc=new Scanner(System.in);
            int bookCode=sc.nextInt();
            os.writeObject("viewBook");
            os.writeObject("select * from Books where BookCode='"+bookCode+"'");
            obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set set=map.keySet();
                Iterator<Integer> itr=set.iterator();
                while(itr.hasNext())
                {
                    int i=itr.next();
                    System.out.print(i+"  ");
                    Book book=map.get(i);
                    book.display();
                }
            }
            else
            {
                System.out.println("not Available");
            }   
            break;
        case 5:
        //Issue a book"
            System.out.println("Enter the book code : ");
            sc=new Scanner(System.in);
            bookCode=sc.nextInt();
            os.writeObject("viewBook");
            os.writeObject("select * from Books where BookCode='"+bookCode+"' and status='no'");
            obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set set=map.keySet();
                Iterator<Integer> itr=set.iterator();
                if(map.isEmpty())
                {
                    System.out.println("book not Available");
                }
                if(itr.hasNext())
                {
                    if(stud.getNoOfBook()<3)
                    {
                        int i=itr.next();
                        Date d=new Date();
                        os.writeObject("update");
                        os.writeObject("insert into issueBook( bookCode, studId, issueDate date) values('"+bookCode+"','"+studentId+"','"+d+"');");
                        obj=is.readObject();
                        
                        stud.setNoOfBook(stud.getNoOfBook()+1);
                        os.writeObject("update");
                        os.writeObject("update student set noOfBooks="+(stud.getNoOfBook())+" where studId='"+stud.getStudId()+"'");
                        obj=is.readObject();
                        
                        os.writeObject("update");
                        os.writeObject("update Book set status='yes' where bookCode='"+bookCode+"'");
                        obj=is.readObject();
                        
                        if(obj!=null)
                        {
                            int status=(int)obj;
                            if(status>0)
                            {
                                System.out.println("Book issued");
                            }
                            else
                            {
                                System.out.println("operatin fail");
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Student cross limitation");
                    }
                }
                else
                {
                    System.out.println("book not Available");
                }
            }
            else
            {
                System.out.println("Operation fail");
            }
            break;
        case 6:
        //Return Book
            sc=new Scanner(System.in);
            System.out.println("Enter the book code : ");
            bookCode=sc.nextInt();
            os.writeObject("update");
            os.writeObject("update issueBook set returnDate='"+new Date()+"' whwre bookCode="+bookCode+" and studId='"+studentId+"'");
            obj=is.readObject();
            if(obj!=null)
            {
                int status=(int)obj;
                if(status>0)
                {
                    stud.setNoOfBook(stud.getNoOfBook()-1);
                    os.writeObject("update");
                    os.writeObject("update student set noOfBooks="+(stud.getNoOfBook())+" where studId='"+studentId+"'");
                    obj=is.readObject();

                    os.writeObject("update");
                    os.writeObject("update Book set status='no' where bookCode='"+bookCode+"'");
                    obj=is.readObject();           
                }
                {
                    System.out.println("book returned");
                }
            }
            else
            {
                System.out.println("Operation fail");
            }
            break;
        case 7:
        //view student profile
            stud.displayStudent();
            break;
        case 8:    
        //Exit
            os.writeObject("exit");
            break;
        default:
            System.out.println("Wrong choice");
        }
        pwrite.flush();                    // flush the data
                 
      }while(x!=8);
      sock.close();
    }                    
}                        