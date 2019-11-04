package library.server;
import java.io.*;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;

import com.pict.bean.Book;
import com.pict.bean.Student;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class AdminUser 
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
    public AdminUser()
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
    public void Login()
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
    
  public  static void main(String[] args) throws Exception
  {
    System.out.println("Start the chitchat, type and press Enter key");
    int x;
    
    
    do
    {
        System.out.println("\nChoose the option:");
        System.out.println("1.Add Book");
        System.out.println("2.View All books");
        System.out.println("3.View available Books");
        System.out.println("4.View issued Books");
        System.out.println("5.Check availability of Book");
        System.out.println("6.View student List");
        System.out.println("7.Exit");
        System.out.println("Enter the choice");
        Scanner sc=new Scanner(System.in);
        x=sc.nextInt();
    //pwrite.println(x);       // sending to server
        switch(x)
        {
        case 1:
            //1.Add Book;
            
            break;
        case 2:
            //2.View All books
            
            break;
        case 3:    
        //3.View available Books"
            
            break;
        case 4:
        //4.View issued Books"
            
            break;
        case 5:
        //5.Check availability of Book"
               
            break;
        case 6:
        //7.View student List"
               
            break;
        case 7:
        //9.Exit
            break;
        default:
            System.out.println("Wrong choice");
        }
                            // flush the data
                 
      }while(x!=7);
     
    }                    
}                        