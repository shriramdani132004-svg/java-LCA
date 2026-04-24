package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.project.model.Internship;
import com.project.util.DBConnection;

public class InternshipDAO {

    // 🔹 METHOD 1: Get ALL internships (no filtering)
    public List<Internship> getAllInternships() {

        List<Internship> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query =
            	    "SELECT i.internship_id, i.role, i.stipend, i.deadline, " +
            	    "c.company_name, c.eligibility_cgpa " +
            	    "FROM internships i " +
            	    "JOIN companies c ON i.company_id = c.company_id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Internship i = new Internship();

                i.setInternshipId(rs.getInt("internship_id"));
                i.setRole(rs.getString("role"));
                i.setStipend(rs.getDouble("stipend"));
                i.setDeadline(rs.getString("deadline"));
                i.setCompanyName(rs.getString("company_name"));
                i.setRequiredCgpa(rs.getDouble("eligibility_cgpa"));
                
                list.add(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 METHOD 2: Get internships based on student CGPA
    public List<Internship> getInternshipsByStudent(String email) {

        List<Internship> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query =
                "SELECT i.internship_id, i.role, i.stipend, i.deadline, c.company_name " +
                "FROM internships i " +
                "JOIN companies c ON i.company_id = c.company_id " +
                "JOIN students s ON s.cgpa >= c.eligibility_cgpa " +
                "JOIN users u ON s.user_id = u.user_id " +
                "WHERE u.email=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Internship i = new Internship();

                i.setInternshipId(rs.getInt("internship_id"));
                i.setRole(rs.getString("role"));
                i.setStipend(rs.getDouble("stipend"));
                i.setDeadline(rs.getString("deadline"));
                i.setCompanyName(rs.getString("company_name"));

                list.add(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}