package com.project.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.dao.ApplicationLogDAO;
import com.project.model.ApplicationLog;

public class ViewLogsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ApplicationLogDAO dao = new ApplicationLogDAO();

        // ✅ MUST return List (NOT ResultSet)
        List<ApplicationLog> logs = dao.getAllLogs();

        request.setAttribute("logs", logs);

        request.getRequestDispatcher("viewLogs.jsp").forward(request, response);
    }
}