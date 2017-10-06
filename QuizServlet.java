package mypack;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class QuizServlet extends HttpServlet {
public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
{	try
{
	int userId=Integer.parseInt(request.getParameter("userId"));
	int quesId=1;
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	ServletConfig config=getServletConfig();
	ServletContext ctx=config.getServletContext();
	Class.forName(ctx.getInitParameter("driverClass"));
	Connection con=DriverManager.getConnection(ctx.getInitParameter("url"),
	ctx.getInitParameter("username"),
	ctx.getInitParameter("password"));
	PreparedStatement stmt=con.prepareStatement("Select QUESTION from QUESTIONBANK where QUESTIONID=?");
	stmt.setInt(1,quesId);
	ResultSet rset=stmt.executeQuery();
	PreparedStatement stmts=con.prepareStatement("Select OPTIONS from OPTIONBANKS where QUESTIONID=?");
	stmts.setInt(1,quesId);
	ResultSet rsets=stmts.executeQuery();
	if(rset.next())
	{
		String Question=rset.getString(1);
		out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
		out.println("<center><h3>Current Question No. Is "+quesId+" /10"+"</h4></center>");
		out.println("<h3>Q"+quesId+")"+"<br/>");
		out.println("<h4>"+Question+"</h4>");
		while(rsets.next())
		{
	out.println("<h4>"+rsets.getString(1)+"</h4><br/>");
			}
		
			out.println("<form action='NextQuestionServlet' method='post'>");
			out.println("<input type='text'name='Answer'><br/>");
			out.print("<input type='hidden' name='quesId' value='"+quesId+"'>");
			out.print("<input type='hidden' name='userId' value='"+userId+"'>");  
			out.println("<input type='Submit'value='Submit your answer'><br/>");
			out.println("</form>");
			out.close();
		//out.println("<form action='NextQuestionServlet' method='post'>");
		//out.println("<input type='text'name='Answer'><br/>");
		//out.print("<input type='hidden' name='quesId' value='"+quesId+"'>");
		//out.print("<input type='hidden' name='userId' value='"+userId+"'>");  
		//out.println("<input type='Submit'value='Submit your answer'><br/>");
		//out.println("</form>");
		//out.close();
	}
}catch(Exception e)
{
	System.out.println(e);
}
}
}
