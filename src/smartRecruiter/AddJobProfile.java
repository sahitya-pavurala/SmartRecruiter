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

public class AddJobProfile extends HttpServlet {

	private static final long serialVersionUID = 6752791800344855468L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String company = request.getParameter("company");
		String profile = request.getParameter("profile");
		Connection con=null;
		try {
			con=DBcon.connect();
			PreparedStatement pst=con.prepareStatement("insert into company(company_name,job_profile) values(?,?)");
			pst.setString(1, company);
			pst.setString(2, profile);
			if(pst.executeUpdate() > 0){
				request.setAttribute("message", "Job Profile added Successfully");
			}else{
				request.setAttribute("message", "Error occured while adding..... Please try again!!!!");
			}
			HttpSession session=request.getSession(true);
			PreparedStatement pst1=con.prepareStatement("select * from company");
			ResultSet rs1=pst1.executeQuery();
			List<Company> companies = new ArrayList<Company>();
			while(rs1.next()){
				Company com = new Company();
				com.setId(rs1.getInt(1));
				com.setName(rs1.getString(2));
				com.setJobProfile(rs1.getString(3));
				companies.add(com);
			}
			session.setAttribute("companies", companies);
			RequestDispatcher rd=request.getRequestDispatcher("adminHome.jsp");
			rd.forward(request,response);
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", "Server connection failed..please try again");
			RequestDispatcher rd=request.getRequestDispatcher("adminLogin.jsp");
			rd.forward(request,response);
		}catch (SQLException e) {
			request.setAttribute("message", "Invalid UserName");
			RequestDispatcher rd=request.getRequestDispatcher("adminLogin.jsp");
			rd.forward(request,response);
		}finally{
			try {
				con.close();
			} 
			catch (SQLException e) {
				request.setAttribute("message", "Server busy..please try again later");
				RequestDispatcher rd=request.getRequestDispatcher("adminHome.jsp");
				rd.forward(request,response);
			}
			catch(NullPointerException e) {
				request.setAttribute("message", "Server not connected..please try again later");
				RequestDispatcher rd=request.getRequestDispatcher("adminHome.jsp");
				rd.forward(request,response);
			}
	    }
	}
}
