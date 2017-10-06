package mypack;
import javax.servlet.*;
import java.util.Random;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
{ 
	try
	{	
		Random randomGenerator = new Random();
		 int userId = randomGenerator.nextInt(10000);
	String name=request.getParameter("UserName");
	String password=request.getParameter("Password");
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	ServletConfig config=getServletConfig();
	ServletContext ctx=config.getServletContext();
	Class.forName(ctx.getInitParameter("driverClass"));
	Connection con=DriverManager.getConnection(ctx.getInitParameter("url"),
	ctx.getInitParameter("username"),
	ctx.getInitParameter("password"));
	PreparedStatement stmt=con.prepareStatement("Insert into QUIZUSER(USERID,USERNAME,PASSWORD) VALUES(?,?,?)");
	stmt.setInt(1,userId);
	stmt.setString(2,name);
	stmt.setString(3,password);
	stmt.executeUpdate();
	out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
	out.println("<center><h1>You have been Successfully Registered</h1><br/>");
	out.println("<h1>Kindly Login to your Account</h1><br/>");
	out.println("<h1><a href=index.html>Login to your account</h1></a></center>");
	out.close();
	}catch(Exception e)
	{
		System.out.println(e);
	}
}
}
