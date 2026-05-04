package com.library;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class DisplayInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("code") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String userCode = (String) session.getAttribute("code");

        String userName = null, readingDate = null, readingTime = null;
        String book1Code = null, book1ReturnDate = null;
        String book2Code = null, book2ReturnDate = null;
        String penalty = "None";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/librarymanagement", "root", "kinjal")) {

                PreparedStatement ps = con.prepareStatement(
                    "SELECT name, date, time, book1_code, book1_returndate, " +
                    "book2_code, book2_returndate, penalty FROM info WHERE code = ?"
                );
                ps.setString(1, userCode);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    userName       = rs.getString("name");
                    readingDate    = rs.getString("date");
                    readingTime    = rs.getString("time");
                    book1Code      = rs.getString("book1_code");
                    book1ReturnDate= rs.getString("book1_returndate");
                    book2Code      = rs.getString("book2_code");
                    book2ReturnDate= rs.getString("book2_returndate");
                    String dbPenalty = rs.getString("penalty");
                    if (dbPenalty != null) penalty = dbPenalty;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String readingTimeDisplay = (readingDate != null && readingTime != null)
            ? readingDate + " " + readingTime : null;

        request.setAttribute("userName",      userName);
        request.setAttribute("readingTime",   readingTimeDisplay);
        request.setAttribute("book1Code",     book1Code);
        request.setAttribute("book1Return",   book1ReturnDate);
        request.setAttribute("book2Code",     book2Code);
        request.setAttribute("book2Return",   book2ReturnDate);
        request.setAttribute("penalty",       penalty);
        request.getRequestDispatcher("displayinfo.jsp").forward(request, response);
    }
}
