package com.project.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class ViewResultsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String,Object>> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            // 🔴 FULL FIXED QUERY (NO DUPLICATES + CORRECT COMPANY + LATEST ATTEMPT)
            String query =
                "SELECT u.name, e.exam_name, " +
                "c.company_name, ea.correct, ea.wrong, " +
                "(ea.correct + ea.wrong) AS total, " +
                "DATE_FORMAT(ea.end_time, '%Y-%m-%d %H:%i:%s') AS submitted_time " +
                "FROM exam_attempts ea " +
                "JOIN users u ON ea.user_id=u.user_id " +
                "JOIN exams e ON ea.exam_id=e.exam_id " +
                "LEFT JOIN students s ON u.user_id=s.user_id " +
                "LEFT JOIN applications a ON a.application_id = ( " +
                    "SELECT MAX(application_id) FROM applications WHERE student_id=s.student_id" +
                ") " +
                "LEFT JOIN internships i ON a.internship_id=i.internship_id " +
                "LEFT JOIN companies c ON i.company_id=c.company_id " +
                "WHERE ea.end_time IS NOT NULL " +
                "AND ea.attempt_id = ( " +
                    "SELECT MAX(attempt_id) FROM exam_attempts " +
                    "WHERE user_id=ea.user_id AND exam_id=ea.exam_id" +
                ")";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Map<String,Object> row = new HashMap<>();

                row.put("name", rs.getString("name"));
                row.put("exam", rs.getString("exam_name"));
                row.put("company", rs.getString("company_name"));
                row.put("correct", rs.getInt("correct"));
                row.put("wrong", rs.getInt("wrong"));
                row.put("total", rs.getInt("total"));
                row.put("time", rs.getString("submitted_time"));

                list.add(row);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("results", list);
        request.getRequestDispatcher("viewResults.jsp").forward(request, response);
    }
}