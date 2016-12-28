package mx.com.everis.architecture;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Url;
import com.restfb.types.User;

/**
 * Servlet implementation class ServletInfo
 */
@WebServlet("/ServletInfo")
public class ServletInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;



	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		PrintWriter out = response.getWriter();
		List<String> ids = new ArrayList<String>();
		ids.add("btaylor");
		ids.add("http://www.imdb.com/title/tt0117500/");

		// First, make the API call...
		FacebookClient facebookClient = new DefaultFacebookClient();
		JsonObject results = facebookClient.fetchObjects(ids, JsonObject.class);
		JsonMapper jsonMapper = new DefaultJsonMapper();
		User user = jsonMapper.toJavaObject(results.getString("btaylor"), User.class);
		Url url = jsonMapper.toJavaObject(results.getString("http://restfb.com"), Url.class);

		out.println("User is " + user);
		out.println("URL is " + url);

		/*Lectura de cadena enviada vía POST*/
		//		StringBuffer jb = new StringBuffer();
		//		String line = null;
		//		try {
		//			BufferedReader reader = request.getReader();
		//			while ((line = reader.readLine()) != null)
		//				jb.append(line);
		//		}catch(Exception ex){
		//			
		//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}



}
