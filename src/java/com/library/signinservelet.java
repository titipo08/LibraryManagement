package com.library;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
public class signinservelet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("name");
        String code = request.getParameter("code");
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal");
            stmt = con.prepareStatement("SELECT name, code FROM info WHERE name=? AND code=?");
            stmt.setString(1, user);
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // ✅ SET session attributes on successful login
                HttpSession session = request.getSession();
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("code", rs.getString("code"));
                response.sendRedirect("section1.jsp");
            } else {
                // ✅ Forward with error attribute instead of redirect
                request.setAttribute("error", "true");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "true");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
