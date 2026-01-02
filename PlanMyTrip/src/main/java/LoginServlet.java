import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    // Database connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "prashant6689"; // CHANGE THIS

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isAuthenticated = false;

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) isAuthenticated = true;
                }
            }
        } catch (Exception e) {
            response.sendRedirect("Login.html?error=server");
            return;
        }

        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("DashboardServlet");
        } else {
            response.sendRedirect("Login.html?error=true");
        }
    }
}
