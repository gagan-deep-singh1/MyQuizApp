package mypack;
import javax.servlet.*;
import dao.ResultDao;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import dao.ResultDao;
public class ResultServlet extends HttpServlet {
public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
{
	String uName="null";
	try{
		
	int userId=Integer.parseInt(request.getParameter("userId"));
	int score=ResultDao.resultFinder(userId);
	PrintWriter out=response.getWriter();
	response.setContentType("text/html");
	ServletConfig config=getServletConfig();
	ServletContext ctx=config.getServletContext();
	Class.forName(ctx.getInitParameter("driverClass"));
	Connection con=DriverManager.getConnection(ctx.getInitParameter("url"),
	ctx.getInitParameter("username"),
	ctx.getInitParameter("password"));
	PreparedStatement stmt=con.prepareStatement("Select USERNAME from QUIZUSER where USERID=?");
	stmt.setInt(1,userId);
	ResultSet rset=stmt.executeQuery();
	while(rset.next())
	{
		uName=rset.getString("USERNAME");
		
	}
	PreparedStatement stmts=con.prepareStatement("INSERT INTO RANKTABLE(USERID,USERNAME,SCORE) values(?,?,?)");
	stmts.setInt(1,userId);
	stmts.setString(2,uName);
	stmts.setInt(3,score);
	stmts.executeUpdate();
	int rank=ResultDao.rankFinder(score);
	out.println("<br/>");
	out.println("<link rel='stylesheet' type='text/css' href='contact.css'>");
	out.println("<center><h1>Your scorecard is:</h1></center>");
	out.println("<center><h2>Name="+uName+"</h2>");
	out.println("<h2>Your score="+score+"/40</h2></center>");
	out.println("<center><h2>Your Rank="+rank+"</h2></center>");
	out.println("<a href=index.html>LogOut</a>");
	out.close(); 
	}catch(Exception e)
	{
		System.out.println(e);
	}
}

}
