package com.library;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class Logoutservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // ✅ Clears all session attributes
        }
        response.sendRedirect("index.jsp");
    }
}
