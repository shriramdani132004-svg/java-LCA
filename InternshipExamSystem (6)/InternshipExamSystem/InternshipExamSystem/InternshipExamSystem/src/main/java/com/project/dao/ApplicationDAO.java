package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.project.model.Application;
import com.project.util.DBConnection;

public class ApplicationDAO {

    public List<Application> getAllApplications() {

        List<Application> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT a.application_id, u.name, c.company_name, i.role, a.status " +
                           "FROM applications a " +
                           "JOIN students s ON a.student_id = s.student_id " +
                           "JOIN users u ON s.user_id = u.user_id " +
                           "JOIN internships i ON a.internship_id = i.internship_id " +
                           "JOIN companies c ON i.company_id = c.company_id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Application app = new Application();

                app.setApplicationId(rs.getInt("application_id"));
                app.setStudentName(rs.getString("name"));
                app.setCompanyName(rs.getString("company_name"));
                app.setRole(rs.getString("role"));
                app.setStatus(rs.getString("status"));

                list.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStatus(int applicationId, String status) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE applications SET status=? WHERE application_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, status);
            ps.setInt(2, applicationId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}