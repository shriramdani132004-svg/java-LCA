package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.project.util.DBConnection;

public class AuditLogDAO {

    public static void addLog(int userId, String action, String ip, String agent) {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO audit_logs(user_id, action, timestamp, ip_address, user_agent) VALUES (?, ?, NOW(), ?, ?)"
            );

            ps.setInt(1, userId);
            ps.setString(2, action);
            ps.setString(3, ip);
            ps.setString(4, agent);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}