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

import org.scribe.model.Token;


public class VerifyToken extends HttpServlet {
	
	private static final long serialVersionUID = -8060838828981704297L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("verifier");
		Linkedin lin = new Linkedin();
		Token accessToken = lin.verifyToken(token, request);
		if(accessToken != null){
			HttpSession session=request.getSession(true);
			session.setAttribute("token", accessToken);
			
			Connection con=null;
			try {
				con=DBcon.connect();
				PreparedStatement pst=con.prepareStatement("select * from company");
				ResultSet rs=pst.executeQuery();
				List<Company> companies = new ArrayList<Company>();
				while(rs.next()){
					Company com = new Company();
					com.setId(rs.getInt(1));
					com.setName(rs.getString(2));
					com.setJobProfile(rs.getString(3));
					companies.add(com);
				}
				session.setAttribute("companies", companies);
				pst = con.prepareStatement("select * from platform");
				rs = pst.executeQuery();
				List<Platform> platForms = new ArrayList<Platform>();
				while(rs.next()){
					Platform pf = new Platform();
					pf.setId(rs.getInt(1));
					pf.setPlatForm(rs.getString(2));
					platForms.add(pf);
				}
				session.setAttribute("platforms", platForms);
				RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
				rd.forward(request,response);
			}catch (ClassNotFoundException e) {
				request.setAttribute("message", "Server connection failed..please try again");
				RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}catch (SQLException e) {
				request.setAttribute("message", "Problem loading video list");
				RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}finally{
				try {
					con.close();
				} 
				catch (SQLException e) {
					request.setAttribute("message", "Server busy..please try again later");
					RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
					rd.forward(request,response);
				}
				catch(NullPointerException e) {
					e.printStackTrace();
					request.setAttribute("message", "Server not connected..please try again later");
					RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
					rd.forward(request,response);
				}
		    }
		}else{
			request.setAttribute("message", "Unable to login..... Pleas try again!!!!!");
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}
	}

}
