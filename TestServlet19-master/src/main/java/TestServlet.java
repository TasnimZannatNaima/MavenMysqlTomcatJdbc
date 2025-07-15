import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ✅ Database connection method
    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure MySQL driver is loaded
        String url = "jdbc:mysql://127.0.0.1:3306/test"; // Your DB URL
        String user = "root";
        String password = "12345";
        return DriverManager.getConnection(url, user, password);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("studentName");
        String email = request.getParameter("studentEmail");
        String course = request.getParameter("studentCourse");
        String semester = request.getParameter("studentSemester");
        String action = request.getParameter("action");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><style>");
        out.println("table { border-collapse: collapse; width: 50%; }");
        out.println("th, td { border: 1px solid #333; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style></head><body>");
        out.println("<h2>Action: " + action + "</h2>");

        try (Connection conn = connect()) {

            if ("Insert".equals(action)) {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO course_registration(name, email, course, semester) VALUES (?, ?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, course);
                ps.setString(4, semester);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    out.println("<p style='color:green;'>✅ Inserted Successfully!</p>");
                } else {
                    out.println("<p style='color:red;'>❌ Insertion failed.</p>");
                }

            } else if ("View".equals(action)) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM course_registration");
                out.println("<table><tr><th>Name</th><th>Email</th><th>Course</th><th>Semester</th></tr>");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    out.println("<tr><td>" + rs.getString("name") + "</td><td>" + rs.getString("email") +
                            "</td><td>" + rs.getString("course") + "</td><td>" + rs.getString("semester") + "</td></tr>");
                }
                out.println("</table>");
                if (!found) {
                    out.println("<p style='color:orange;'>⚠️ No data found.</p>");
                }

            } else if ("Update".equals(action)) {
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE course_registration SET email=?, course=?, semester=? WHERE name=?");
                ps.setString(1, email);
                ps.setString(2, course);
                ps.setString(3, semester);
                ps.setString(4, name);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    out.println("<p style='color:green;'>✅ Updated successfully.</p>");
                } else {
                    out.println("<p style='color:red;'>❌ Update failed. Name not found?</p>");
                }

            } else if ("Delete".equals(action)) {
                PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM course_registration WHERE name=?");
                ps.setString(1, name);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    out.println("<p style='color:green;'>✅ Deleted successfully.</p>");
                } else {
                    out.println("<p style='color:red;'>❌ Delete failed. Name not found?</p>");
                }

            } else {
                out.println("<p style='color:red;'>⚠️ Unknown action: " + action + "</p>");
            }

        } catch (SQLException | ClassNotFoundException e) {
            out.println("<p style='color:red;'>❌ Database error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("</body></html>");
    }
}
