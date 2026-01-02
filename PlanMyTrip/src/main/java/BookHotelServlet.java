import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/BookHotelServlet")
public class BookHotelServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/travel_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Your DB Password"; // CHANGE TO YOUR PASSWORD

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String hotel_name = request.getParameter("hotel_name");
        String username = request.getParameter("username");
        String guests = request.getParameter("guests");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String phone = request.getParameter("phone");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'><title>Booking Confirmation</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>body{background: radial-gradient(circle at 23% 20%, #43cea2 0%, #185a9d 85%);} .booking-box{background:#fff; border-radius:16px; box-shadow:0 8px 32px #185a9d23; padding:2em; margin:60px auto; max-width:580px;} .booking-title{font-size:1.5rem; font-weight:700; color:#185a9d; text-align:center;}</style>");
        out.println("</head><body><div class='booking-box'>");

        try (Connection con = getConnection()) {
            String sql = "INSERT INTO hotel_bookings (hotel_name, username, guests, checkin, checkout, phone) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, hotel_name);
                ps.setString(2, username);
                ps.setString(3, guests);
                ps.setString(4, checkin);
                ps.setString(5, checkout);
                ps.setString(6, phone);
                ps.executeUpdate();

                out.println("<div class='booking-title'>Booking Successful!</div>");
                out.println("<div class='mt-3 alert alert-success text-center'>Your booking for hotel <b>" + hotel_name + "</b> is confirmed.<br>");
                out.println("Check-in: <b>" + checkin + "</b> | Check-out: <b>" + checkout + "</b><br>");
                out.println("Guests: <b>" + guests + "</b> | Username: <b>" + username + "</b></div>");
                out.println("<div class='mt-2 text-center'><a href='ViewHotel.html' class='btn btn-dark'>Book Another Hotel</a></div>");
            }
        } catch (Exception e) {
            out.println("<div class='booking-title'>Booking Failed!</div>");
            out.println("<div class='alert alert-danger text-center mt-3'>Error: " + e.getMessage() + "</div>");
            out.println("<div class='mt-2 text-center'><a href='BookHotel.html?hotel=" + hotel_name + "' class='btn btn-dark'>Try Again</a></div>");
        }

        out.println("</div></body></html>");
    }
}

