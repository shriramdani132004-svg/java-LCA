package com.project.servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class ApplyInternshipServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int internshipId = Integer.parseInt(request.getParameter("internshipId"));

        try (Connection con = DBConnection.getConnection()) {

            int studentId = 0;
            double studentCgpa = 0;

            // student_id + cgpa
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT student_id, cgpa FROM students WHERE user_id=?"
            );
            ps1.setInt(1, userId);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                studentId = rs1.getInt("student_id");
                studentCgpa = rs1.getDouble("cgpa");
            } else {
                response.sendRedirect(request.getContextPath() + "/viewInternships?error=NoStudent");
                return;
            }

            // required cgpa
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT c.eligibility_cgpa FROM internships i " +
                "JOIN companies c ON i.company_id = c.company_id " +
                "WHERE i.internship_id=?"
            );
            ps2.setInt(1, internshipId);
            ResultSet rs2 = ps2.executeQuery();

            double requiredCgpa = 0;
            if (rs2.next()) {
                requiredCgpa = rs2.getDouble("eligibility_cgpa");
            }

            // eligibility
            if (studentCgpa < requiredCgpa) {
                response.sendRedirect(request.getContextPath() + "/viewInternships?error=NotEligible");
                return;
            }

            // duplicate
            PreparedStatement check = con.prepareStatement(
                "SELECT 1 FROM applications WHERE student_id=? AND internship_id=?"
            );
            check.setInt(1, studentId);
            check.setInt(2, internshipId);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                response.sendRedirect(request.getContextPath() + "/viewInternships?error=AlreadyApplied");
                return;
            }

            // insert
            PreparedStatement insert = con.prepareStatement(
                "INSERT INTO applications(student_id, internship_id, status) VALUES(?,?,?)"
            );
            insert.setInt(1, studentId);
            insert.setInt(2, internshipId);
            insert.setString(3, "APPLIED");
            insert.executeUpdate();

            // keep for next page
            session.setAttribute("internshipId", internshipId);

            // ✅ ALWAYS GO TO SUCCESS PAGE
            response.sendRedirect(request.getContextPath() + "/applicationSuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/viewInternships?error=ServerError");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/viewInternships");
    }
}