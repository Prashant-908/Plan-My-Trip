import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ViewPersonalDetailServlet")
public class ViewPersonalDetailServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Your DB Password"; // CHANGE THIS

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String username = request.getParameter("username");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<title>View Personal Details</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");
        out.println("<style>");
        out.println(".profile-card {max-width: 400px; margin:50px auto; background:#fff; border-radius:18px; box-shadow:0 8px 32px #185a9d23; padding:2em;}");
        out.println(".profile-title {font-size:1.35rem; font-weight:700; color:#185a9d; text-align:center; margin-bottom:1em; letter-spacing:1px;}");
        out.println(".profile-row {padding:10px 0; border-bottom:1px solid #e0f0ff; display:flex; align-items:center;}");
        out.println(".profile-label {width:120px; color:#185a9d; font-weight:600;}");
        out.println(".profile-value {color:#1b263b;}");
        out.println("</style></head><body style='background: radial-gradient(circle at 23% 20%, #43cea2 0%, #185a9d 85%); min-height:100vh;'>");

        if (username == null || username.trim().isEmpty()) {
            out.println("<div class='alert alert-danger text-center fw-bold mt-5'>Username required!</div>");
            out.println("</body></html>");
            return;
        }

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM personal_details WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        out.println("<div class='profile-card'>");
                        out.println("<div class='profile-title'><i class='bi bi-person-badge' style='color:#43cea2;'></i> Personal Detail</div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Username</div><div class='profile-value'>" +
                                    rs.getString("username") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>ID Type</div><div class='profile-value'>" +
                                    rs.getString("id_type") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>ID Number</div><div class='profile-value'>" +
                                    rs.getString("id_number") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Name</div><div class='profile-value'>" +
                                    rs.getString("name") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Gender</div><div class='profile-value'>" +
                                    rs.getString("gender") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Country</div><div class='profile-value'>" +
                                    rs.getString("country") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Address</div><div class='profile-value'>" +
                                    rs.getString("address") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Phone</div><div class='profile-value'>" +
                                    rs.getString("phone") + "</div></div>");
                        out.println("<div class='profile-row'><div class='profile-label'>Email</div><div class='profile-value'>" +
                                    rs.getString("email") + "</div></div>");
                        out.println("<a href='Dashboard.html' class='btn btn-dark mt-4 w-100'>Back to Dashboard</a>");
                        out.println("</div>");
                    } else {
                        out.println("<div class='alert alert-warning text-center fw-bold mt-5'>No details found for username '<span style='color:#185a9d;'>" + username + "</span>'</div>");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<div class='alert alert-danger text-center fw-bold mt-5'>Error: " + e.getMessage() + "</div>");
        }
        out.println("</body></html>");
    }
}

