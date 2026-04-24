package com.project.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class ViewStudentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("ADMIN")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ✅ FIXED GENERICS
        List<Map<String, Object>> students = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT s.student_id, u.name, u.email, s.cgpa, s.course " +
                           "FROM students s JOIN users u ON s.user_id = u.user_id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                // ✅ FIXED GENERICS
                Map<String, Object> row = new HashMap<>();

                row.put("id", rs.getInt("student_id"));
                row.put("name", rs.getString("name"));
                row.put("email", rs.getString("email"));
                row.put("cgpa", rs.getDouble("cgpa"));
                row.put("course", rs.getString("course"));

                students.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("students", students);
        request.getRequestDispatcher("viewStudents.jsp").forward(request, response);
    }
}