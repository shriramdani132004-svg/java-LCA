package com.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class UpdateApplicationStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔴 Validate session (important)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null ||
            !"ADMIN".equals(session.getAttribute("role"))) {

            response.sendRedirect("login.jsp");
            return;
        }

        try {

            // 🔴 Get parameters safely
            String idParam = request.getParameter("id");
            String status = request.getParameter("status");

            if (idParam == null || status == null) {
                response.sendRedirect("viewApplications");
                return;
            }

            int id = Integer.parseInt(idParam);

            // 🔴 Normalize status (avoid DB mismatch)
            status = status.trim().toUpperCase();

            // 🔴 Allow only valid values
            if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
                response.sendRedirect("viewApplications");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE applications SET status=? WHERE application_id=?"
                );

                ps.setString(1, status);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                // 🔴 Debug log (remove later if needed)
                System.out.println("Status Updated → ID: " + id + " | Status: " + status + " | Rows: " + rows);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔴 Always redirect properly with context path
        response.sendRedirect(request.getContextPath() + "/viewApplications");
    }
}