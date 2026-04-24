package com.project.servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class DeleteStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int studentId = Integer.parseInt(request.getParameter("id"));

        try (Connection con = DBConnection.getConnection()) {

            // Get user_id
            PreparedStatement ps = con.prepareStatement(
                "SELECT user_id FROM students WHERE student_id=?"
            );
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();
            rs.next();
            int userId = rs.getInt("user_id");

            // Delete student
            PreparedStatement del1 = con.prepareStatement(
                "DELETE FROM students WHERE student_id=?"
            );
            del1.setInt(1, studentId);
            del1.executeUpdate();

            // Delete user
            PreparedStatement del2 = con.prepareStatement(
                "DELETE FROM users WHERE user_id=?"
            );
            del2.setInt(1, userId);
            del2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("viewStudents");
    }
}