package com.project.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class ViewApplicationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String,Object>> list = new ArrayList<>();

        try(Connection con = DBConnection.getConnection()){

            String query =
                "SELECT a.application_id, u.name, c.company_name, i.role, a.status " +
                "FROM applications a " +
                "JOIN students s ON a.student_id=s.student_id " +
                "JOIN users u ON s.user_id=u.user_id " +
                "JOIN internships i ON a.internship_id=i.internship_id " +
                "JOIN companies c ON i.company_id=c.company_id";

            ResultSet rs = con.prepareStatement(query).executeQuery();

            while(rs.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("id", rs.getInt("application_id"));
                row.put("name", rs.getString("name"));
                row.put("company", rs.getString("company_name"));
                row.put("role", rs.getString("role"));
                row.put("status", rs.getString("status"));
                list.add(row);
            }

        }catch(Exception e){e.printStackTrace();}

        request.setAttribute("applications", list);
        request.getRequestDispatcher("viewApplications.jsp").forward(request,response);
    }
}