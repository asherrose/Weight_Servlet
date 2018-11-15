

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WeightServlet
 */
@WebServlet("/WeightServlet")
public class WeightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String n;
	
	Connection conn;
	
	PreparedStatement pre = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeightServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		n = request.getParameter("user");//entered username
		String p = request.getParameter("pwd");//entered password
		
		final String qCheck = "select count(*) from stats where name='" + n + "' and password='" + p + "';";
		try {//check database to see if user and password are in there
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weight", "root", "nyyin07");
			pre = conn.prepareStatement(qCheck);
			ResultSet r1 = pre.executeQuery();
			r1.next();
			String res = r1.getString(1);
			int resInt = Integer.parseInt(res);
			if (resInt > 0) {//that user exists, proceed
				request.getRequestDispatcher("weight.html").forward(request, response);
			}
			else {//if not, reenter user/password
				out.println("Username or password incorrect");
				RequestDispatcher rs = request.getRequestDispatcher("login.html");
				rs.include(request, response);
			}
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
