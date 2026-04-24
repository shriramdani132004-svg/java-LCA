package com.project.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.dao.AuditLogDAO;

public class LogActivityServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {

            int userId = (int) session.getAttribute("userId");
            String action = request.getParameter("action");

            AuditLogDAO.addLog(
                userId,
                action,
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
            );
        }
    }
}