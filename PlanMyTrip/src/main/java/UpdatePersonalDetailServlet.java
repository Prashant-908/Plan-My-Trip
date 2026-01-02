import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/UpdatePersonalDetailServlet")
public class UpdatePersonalDetailServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "prashant6689";  // CHANGE THIS

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
            String sql = "UPDATE personal_details SET id_type=?, id_number=?, name=?, gender=?, country=?, address=?, phone=?, email=? WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, id_type);
                ps.setString(2, id_number);
                ps.setString(3, name);
                ps.setString(4, gender);
                ps.setString(5, country);
                ps.setString(6, address);
                ps.setString(7, phone);
                ps.setString(8, email);
                ps.setString(9, username); // WHERE clause!
                int result = ps.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("UpdatePersonalDetails.html?success=true");
                } else {
                    response.sendRedirect("UpdatePersonalDetails.html?success=false");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("UpdatePersonalDetails.html?success=false");
        }
    }
}
