package com.library;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class BookReturnServlet extends HttpServlet {

    // GET — show currently issued books for return
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("code") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String userCode = (String) session.getAttribute("code");

        String book1Code = null, book1ReturnDate = null;
        String book2Code = null, book2ReturnDate = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal")) {

                PreparedStatement ps = con.prepareStatement(
                    "SELECT book1_code, book1_returndate, book2_code, book2_returndate " +
                    "FROM info WHERE code = ?"
                );
                ps.setString(1, userCode);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    book1Code       = rs.getString("book1_code");
                    book1ReturnDate = rs.getString("book1_returndate");
                    book2Code       = rs.getString("book2_code");
                    book2ReturnDate = rs.getString("book2_returndate");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("book1Code",       book1Code);
        request.setAttribute("book1ReturnDate", book1ReturnDate);
        request.setAttribute("book2Code",       book2Code);
        request.setAttribute("book2ReturnDate", book2ReturnDate);
        request.getRequestDispatcher("bookreturn.jsp").forward(request, response);
    }

    // POST — clear the returned book slot from info table
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("code") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String userCode = (String) session.getAttribute("code");
        String slot     = request.getParameter("slot"); // "1" or "2"

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal")) {

                String sql;
                if ("2".equals(slot)) {
                    sql = "UPDATE info SET book2_code=NULL, book2_issuedate=NULL, book2_returndate=NULL WHERE code=?";
                } else {
                    sql = "UPDATE info SET book1_code=NULL, book1_issuedate=NULL, book1_returndate=NULL WHERE code=?";
                }
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, userCode);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("BookReturnServlet");
    }
}
