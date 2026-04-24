package com.project.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class ViewStudentResultsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        List<Map<String,Object>> list = new ArrayList<>();

        try(Connection con = DBConnection.getConnection()){

            String query =
                "SELECT c.company_name, i.role, a.status " +
                "FROM applications a " +
                "JOIN students s ON a.student_id=s.student_id " +
                "JOIN internships i ON a.internship_id=i.internship_id " +
                "JOIN companies c ON i.company_id=c.company_id " +
                "WHERE s.user_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("company", rs.getString("company_name"));
                row.put("role", rs.getString("role"));
                row.put("status", rs.getString("status"));
                list.add(row);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("results", list);
        request.getRequestDispatcher("studentResults.jsp").forward(request, response);
    }
}