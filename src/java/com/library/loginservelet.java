package com.library;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
public class loginservelet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        Random rand = new Random();
        char letter = (char) ('A' + rand.nextInt(26));
        int number = rand.nextInt(9000) + 1000;
        String uqcode = letter + "" + number;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal");
            stmt = con.prepareStatement("INSERT INTO info(name, code) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, uqcode);
            stmt.executeUpdate();

            // ✅ SET session attributes so index.jsp can read them
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            session.setAttribute("code", uqcode);

            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=true");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
