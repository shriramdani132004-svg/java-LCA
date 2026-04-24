package com.project.servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class AddStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        double cgpa = Double.parseDouble(request.getParameter("cgpa"));
        String course = request.getParameter("course");

        try (Connection con = DBConnection.getConnection()) {

            // 🔴 CHECK DUPLICATE EMAIL FIRST
            PreparedStatement check = con.prepareStatement(
                "SELECT user_id FROM users WHERE email=?"
            );
            check.setString(1, email);
            ResultSet rsCheck = check.executeQuery();

            if (rsCheck.next()) {
                request.setAttribute("error", "Email already exists!");
                request.getRequestDispatcher("viewStudents").forward(request, response);
                return;
            }

            // 🔴 INSERT USER
            PreparedStatement userPs = con.prepareStatement(
                "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            userPs.setString(1, name);
            userPs.setString(2, email);
            userPs.setString(3, password);
            userPs.setString(4, "STUDENT");
            userPs.executeUpdate();

            ResultSet rs = userPs.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);

            // 🔴 INSERT STUDENT
            PreparedStatement studentPs = con.prepareStatement(
                "INSERT INTO students(user_id,course,cgpa) VALUES(?,?,?)"
            );
            studentPs.setInt(1, userId);
            studentPs.setString(2, course);
            studentPs.setDouble(3, cgpa);
            studentPs.executeUpdate();

            response.sendRedirect("viewStudents");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error adding student");
        }
    }
}