package library.server;
import com.pict.bean.Book;
import com.pict.bean.IssueBook;
import com.pict.bean.Student;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    Socket s;
    ObjectInputStream is;
    ObjectOutputStream os;
    Server(Socket s,ObjectInputStream is,ObjectOutputStream os)
    {
        this.s=s;
        this.os=os;
        this.is=is;
    }
    public void run()
    {
        try
        {
            String msg;
            do
            {
                msg=(String) is.readObject();
                String query=null;
                Object obj=null;
                switch(msg)
                {
                    case "viewBook":
                        query=(String) is.readObject();
                        obj=processSelectBook(query);
                        break;
                    case "viewStudent":
                        query=(String) is.readObject();
                        obj=processSelectStudent(query);
                        break;
                    case  "update":
                        query=(String) is.readObject();
                        obj=processUpdate(query);
                }
                os.writeObject(obj);
            }while(!msg.equals("exit"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    Statement getStatementDB()
    {
        Connection con=null;
        Statement stmt=null;
        try
        {  
            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/lms","root","root");  
            stmt=con.createStatement();  
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
        return stmt;
    }
    Object processSelectBook(String query)
    {
        ResultSet rs=null; 
        Map<Integer,Book> map=new LinkedHashMap<Integer,Book>();
        try
        {  
            Statement stmt=getStatementDB();
            rs=stmt.executeQuery(query);
            while(rs.next())
            {
                Book b=new Book();
                int bc=rs.getInt(1);
                b.setBookName(rs.getString(2));
                b.setAuthorName(rs.getString(3));
                b.setPublisher(rs.getString(4));
                b.setPrice(rs.getInt(5));
                b.setStatus(rs.getString(6));
                map.put(bc, b);
                //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"   "+rs.getString(3)+"   "+rs.getString(4)+"   "+rs.getInt(5)+"   "+rs.getString(6));
            }
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
        return map;
    }

    Object processSelectStudent(String query)
    {
        ResultSet rs=null; 
        List<Student> list=new ArrayList<Student>();
        try
        {  
            Statement stmt=getStatementDB();  
            rs=stmt.executeQuery(query);
            while(rs.next())
            {
                Student b=new Student();
                b.setStudentName(rs.getString(1));
                b.setStudId(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setEmail(rs.getString(4));
                b.setNoOfBook(rs.getInt(5));
                list.add(b);
            }
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
        return list;
    }
    
    Object processSelectIssueBook(String query)
    {
        ResultSet rs=null; 
        List<IssueBook> list=new ArrayList<IssueBook>();
        try
        {  
            Statement stmt=getStatementDB();  
            rs=stmt.executeQuery(query);
            while(rs.next())
            {
                IssueBook b=new IssueBook();
                b.setBookCode(rs.getInt(1));
                b.setStudId(rs.getString(2));
                b.setIssueDate(rs.getDate(3));
                b.setReturnDate(rs.getDate(4));
                list.add(b);
            }
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
        return list;
    }
    
    Object processUpdate(String query)
    {
        Object rs=null; 
        try
        {    
            Statement stmt=getStatementDB();  
            rs=stmt.executeUpdate(query);
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
        return rs;
    }
    
  
    public static void main(String[] args) throws Exception
    {
        ExecutorService executor = Executors.newFixedThreadPool(10);//creating a pool of 5 threads
        ServerSocket sersock = null;
        try
        {
            sersock = new ServerSocket(3012);
            while (true)  
            { 
                Socket s = null; 
                try 
                { 
                    // socket object to receive incoming client requests 
                    s = sersock.accept(); 

                    System.out.println("A new client is connected : " + s); 

                    // obtaining input and out streams 
                    ObjectOutputStream os=new ObjectOutputStream(s.getOutputStream());
                    ObjectInputStream is=new ObjectInputStream(s.getInputStream());
                    System.out.println("Assigning new thread for this client"); 

                    // create a new thread object
                    Runnable worker = new Server(s,is,os);
                    executor.execute(worker); 

                    // Invoking the start() method

                } 
                catch (Exception e)
                { 
                    s.close(); 
                    executor.shutdown();  
                    e.printStackTrace(); 
                } 
            }
        }
        catch(Exception e)
        {
            sersock.close();
            e.printStackTrace();
        }
        finally
        {
            sersock.close();
            executor.shutdown();  
            while (!executor.isTerminated()) {   } 
        }
    }                    
}                        