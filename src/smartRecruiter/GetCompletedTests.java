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

public class GetCompletedTests extends HttpServlet {

	private static final long serialVersionUID = -4717082849160338864L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int job_id = Integer.parseInt(request.getParameter("job_id"));
		HttpSession session=request.getSession(true);
		ArrayList<Company> companies = (ArrayList<Company>) session.getAttribute("companies");
		ArrayList<Platform> platforms = (ArrayList<Platform>)session.getAttribute("platforms");
		Connection con=null;
		try {
			con=DBcon.connect();
			PreparedStatement pst=con.prepareStatement("select * from candidateInfo where job_id=?");
			pst.setInt(1, job_id);
			ResultSet rs=pst.executeQuery();
			List<CandidateInfoBean> candidates = new ArrayList<CandidateInfoBean>();
			while(rs.next()){
				CandidateInfoBean cand = new CandidateInfoBean();
				cand.setId(rs.getInt(1));
				cand.setFirstName(rs.getString(2));
				cand.setLastName(rs.getString(3));
				cand.setLinkToProfile(rs.getString(4));
				for(Company com : companies){
					if(com.getId() == job_id){
						cand.setJobId(com.getName() + " - " + com.getJobProfile());
						break;
					}
				}
				for(Platform plat : platforms){
					if(rs.getInt(6) == plat.getId()){
						cand.setPlatform(plat.getPlatForm());
						break;
					}
				}
				cand.setInterviewProfile(rs.getString(7));
				candidates.add(cand);
			}
			session.setAttribute("candidates", candidates);
			RequestDispatcher rd=request.getRequestDispatcher("reportTable.jsp");
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
