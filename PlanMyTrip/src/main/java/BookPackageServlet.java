import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/BookPackageServlet")
public class BookPackageServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Your DB Password"; // CHANGE THIS

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String package_type = request.getParameter("package_type");
        String total_persons = request.getParameter("total_persons");
        String id_type = request.getParameter("id_type");
        String id_number = request.getParameter("id_number");
        String phone = request.getParameter("phone");
        String total_price = request.getParameter("total_price");

        try (Connection con = getConnection()) {
            String sql = "INSERT INTO bookings (username, package_type, total_persons, id_type, id_number, phone, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, package_type);
                ps.setString(3, total_persons);
                ps.setString(4, id_type);
                ps.setString(5, id_number);
                ps.setString(6, phone);
                ps.setString(7, total_price);
                ps.executeUpdate();
            }
            response.sendRedirect("BookPackage.html?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("BookPackage.html?success=false");
        }
    }
}

