package com.project.servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class RemoveApplicationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int internshipId = Integer.parseInt(request.getParameter("internshipId"));

        try (Connection con = DBConnection.getConnection()) {

            // 🔴 GET student_id from user_id
            PreparedStatement ps = con.prepareStatement(
                "SELECT student_id FROM students WHERE user_id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                response.sendRedirect("studentDashboard.jsp");
                return;
            }

            int studentId = rs.getInt("student_id");

            PreparedStatement del = con.prepareStatement(
                "DELETE FROM applications WHERE student_id=? AND internship_id=?"
            );

            del.setInt(1, studentId);
            del.setInt(2, internshipId);
            del.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("studentDashboard.jsp");
    }
}