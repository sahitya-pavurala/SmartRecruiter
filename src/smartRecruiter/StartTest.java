package smartRecruiter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StartTest extends HttpServlet {

	private static final long serialVersionUID = 7825233003593842035L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String company = request.getParameter("company");
		String platform = request.getParameter("platform");
		if(company != null && !company.isEmpty() && !company.equals("none") && platform != null && !platform.isEmpty() && !platform.equals("none")){
			HttpSession session=request.getSession(true);
			Connection con=null;
			try {
				con=DBcon.connect();
				PreparedStatement pst=con.prepareStatement("select * from questions order by rand() limit 10");
				ResultSet rs=pst.executeQuery();
				List<String> questions = new ArrayList<String>();
				while(rs.next()){
					questions.add(rs.getString(2));
				}
				request.setAttribute("company", company);
				request.setAttribute("platform", platform);
				session.setAttribute("questions", questions);
				RequestDispatcher rd=request.getRequestDispatcher("questions.jsp");
				rd.forward(request,response);
			}catch (ClassNotFoundException e) {
				request.setAttribute("message", "Server connection failed..please try again");
				RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
				rd.forward(request,response);
			}catch (SQLException e) {
				request.setAttribute("message", "Problem loading video list");
				RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
				rd.forward(request,response);
			}finally{
				try {
					con.close();
				} 
				catch (SQLException e) {
					request.setAttribute("message", "Server busy..please try again later");
					RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
					rd.forward(request,response);
				}
				catch(NullPointerException e) {
					e.printStackTrace();
					request.setAttribute("message", "Server not connected..please try again later");
					RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
					rd.forward(request,response);
				}
		    }
		}else{
			request.setAttribute("message", "Undefined company or platform");
			RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
			rd.forward(request,response);
		}
	}

}
