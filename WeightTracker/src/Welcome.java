

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static int[] intArray;
	Connection con;
	
	PreparedStatement pre = null;
	
	PreparedStatement pre2 = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
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
		out.println("Welcome user, your weight history will be displayed in a graph");
		String w = request.getParameter("weights");//get the weight entered
		String d = new SimpleDateFormat("MM-dd-yyyy").format(new Date());//get current date
		final String qAddWeight = "update stats set weights = concat(weights, ' ', " + w + "), dates = concat(dates, ' ', '"
		        + d + "') where name='" + WeightServlet.n
		        + "';";
		try {//add the new weight, date to the string of old weights, dates in the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/weight", "root", "nyyin07");
			pre = con.prepareStatement(qAddWeight);
			pre.executeUpdate();
			String check = "select weights from stats where name='" + WeightServlet.n + "';";
			pre2 = con.prepareStatement(check);
			ResultSet r1 = pre2.executeQuery();
			r1.next();
			String res = r1.getString(1);
			String[] splitArray = {};
			try {//take the string of weights and turn it into an array where each weight is separate
				splitArray = res.split("\\s+");
			}
			catch (PatternSyntaxException e) {
				e.printStackTrace();
			}
			intArray = new int[splitArray.length];
			for (int i = 0; i < splitArray.length; i++) {//turn array of strings to array of ints
				intArray[i] = Integer.parseInt(splitArray[i]);
			}
			String dateCheck = "select dates from stats where name='" + WeightServlet.n + "';";
			pre3 = con.prepareStatement(dateCheck);
			ResultSet r2 = pre3.executeQuery();
			r2.next();
			String res2 = r2.getString(1);
			try {
				dateSplit = res2.split("\\s+");//split string of dates into array
			}
			catch (PatternSyntaxException e) {
				e.printStackTrace();
			}
			WeightLineChart.launch(WeightLineChart.class);//launch chart
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
