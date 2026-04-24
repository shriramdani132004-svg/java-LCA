package com.project.servlet;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.dao.ReportDAO;

public class ReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReportDAO dao = new ReportDAO();

        List<String[]> report1 = dao.getStudentsPerCompany();

        request.setAttribute("report1", report1);

        request.getRequestDispatcher("reports.jsp").forward(request, response);
    }
}