package com.project.dao;

import java.sql.*;
import java.util.*;

import com.project.util.DBConnection;

public class ReportDAO {

    public List<String[]> getStudentsPerCompany() {

        List<String[]> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT c.company_name, COUNT(a.student_id) AS total " +
                    "FROM applications a " +
                    "JOIN internships i ON a.internship_id=i.internship_id " +
                    "JOIN companies c ON i.company_id=c.company_id " +
                    "WHERE a.status='SELECTED' " +
                    "GROUP BY c.company_name";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("company_name"),
                        rs.getString("total")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}