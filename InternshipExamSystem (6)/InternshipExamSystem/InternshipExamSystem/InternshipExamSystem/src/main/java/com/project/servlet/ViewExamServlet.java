package com.project.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;   // 🔴 ADD THIS
import javax.servlet.http.*;

import com.project.util.DBConnection;


public class ViewExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 🔴 FIX: get examId from session safely
        Object examObj = session.getAttribute("examId");

        if (examObj == null) {
            response.getWriter().println("Exam not started properly");
            return;
        }

        int examId = Integer.parseInt(examObj.toString());

        List<Map<String, Object>> questions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "SELECT q.question_id, q.question_text, o.option_id, o.option_text " +
                "FROM questions q " +
                "JOIN options o ON q.question_id = o.question_id " +
                "WHERE q.exam_id=? ORDER BY q.question_id"
            );

            ps.setInt(1, examId);

            ResultSet rs = ps.executeQuery();

            Map<Integer, Map<String, Object>> map = new LinkedHashMap<>();

            while (rs.next()) {

                int qid = rs.getInt("question_id");

                if (!map.containsKey(qid)) {
                    Map<String, Object> q = new HashMap<>();
                    q.put("question", rs.getString("question_text"));
                    q.put("options", new ArrayList<Map<String, Object>>());
                    map.put(qid, q);
                }

                Map<String, Object> opt = new HashMap<>();
                opt.put("id", rs.getInt("option_id"));
                opt.put("text", rs.getString("option_text"));

                ((List<Map<String, Object>>) map.get(qid).get("options")).add(opt);
            }

            for (Map.Entry<Integer, Map<String, Object>> entry : map.entrySet()) {
                Map<String, Object> q = entry.getValue();
                q.put("id", entry.getKey());
                questions.add(q);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("questions", questions);

        request.getRequestDispatcher("exam.jsp").forward(request, response);
    }
}