package com.library;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class BookIssueServlet extends HttpServlet {

    // GET — show current issued books + available book list
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("code") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String userCode = (String) session.getAttribute("code");

        String book1Code = null, book1IssueDate = null, book1ReturnDate = null;
        String book2Code = null, book2IssueDate = null, book2ReturnDate = null;
        List<String[]> availableBooks = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal")) {

                // Get current issued books for this user
                PreparedStatement ps1 = con.prepareStatement(
                    "SELECT book1_code, book1_issuedate, book1_returndate, " +
                    "book2_code, book2_issuedate, book2_returndate FROM info WHERE code = ?"
                );
                ps1.setString(1, userCode);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    book1Code       = rs1.getString("book1_code");
                    book1IssueDate  = rs1.getString("book1_issuedate");
                    book1ReturnDate = rs1.getString("book1_returndate");
                    book2Code       = rs1.getString("book2_code");
                    book2IssueDate  = rs1.getString("book2_issuedate");
                    book2ReturnDate = rs1.getString("book2_returndate");
                }

                // Get all books from book table
                PreparedStatement ps2 = con.prepareStatement(
                    "SELECT bk_code, bk_name, bk_author FROM book ORDER BY bk_code"
                );
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    availableBooks.add(new String[]{
                        rs2.getString("bk_code"),
                        rs2.getString("bk_name"),
                        rs2.getString("bk_author")
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("book1Code",       book1Code);
        request.setAttribute("book1IssueDate",  book1IssueDate);
        request.setAttribute("book1ReturnDate", book1ReturnDate);
        request.setAttribute("book2Code",       book2Code);
        request.setAttribute("book2IssueDate",  book2IssueDate);
        request.setAttribute("book2ReturnDate", book2ReturnDate);
        request.setAttribute("availableBooks",  availableBooks);
        request.getRequestDispatcher("bookissue.jsp").forward(request, response);
    }

    // POST — issue selected book to slot 1 or 2
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("code") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String userCode  = (String) session.getAttribute("code");
        String bookCode  = request.getParameter("bookCode");
        String slot      = request.getParameter("slot"); // "1" or "2"
        String issueDate = java.time.LocalDate.now().toString();
        String returnDate = java.time.LocalDate.now().plusDays(14).toString();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal")) {

                String sql;
                if ("2".equals(slot)) {
                    sql = "UPDATE info SET book2_code=?, book2_issuedate=?, book2_returndate=? WHERE code=?";
                } else {
                    sql = "UPDATE info SET book1_code=?, book1_issuedate=?, book1_returndate=? WHERE code=?";
                }
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, bookCode);
                ps.setString(2, issueDate);
                ps.setString(3, returnDate);
                ps.setString(4, userCode);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("BookIssueServlet");
    }
}
