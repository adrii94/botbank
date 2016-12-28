package mx.com.everis.architecture.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthenticationFlow
 */
@WebServlet("/AuthenticationFlow")
public class AuthenticationFlow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticationFlow() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			String redirURI = request.getParameter("redirect_uri").toString();
			String accToken = request.getParameter("account_linking_token").toString();
			String urlRedirect = "https://www.facebook.com/messenger_platform/account_linking?account_linking_token="+accToken+
					"&authorization_code=1234";
			System.out.println("Redirigiendo a: " + urlRedirect);
			PrintWriter pw = response.getWriter();
			
			
//			response.sendRedirect(urlRedirect);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
