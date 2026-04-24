package com.project.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.project.util.DBConnection;
import com.project.dao.AuditLogDAO;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT user_id, name, role FROM users WHERE email=? AND password=?")) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String role = rs.getString("role");

                HttpSession session = request.getSession(true);

                session.setAttribute("userId", userId);
                session.setAttribute("user", email);
                session.setAttribute("name", name);
                session.setAttribute("role", role);

                if ("STUDENT".equals(role)) {
                    PreparedStatement ps2 = con.prepareStatement(
                            "SELECT cgpa FROM students WHERE user_id=?");
                    ps2.setInt(1, userId);
                    ResultSet rs2 = ps2.executeQuery();

                    if (rs2.next()) {
                        session.setAttribute("cgpa", rs2.getDouble("cgpa"));
                    }
                }

                AuditLogDAO.addLog(
                    userId,
                    "LOGIN",
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent")
                );

                if ("ADMIN".equals(role)) {
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    response.sendRedirect("studentDashboard.jsp");
                }

            } else {
                response.getWriter().println("Invalid Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}