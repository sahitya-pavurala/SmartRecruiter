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


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		Connection con=null;
		try {
			con=DBcon.connect();
			PreparedStatement pst=con.prepareStatement("select * from user where username=?");
			pst.setString(1,username);
			ResultSet rs=pst.executeQuery();
			rs.next();
			String storedPass=rs.getString("password");
			if(password.equals(storedPass)){
				UserBean user = new UserBean();
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				HttpSession session=request.getSession(true);
		    	session.setAttribute("user", user);
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
				pst1 = con.prepareStatement("select * from platform");
				rs1 = pst1.executeQuery();
				List<Platform> platForms = new ArrayList<Platform>();
				while(rs1.next()){
					Platform pf = new Platform();
					pf.setId(rs1.getInt(1));
					pf.setPlatForm(rs1.getString(2));
					platForms.add(pf);
				}
				session.setAttribute("platforms", platForms);
		    	RequestDispatcher rd=request.getRequestDispatcher("adminHome.jsp");
				rd.forward(request,response);
			}else{
				request.setAttribute("message", "Invalid Password");
				RequestDispatcher rd=request.getRequestDispatcher("adminLogin.jsp");
				rd.forward(request,response);
			}
			
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
				RequestDispatcher rd=request.getRequestDispatcher("adminLogin.jsp");
				rd.forward(request,response);
			}
			catch(NullPointerException e) {
				e.printStackTrace();
				request.setAttribute("message", "Server not connected..please try again later");
				RequestDispatcher rd=request.getRequestDispatcher("adminLogin.jsp");
				rd.forward(request,response);
			}
	    }
	}
}


