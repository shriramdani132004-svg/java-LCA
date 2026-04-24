package com.project.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();

        // Allow public resources
        if (path.contains("login.jsp") ||
            path.contains("login") ||
            path.contains("style.css") ||
            path.contains("error.jsp") ||
            path.contains("index.jsp")) {

            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);

        // If not logged in
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");

        // Admin-only pages
        if (path.contains("admin") ||
            path.contains("addCompany") ||
            path.contains("addInternship") ||
            path.contains("viewStudents") ||
            path.contains("viewLogs") ||
            path.contains("viewApplications")) {

            if (!"ADMIN".equals(role)) {
                response.sendRedirect("login.jsp");
                return;
            }
        }

        // Student-only pages
        if (path.contains("studentDashboard") ||
            path.contains("applyInternship") ||
            path.contains("startExam") ||
            path.contains("viewInternships")) {

            if (!"STUDENT".equals(role)) {
                response.sendRedirect("login.jsp");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}