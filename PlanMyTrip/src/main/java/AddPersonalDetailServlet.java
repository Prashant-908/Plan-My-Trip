import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AddPersonalDetailServlet")
public class AddPersonalDetailServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Your DB Password";  // CHANGE THIS

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String id_type = request.getParameter("id_type");
        String id_number = request.getParameter("id_number");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String country = request.getParameter("country");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        try (Connection con = getConnection()) {
            String sql = "INSERT INTO personal_details (username, id_type, id_number, name, gender, country, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, id_type);
                ps.setString(3, id_number);
                ps.setString(4, name);
                ps.setString(5, gender);
                ps.setString(6, country);
                ps.setString(7, address);
                ps.setString(8, phone);
                ps.setString(9, email);
                ps.executeUpdate();
            }
            response.sendRedirect("AddPersonalDetails.html?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AddPersonalDetails.html?success=false");
        }
    }
}

