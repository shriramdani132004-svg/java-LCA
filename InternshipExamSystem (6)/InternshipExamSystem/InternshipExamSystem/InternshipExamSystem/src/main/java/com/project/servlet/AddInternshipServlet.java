package com.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class AddInternshipServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int companyId = Integer.parseInt(request.getParameter("companyId"));
        String role = request.getParameter("role");
        double stipend = Double.parseDouble(request.getParameter("stipend"));
        String deadline = request.getParameter("deadline");

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO internships(company_id, role, stipend, deadline) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, companyId);
            ps.setString(2, role);
            ps.setDouble(3, stipend);
            ps.setString(4, deadline);

            ps.executeUpdate();

            response.getWriter().println("<h3>Internship Added Successfully!</h3>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}