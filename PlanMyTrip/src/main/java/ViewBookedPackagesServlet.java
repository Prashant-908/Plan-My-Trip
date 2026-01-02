import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ViewBookedPackagesServlet")
public class ViewBookedPackagesServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "prashant6689"; // CHANGE THIS!

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<title>Booked Packages - Customer Details</title>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>body{background: radial-gradient(circle at 23% 20%, #43cea2 0%, #185a9d 85%); min-height:100vh;} .main-title{font-size:2rem; color:#185a9d; font-weight:700; text-align:center; margin:38px 0;} .table-wrapper{background:#fff; border-radius:14px; box-shadow:0 8px 32px #185a9d23; padding:2em;} .table{ background-color:#fff; border-radius:10px;}</style>");
        out.println("</head><body><div class='container'>");
        out.println("<div class='main-title'>Customer Details of Booked Packages</div>");
        out.println("<div class='table-wrapper'><table class='table table-bordered table-hover'><thead class='table-info'><tr>");
        out.println("<th>ID</th><th>Username</th><th>Package Type</th><th>Persons</th><th>ID Type</th><th>ID Number</th><th>Phone</th><th>Total Price</th>");
        out.println("</tr></thead><tbody>");

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM bookings";
            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("username") + "</td>");
                    out.println("<td>" + rs.getString("package_type") + "</td>");
                    out.println("<td>" + rs.getInt("total_persons") + "</td>");
                    out.println("<td>" + rs.getString("id_type") + "</td>");
                    out.println("<td>" + rs.getString("id_number") + "</td>");
                    out.println("<td>" + rs.getString("phone") + "</td>");
                    out.println("<td>₹" + rs.getInt("total_price") + "</td>");
                    out.println("</tr>");
                }
                if (!found) {
                    out.println("<tr><td colspan='8' class='text-center text-danger fw-bold'>No bookings found!</td></tr>");
                }
            }
        } catch (Exception e) {
            out.println("<tr><td colspan='8' class='text-center text-danger fw-bold'>Error: " + e.getMessage() + "</td></tr>");
        }

        out.println("</tbody></table>");
        out.println("<div class='mt-4 text-center'><a href='Dashboard.html' class='btn btn-dark btn-lg'>Back to Dashboard</a></div>");
        out.println("</div></div></body></html>");
    }
}
