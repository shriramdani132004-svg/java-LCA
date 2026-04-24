package com.project.servlet;

import java.io.IOException;
import javax.servlet.http.*;

import com.project.dao.AuditLogDAO;

public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {

            int userId = (int) session.getAttribute("userId");

            AuditLogDAO.addLog(
                userId,
                "LOGOUT",
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
            );

            session.invalidate();
        }

        response.sendRedirect("login.jsp");
    }
}