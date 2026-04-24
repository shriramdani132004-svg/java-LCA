package com.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class AddCompanyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String location = request.getParameter("location");
        double cgpa = Double.parseDouble(request.getParameter("cgpa"));

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO companies(company_name, location, eligibility_cgpa) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, location);
            ps.setDouble(3, cgpa);

            ps.executeUpdate();

            response.getWriter().println("<h3>Company Added Successfully!</h3>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}