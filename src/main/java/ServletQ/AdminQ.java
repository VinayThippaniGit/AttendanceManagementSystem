package ServletQ;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Dao.ConnectionDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "a", urlPatterns = "/admin")
public class AdminQ extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String u = req.getParameter("username");
		String p = req.getParameter("password");

		try {
			PreparedStatement ps = ConnectionDao.getConnection()
					.prepareStatement("select * from admin where username=? AND password=?");
			ps.setString(1, u);
			ps.setString(2, p);
			ResultSet rs = ps.executeQuery();

			ResultSet rst = AdminQ.getTeacherData();
			ArrayList<String> teachersName = new ArrayList();
			ArrayList<String> subject = new ArrayList();

			while (rst.next()) {
				teachersName.add(rst.getString(1));
				subject.add(rst.getString(2));
			}

			
				int i = 0;
				// ==========================================================
				PrintWriter pw = resp.getWriter();
				pw.print("<!DOCTYPE html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n"
						+ "	<meta charset=\"UTF-8\">\r\n"
						+ "	<meta name=\"viewport\" content=\"width=device-width , initial-scale=1.0\">\r\n"
						+ "	<title>MTEC</title>\r\n" + "	<link rel=\"stylesheet\" href=\"style.css\">\r\n"
						+ "	<script src=\"script.js\"></script>\r\n" + "\r\n" + "	<style>\r\n" + "\r\n"
						+ "	</style>\r\n" + "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n" + "\r\n" + "	<header>\r\n"
						+ "		<h1>MOTHER THERESSA COLLEGE</h1>\r\n" + "	</header>\r\n" + "\r\n"
						+ "	<div class=\"nav\">\r\n" + "		<a href=#>Home</a>\r\n"
						+ "		<a href=\"#\">About Us</a>\r\n" + "		<a href=\"#\">Services</a>\r\n"
						+ "		<a href=\"#\">Locations</a>\r\n" + "		<a href=\"#\">Contact Us</a>");
				
				if (rs.next()) {
						pw.print("<div class=\"dropdown\"><div class=\"loginbtn\" onclick=\"logout()\">*logout</div>\r\n" + "		</div>\r\n"
						+ "	</div>\r\n" + "	\r\n" + "	<table border=\"1px\">\r\n" + "		<th>Teacher Name</th>\r\n"
						+ "		<th>Subject</th>\r\n");

				while (i < subject.size()) {
					pw.print("<tr>" + "<td>" + teachersName.get(i) + "</td>" + "<td>" + subject.get(i) + "</td>"
							+ "</td>");
					i++;

				}
				pw.print("	</table>");
				}else{
					pw.print("</div><br><br><h2 class=error>Invalid Credentials</h2>"
							+ "<a href=index.html>Click here to login again</a><br><br><br><br><br><br>");
				}
				
				pw.print("<footer class=\"footer\">\r\n"
						+ "		<p>&copy; 2024 QUniversity. All rights reserved.</p>\r\n" + "\r\n"
						+ "		<section class=\"section\">\r\n" + "			<h2>Contact Us</h2>\r\n"
						+ "			<p>Have questions? Need assistance? Our friendly team is here to help.</p>\r\n"
						+ "			<p>Phone: 1-800-QUNIVERSITY</p>\r\n"
						+ "			<p>Email: info@quniversity.com</p>\r\n" + "			<p>Visit Us: [Address]</p>\r\n"
						+ "		</section>\r\n" + "	</footer>\r\n" + "	\r\n" + "	<script src=\"script.js\">\r\n"
						+ "		\r\n" + "	</script>\r\n" + "\r\n" + "</body>\r\n" + "\r\n" + "</html>");

				// ==========================================================
			

		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("Couldnt get connection");
		}

	}

	public static ResultSet getTeacherData() throws SQLException {
		Statement st = ConnectionDao.getConnection().createStatement();
		ResultSet rst = st.executeQuery("select * from teachers");

		return rst;
	}

}
