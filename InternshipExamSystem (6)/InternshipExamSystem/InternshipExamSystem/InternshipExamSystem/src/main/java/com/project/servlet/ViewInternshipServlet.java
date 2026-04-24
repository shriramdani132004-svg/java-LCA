package com.project.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.dao.InternshipDAO;
import com.project.model.Internship;

public class ViewInternshipServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // ✅ added

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(); // ✅ FIXED (removed unused email)

        InternshipDAO dao = new InternshipDAO();
        List<Internship> list = dao.getAllInternships();

        request.setAttribute("internships", list);
        request.getRequestDispatcher("viewInternships.jsp").forward(request, response);
    }
}