package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.project.util.DBConnection;
import com.project.model.ApplicationLog;

public class ApplicationLogDAO {

    // 🔹 INSERT LOG
    public void addLog(String action, String email) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO application_logs(action, user_email) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, action);
            ps.setString(2, email);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 FETCH ALL LOGS (IMPORTANT FIX)
    public List<ApplicationLog> getAllLogs() {

        List<ApplicationLog> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM application_logs ORDER BY timestamp DESC";
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ApplicationLog log = new ApplicationLog();

                log.setAction(rs.getString("action"));
                log.setUserEmail(rs.getString("user_email"));
                log.setTimestamp(rs.getString("timestamp"));

                list.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}