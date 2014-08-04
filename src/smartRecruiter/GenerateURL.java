package smartRecruiter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateURL extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8425451261145728620L;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Linkedin lin = new Linkedin();
		try {
			String url = lin.generateAuthUrl();
			request.setAttribute("AuthURL", url);
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		} catch (Exception e) {
			request.setAttribute("message", "Error occured.... Try Again!!!!");
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.forward(request,response);
		}
		
	}

}
