package mx.com.everis.architecture.tools;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import mx.com.everis.dao.IFacebookUsersDao;

/**
 * Servlet implementation class ServletTools
 */
@WebServlet("/ServletTools")
public class ServletTools extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IFacebookUsersDao facebookUsersDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTools() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String accion = request.getParameter("action") != null ? request.getParameter("action").toString() : "";
		String user = request.getParameter("user") != null ? request.getParameter("user").toString() : "";
		String password = request.getParameter("password") != null ? request.getParameter("password").toString() : "";
		if ( !accion.equals("") && validateUser(user, password) ){
			if (accion.equals("RESTART_REGISTER")){
				facebookUsersDao.deleteUsersRegistered();
				response.getWriter().append("La base de datos de usuarios registrados ha sido limpiada");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public boolean validateUser(String user, String password){
		String passwordToken = "EAAWpG5xBwqoBAO4f03WG3s6dhmSUd9v3hSZBZCba9IZAHZC9fVr43AasfUgztWrbFokyn4ZCMCZBz6I2YxzDxZBUfqC60kwsT4S8YA6TTZAjb6JmxjP5sVpDjnZCqwZBZBwL2IL7yc1sFhgnPY5abS9570JJ5XeNtm5qg0xDNbJtlxSuQw4ZCwFVYKPD";
		boolean response = false; 
		if ( user.equals("everis1987") && password.equals(passwordToken) ){
			response = true;
		}
		return response;
	}
}
