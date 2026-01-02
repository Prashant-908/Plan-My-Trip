import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ViewHotelServlet")
public class ViewHotelServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "prashant6689"; // CHANGE THIS

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'><title>View Hotels</title>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println("body { background: radial-gradient(circle at 23% 20%, #43cea2 0%, #185a9d 85%); min-height: 100vh;}");
        out.println(".main-title {font-size:2rem; color:#185a9d; font-weight:700; text-align:center; margin:38px 0;}");
        out.println(".hotel-card { background:#fff; border-radius:16px; box-shadow:0 8px 32px #185a9d23; padding:1.2em; margin-bottom:28px;}");
        out.println(".hotel-title {font-size:1.25rem; font-weight:bold; color:#43cea2; margin-bottom:8px;}");
        out.println(".hotel-img {width:100%; max-width:350px; border-radius:10px;}");
        out.println(".feature-row { font-size:1.09rem; margin:4px 0;}");
        out.println(".price-row { font-size:1.12rem; color:#18c618; font-weight:600;}");
        out.println("</style></head><body>");
        out.println("<div class='container'><div class='main-title'>Available Hotels</div><div class='row'>");

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM hotels";
            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    out.println("<div class='col-md-6'><div class='hotel-card'>");
                    out.println("<div class='hotel-title'>" + rs.getString("name") + "</div>");
                    out.println("<img src='" + rs.getString("image_url") + "' class='hotel-img mb-2' alt='" + rs.getString("name") + "'>");
                    out.println("<div class='feature-row'>Location: " + rs.getString("location") + "</div>");
                    out.println("<div class='feature-row'>Type: " + rs.getString("type") + "</div>");
                    out.println("<div class='feature-row'>" + rs.getString("facilities") + "</div>");
                    out.println("<div class='feature-row'>Max Guests: " + rs.getInt("max_guests") + " per room</div>");
                    out.println("<div class='price-row'>Starting price: ₹" + rs.getInt("price_per_night") + "/night</div>");
                    out.println("</div></div>");
                }
                if (!found) {
                    out.println("<div class='col-12 text-center text-danger fw-bold'>No hotel records found!</div>");
                }
            }
        } catch (Exception e) {
            out.println("<div class='col-12 text-center text-danger fw-bold'>Error: " + e.getMessage() + "</div>");
        }

        out.println("</div><div class='mt-4 text-center'>");
        out.println("<a href='Dashboard.html' class='btn btn-dark btn-lg'>Back to Dashboard</a>");
        out.println("</div></div></body></html>");
    }
}
