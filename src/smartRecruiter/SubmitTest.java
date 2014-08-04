package smartRecruiter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.xml.sax.SAXException;

public class SubmitTest extends HttpServlet {
	
	private static final long serialVersionUID = -6296283850993797846L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int company = Integer.parseInt(request.getParameter("company"));
		int platform = Integer.parseInt(request.getParameter("platform"));
		HttpSession session = request.getSession(true);
		Token accessToken = (Token)session.getAttribute("token");
		List<String> questions = (ArrayList<String>)session.getAttribute("questions");
		List<String> solutions = new ArrayList<String>();
		for(int i=0; i<questions.size(); i++){
			solutions.add(request.getParameter("Solution"+(i+1)));
		}
		Connection con=null;
		try{
			con=DBcon.connect();
			OAuthRequest req = new OAuthRequest(Verb.GET, "http://api.linkedin.com/v1/people/~");
			OAuthService service = (OAuthService)session.getAttribute("service");
	        service.signRequest(accessToken, req);
	        Response resp = req.send();
	        CandidateInfoBean cand = Linkedin.parseProfileInfo(resp.getBody());
			PreparedStatement pst=con.prepareStatement("insert into candidateInfo(first_name,last_name,linkToProfile,job_id,platform_id,interviewPacketURL) values(?,?,?,?,?,?)");
			pst.setString(1, cand.getFirstName());
			pst.setString(2, cand.getLastName());
			pst.setString(3, cand.getLinkToProfile());
			pst.setInt(4, company);
			pst.setInt(5, platform);
			pst.setString(6, S3Actions.upload(questions, solutions, getServletContext(), cand.getLastName().toLowerCase()+"-"+company));
			pst.executeUpdate();
			request.setAttribute("message", "Exam Submitted successfully!!!!");
			RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
			rd.forward(request,response);
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", "Error occured : " + e.getMessage());
			RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
			rd.forward(request,response);
		}catch (SQLException e) {
			request.setAttribute("message", "Error occured : " + e.getMessage());
			RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
			rd.forward(request,response);
		} catch (ParserConfigurationException e) {
			request.setAttribute("message", "Error occured : " + e.getMessage());
			RequestDispatcher rd=request.getRequestDispatcher("selection.jsp");
			rd.forward(request,response);
		} catch (SAXException e) {
			request.setAttribute("message", "Error occured : " + e.getMessage());
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
	}

}
