package com.project.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class GenerateCertificateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // ✅ Get from URL first (Admin side)
        String studentName = request.getParameter("student");
        String company = request.getParameter("company");

        // ✅ Fallback to session (Student side)
        if (studentName == null && session != null) {
            studentName = (String) session.getAttribute("name");
        }

        // ✅ Safety fallback
        if (studentName == null) {
            studentName = "Student";
        }

        if (company == null) {
            company = "Company";
        }

        // Pass to JSP
        request.setAttribute("studentName", studentName);
        request.setAttribute("company", company);

        request.getRequestDispatcher("certificate.jsp").forward(request, response);
    }
}