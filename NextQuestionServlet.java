package mypack;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class NextQuestionServlet extends HttpServlet {
public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
{	
	try
{
	
	int userId=Integer.parseInt(request.getParameter("userId"));
	int quesId=Integer.parseInt(request.getParameter("quesId"));
	String userAnswer=request.getParameter("Answer");
	PrintWriter out=response.getWriter();
	response.setContentType("text/html");
	ServletConfig config=getServletConfig();
	ServletContext ctx=config.getServletContext();
	Class.forName(ctx.getInitParameter("driverClass"));
	Connection con=DriverManager.getConnection(ctx.getInitParameter("url"),
	ctx.getInitParameter("username"),
	ctx.getInitParameter("password"));
	PreparedStatement stmt=con.prepareStatement("INSERT INTO USERANSWERTABLE (USERID,QUESTIONID,USERANSWER)VALUES (?,?,?)");
	stmt.setInt(1,userId);
	stmt.setInt(2,quesId);
	stmt.setString(3,userAnswer);
	stmt.executeUpdate();
	quesId++;
	if(quesId==11)
		{  
		out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
		out.println("<center><h1>Your Test has been Successfully Completed</h1></center><br/>");
		out.println("<form action='ResultServlet'method='post'>");
		out.print("<input type='hidden' name='userId' value='"+userId+"'>");
		out.println("<input type='Submit'value='View Your Result'>");
		out.println("</form>");
		out.close();
	}
	else
	{
	PreparedStatement statement=con.prepareStatement("Select QUESTION from QUESTIONBANK where QUESTIONID=?");
	statement.setInt(1,quesId);
	ResultSet resultSet=statement.executeQuery();
	PreparedStatement statements=con.prepareStatement("Select OPTIONS from OPTIONBANKS where QUESTIONID=?");
	statements.setInt(1,quesId);
	ResultSet resultSets=statements.executeQuery();
	if(resultSet.next())
	{
		String Question=resultSet.getString(1);
		out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
		out.println("<center><h3>Current Question No. Is "+quesId+" /10"+"</h4></center>");
		out.println("<h3>Q"+quesId+")"+"<br/>");
		out.println("<h4>"+Question+"</h4>");
		while(resultSets.next())
		{
out.println("<h4>"+resultSets.getString(1)+"</h4><br/>");
		}
		out.println("<form action='NextQuestionServlet' method='post'>");
		out.println("<input type='text'name='Answer'><br/>");
		out.print("<input type='hidden' name='quesId' value='"+quesId+"'>");
		out.print("<input type='hidden' name='userId' value='"+userId+"'>");  
		out.println("<input type='Submit'value='Submit your answer'><br/>");
		out.println("</form>");
		out.close();

//			out.println("<form action='NextQuestionServlet' method='post'>");
//			out.println("<input type='radio'name=Answer value='"+resultSets.getString(1)+"'>");
//				//out.println("<h4>"+rsets.getString(1)+"</h4><br/>");
//			out.println("<input type='text'name='Answer'><br/>");
//			out.print("<input type='hidden' name='quesId' value='"+quesId+"'>");
//			out.print("<input type='hidden' name='userId' value='"+userId+"'>");
//			out.println("<input type='Submit' value='Submit'");
//			out.println("</form>");
//			out.close();
		
	}
	
	}	
	
	
	
	
	
	
	
	
	
	
}catch(Exception e)
{
	System.out.println(e);
}
}
}
