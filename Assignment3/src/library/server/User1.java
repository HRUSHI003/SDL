package library.server;
import java.io.*;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;

import com.pict.bean.Book;
import com.pict.bean.Student;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class User1 
{
    Socket sock;
                              // reading from keyboard (keyRead object)
    BufferedReader keyRead;
                             // sending to client (pwrite object)
    OutputStream ostream; 
    PrintWriter pwrite;

    ObjectOutputStream os;

    ObjectInputStream is;
                             // receiving from server ( receiveRead  object)
    InputStream istream;
    BufferedReader receiveRead;
    
    String studentId = null;
    Student stud = null;
    
    public User1()
    {
        try
        {
            sock = new Socket("127.0.0.1", 3012);
                                  // reading from keyboard (keyRead object)
            keyRead = new BufferedReader(new InputStreamReader(System.in));
                                 // sending to client (pwrite object)
            OutputStream ostream = sock.getOutputStream(); 
            pwrite = new PrintWriter(ostream, true);

            os=new ObjectOutputStream(sock.getOutputStream());

            is=new ObjectInputStream(sock.getInputStream());
                                 // receiving from server ( receiveRead  object)
            istream = sock.getInputStream();
            receiveRead = new BufferedReader(new InputStreamReader(istream));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void adminLogin()
    {
        try
        {
            int flag=0;
            while(flag==0)
            {
                System.out.println("Enter the choice \n\t1.Login \n\t2.Exit");
                int ch=Integer.parseInt(keyRead.readLine().trim());
                switch(ch)
                {
                case 1:
                    System.out.println("Enter the  ID : ");
                    String id=keyRead.readLine().trim();
                    System.out.println("Enter the password : ");
                    String pass=keyRead.readLine().trim();
                    if(id.equals("admin123") && pass.equals("admin321"))
                    {
                      flag=1;       
                    }
                    else
                    {
                        System.out.println("Try again ");
                        continue;
                    }
                    break;
                case 2:
                    os.writeObject("exit");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void studentLogin()
    {
        try
        {
            int flag=0;
            Scanner sc=new Scanner(System.in);
            while(flag==0)
            {
                System.out.println("Enter the choice \n\t1.Login \n\t2.New Registration \n\t3.Exit");
                int ch=Integer.parseInt(keyRead.readLine().trim());
                switch(ch)
                {
                case 1:
                    System.out.println("Enter the student ID : ");
                    studentId=sc.next();
                    System.out.println("Enter the password : ");
                    String pass=sc.next();
                    os.writeObject("viewStudent");
                    os.writeObject("select * from Student where studId='"+studentId+"' and password='"+pass+"'");
                    Object obj=is.readObject();
                    if(obj!=null)
                    {
                        List<Student> list=(ArrayList<Student>) obj;
                        if(list.size()>0)
                        {
                            flag=1;
                            stud=list.get(0);
                            break;
                        }
                        else
                        {
                            System.out.println("Try again ");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Try again ");
                    }
                    break;
                case 2:
                    //Add student"
                    Student s=new Student();
                    sc=new Scanner(System.in);
                    sc=new Scanner(System.in);
                    System.out.println("Enter the student ID : ");
                    s.setStudId(sc.next());
                    System.out.println("Enter the student name : ");
                    s.setStudentName(sc.next());
                    System.out.println("Enter the password : ");
                    s.setPassword(sc.next());
                    System.out.println("Enter the email : ");
                    s.setEmail(sc.next());
                    os.writeObject("update");
                    os.writeObject("insert into Student values('"+s.getStudId()+"','"+s.getStudentName()+"','"+s.getPassword()+"','"+s.getEmail()+"',0);");
                    obj=is.readObject();
                    if(obj!=null)
                    {
                        int status=(int)obj;
                        if(status>0)
                        {
                            System.out.println("Student record inserted");
                        }
                        else
                        {
                            System.out.println("insertion Fail");
                        }
                    }
                    break;
                case 3:
                    os.writeObject("exit");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void issueBook()
    {
        try
        {
            System.out.println("Enter the book code : ");
            Scanner sc=new Scanner(System.in);
            int bookCode=sc.nextInt();
            os.writeObject("viewBook");
            os.writeObject("select * from Books where BookCode='"+bookCode+"' and status='no'");
            Object obj=is.readObject();
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
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void returnBook()
    {
        try
        {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the book code : ");
            int bookCode=sc.nextInt();
            os.writeObject("update");
            os.writeObject("update issueBook set returnDate='"+new Date()+"' whwre bookCode="+bookCode+" and studId='"+studentId+"'");
            Object obj=is.readObject();
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
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void  viewStudentProfile()
    {
        try
        {
            stud.displayStudent();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void addBook()
    {
        try
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
            b.setStatus("no");
            os.writeObject("update");
            os.writeObject("insert into Books values("+bookCode+",'"+b.getBookName()+"','"+b.getAuthorName()+"','"+b.getPublisher()+"',"+b.getPrice()+",'"+b.getStatus()+"')");

            Object obj=is.readObject();
            if(obj!=null)
            {
                int status=(int)obj;
                if(status>0)
                {
                    System.out.println("Book record inserted");
                }
                else
                {
                    System.out.println("Fail Book");
                }
            }
            pwrite.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewAllbBooks()
    {
        try
        {
            os.writeObject("viewBook");
            os.writeObject("select * from books");
            Object obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set s=map.keySet();
                Iterator<Integer> itr=s.iterator();
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
            pwrite.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewAvailableBooks()
    {
        try
        {
            os.writeObject("viewBook");
            os.writeObject("select * from books where status='no'");
            Object obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set s=map.keySet();
                Iterator<Integer> itr=s.iterator();
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
            pwrite.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewIssuedBooks()
    {
        try
        {
            os.writeObject("viewBook");
            os.writeObject("select * from books where status='yes'");
            Object obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set s=map.keySet();
                Iterator<Integer> itr=s.iterator();
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
            pwrite.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void checkAvailabilityOfBook()
    {
        try
        {
            System.out.println("Enter the book code : ");
            Scanner sc=new Scanner(System.in);
            int bookCode=sc.nextInt();
            os.writeObject("viewBook");
            os.writeObject("select * from Books where BookCode='"+bookCode+"'");
            Object obj=is.readObject();
            if(obj!=null)
            {
                Map<Integer,Book> map=(Map<Integer,Book>) obj;
                Set s=map.keySet();
                Iterator<Integer> itr=s.iterator();
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
            pwrite.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void viewStudentList()
    {
        try
        {
            Scanner sc=new Scanner(System.in);
            os.writeObject("viewStudent");
            os.writeObject("select * from Student");
            Object obj=is.readObject();
            if(obj!=null)
            {
                ArrayList<Student> list=(ArrayList<Student>) obj;
                Iterator<Student> itr=list.iterator();
                if(itr.hasNext())
                {
                    Student std=itr.next();
                    std.displayStudent();
                }
            }
            else
            {
                System.out.println("operation fail");
            }
            pwrite.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void logout()
    {
        try
        {   
            os.writeObject("exit");
            sock.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }            
}                        