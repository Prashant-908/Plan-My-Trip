import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Your DB Password"; // CHANGE TO YOUR PASSWORD

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        String displayName = "";

        if (username != null) {
            try (Connection con = getConnection()) {
                String sql = "SELECT name FROM users WHERE username=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        displayName = rs.getString("name");
                    }
                }
            } catch (Exception e) { displayName = ""; }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'><title>Travel Management Dashboard</title>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");
        out.println("<style>");
        out.println("body{min-height:100vh;background:radial-gradient(circle at 20% 20%, #43cea2cc 0%, #185a9dcc 85%), url('image.jpg') no-repeat center center fixed;background-size:cover;}");
        out.println(".main-content{margin:0 auto;margin-top:48px;padding:36px 32px;border-radius:1.5rem;background:rgba(255,255,255,0.82);box-shadow:0 12px 42px #185a9d22;max-width:1080px;}");
        out.println(".dashboard-title{text-align:center;font-weight:bold;color:#185a9d;font-size:2.25rem;margin-bottom:30px;letter-spacing:2px;text-shadow:0 6px 28px #185a9d55;}");
        out.println(".dashboard-grid{display:flex;flex-wrap:wrap;gap:30px;justify-content:center;}");
        out.println(".dashboard-card{background:linear-gradient(135deg,#185a9d 62%,#43cea299 100%);width:252px;min-height:135px;border-radius:1.15rem;color:#fff;box-shadow:0 6px 22px #185a9d18;display:flex;flex-direction:row;align-items:center;gap:18px;padding:22px 22px;font-size:1.09rem;font-weight:600;text-decoration:none;opacity:0.98;transition:all 0.14s;border:2px solid #43cea2;}");
        out.println(".dashboard-card i{font-size:1.9rem;color:#43cea2;background:#185a9d40;border-radius:50%;width:42px;height:42px;display:flex;align-items:center;justify-content:center;box-shadow:0 3px 12px #43cea250;}");
        out.println(".dashboard-card:hover,.dashboard-card:focus{background:linear-gradient(135deg,#43cea2 30%,#185a9d 100%);color:#fff;box-shadow:0 14px 34px #43cea260,0 0 0 2px #43cea260;transform:translateY(-2px) scale(1.045);text-decoration:none;border:2px solid #185a9d;}");
        out.println("@media (max-width:900px){.main-content{padding:12px 4px;}.dashboard-grid{gap:12px;}.dashboard-card{width:98vw;max-width:400px;}.dashboard-title{font-size:1.45rem;margin-bottom:14px;}}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='main-content'>");
        out.println("<div class='dashboard-title'>Welcome, <span style='color:#43cea2'>" +
            (displayName != null && !displayName.isEmpty() ? displayName : "Prashant") + "</span>!</div>");
        out.println("<div class='dashboard-grid'>");
        out.println("<a class='dashboard-card' href='AddPersonalDetails.html'><i class='bi bi-person-plus-fill'></i> Add Personal Details</a>");
        out.println("<a class='dashboard-card' href='UpdatePersonalDetails.html'><i class='bi bi-pencil-square'></i> Update Personal Details</a>");
        out.println("<a class='dashboard-card' href='ViewPersonalDetails.html'><i class='bi bi-eye-fill'></i> View Personal Details</a>");
        out.println("<a class='dashboard-card' href='CheckPackage.html'><i class='bi bi-gift-fill'></i> Check Package</a>");
        out.println("<a class='dashboard-card' href='BookPackage.html'><i class='bi bi-box2-heart-fill'></i> Book Package</a>");
        out.println("<a class='dashboard-card' href='ViewBookedPackagesServlet'><i class='bi bi-clipboard-check-fill'></i> View Booked Packages</a>");
        out.println("<a class='dashboard-card' href='ViewHotel.html'><i class='bi bi-buildings-fill'></i> View Hotels</a>");
        out.println("<a class='dashboard-card' href='BookHotel.html'><i class='bi bi-house-heart-fill'></i> Book Hotel</a>");
        out.println("<a class='dashboard-card' href='ViewDestination.html'><i class='bi bi-geo-alt-fill'></i> Destinations</a>");
        out.println("<a class='dashboard-card' href='LogoutServlet'><i class='bi bi-door-open-fill'></i> Logout</a>");
        out.println("</div></div></body></html>");
    }
}

