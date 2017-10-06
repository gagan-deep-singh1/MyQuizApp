package mypack;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class LoginServlet extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
{
	try{
	String m=request.getParameter("UserName");
	String p=request.getParameter("Password");
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	ServletConfig config=getServletConfig();
	ServletContext ctx=config.getServletContext();
	Class.forName(ctx.getInitParameter("driverClass"));
	Connection con=DriverManager.getConnection(ctx.getInitParameter("url"),
	ctx.getInitParameter("username"),
	ctx.getInitParameter("password"));
	PreparedStatement stmt=con.prepareStatement("Select* from QUIZUSER where USERNAME=? and PASSWORD=?");
	PreparedStatement stmts=con.prepareStatement("Select * from USERANSWERTABLE where USERID=?");
	stmt.setString(1,m);
	stmt.setString(2,p);
	ResultSet rset=stmt.executeQuery();
	if(rset.next())
	{	int userId=rset.getInt(1);
		stmts.setInt(1,userId);
		ResultSet rsets=stmts.executeQuery();
		if(rsets.next())
		{
			out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
			out.println("<h2><center>You have already taken the TEST</center></h2>");
		}
		else
		{
		out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
		out.println("<h1><center>Welcome,"+rset.getString(2)+"</center></h1>");
		out.println("<h2>INSTRUCTIONS</h2>");
		out.println("<h3>Click on Submit Button If you have read all the instructions</h3><br/>");
		out.println("<h4>1.This Test contains 10 Questions which are multiple type questions.<br/>");
		out.println("2.Each Question has 4 choices A,B,C,D.Out of which only one choice is correct.<br/>");
		out.println("3.You Have to type the Answer in the textfield that will appear below each question.<br/>");
		out.println("4.You have to type the Anser in <b>CAPITAL LETTERS ONLY</b>.Other wise all the other answers will be marked wwrong.<br/>");
		out.println("5.You can only view the next question if you have answered the previous one.<br/>");
		out.println("<a href=\"QuizServlet?userId="+userId+"\"><h3>Submit</h3></a>");
	}
	}
	else
	{
		out.println("<h3>invalid MailId or Password<h3><br/>");
	RequestDispatcher rd=request.getRequestDispatcher("index.html");
	rd.include(request,response);
	}
	}catch(Exception e)
	{
		System.out.println(e);
	}
}
}