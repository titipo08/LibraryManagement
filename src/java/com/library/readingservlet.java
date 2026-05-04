package com.library;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.sql.*;
public class readingservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = (String) request.getSession().getAttribute("code");
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String name = "";
        String info = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal");

            // Get user name
            stmt = con.prepareStatement("SELECT name FROM info WHERE code = ?");
            stmt.setString(1, code);
            rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }

            // ✅ Save date and time to DB
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            PreparedStatement update = con.prepareStatement(
                "UPDATE info SET date = ?, time = ? WHERE code = ?"
            );
            update.setString(1, date.toString());
            update.setString(2, formattedTime);
            update.setString(3, code);
            update.executeUpdate();

            info = "Name : " + name +
                   " | Date : " + date +
                   " | Time : " + formattedTime;

            // ✅ Save to session for DisplayInfoServlet
            request.getSession().setAttribute("readingTime", date + " " + formattedTime);

            request.setAttribute("info", info);
            request.getRequestDispatcher("reading.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("section1.jsp?error=true");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
