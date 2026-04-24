package com.project.servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class SubmitExamServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        int examId = (int) session.getAttribute("examId");

        try (Connection con = DBConnection.getConnection()) {

            // 🔴 GET ATTEMPT
            PreparedStatement ps = con.prepareStatement(
                "SELECT attempt_id FROM exam_attempts WHERE user_id=? AND exam_id=?"
            );
            ps.setInt(1, userId);
            ps.setInt(2, examId);

            ResultSet rs = ps.executeQuery();
            rs.next();
            int attemptId = rs.getInt("attempt_id");

            // =====================================
            // ✅ SAVE ANSWERS (MAIN FIX)
            // =====================================
            String[] qids = request.getParameterValues("questionId");

            if (qids != null) {

                for (String qid : qids) {

                    String selected = request.getParameter("q_" + qid);

                    PreparedStatement save = con.prepareStatement(
                        "UPDATE answers SET selected_option=? WHERE attempt_id=? AND question_id=?"
                    );

                    if (selected != null) {
                        save.setInt(1, Integer.parseInt(selected));
                    } else {
                        save.setNull(1, Types.INTEGER);
                    }

                    save.setInt(2, attemptId);
                    save.setInt(3, Integer.parseInt(qid));
                    save.executeUpdate();
                }
            }

            // =====================================
            // ✅ CALCULATE CORRECTLY
            // =====================================
            PreparedStatement calc = con.prepareStatement(
            	    "SELECT " +
            	    "SUM(CASE WHEN o.is_correct=1 THEN 1 ELSE 0 END) AS correct, " +
            	    "COUNT(q.question_id) - SUM(CASE WHEN o.is_correct=1 THEN 1 ELSE 0 END) AS wrong " +
            	    "FROM questions q " +
            	    "LEFT JOIN answers a ON q.question_id=a.question_id AND a.attempt_id=? " +
            	    "LEFT JOIN options o ON a.selected_option=o.option_id " +
            	    "WHERE q.exam_id=?"
            	);

            calc.setInt(1, attemptId);
            calc.setInt(2, examId);

            ResultSet r = calc.executeQuery();

            int correct = 0, wrong = 0;

            if (r.next()) {
                correct = r.getInt("correct");
                wrong = r.getInt("wrong");
            }

            // =====================================
            // ✅ UPDATE ATTEMPT
            // =====================================
            PreparedStatement update = con.prepareStatement(
                "UPDATE exam_attempts SET correct=?, wrong=?, end_time=NOW(), status='COMPLETED' WHERE attempt_id=?"
            );

            update.setInt(1, correct);
            update.setInt(2, wrong);
            update.setInt(3, attemptId);
            update.executeUpdate();

            response.sendRedirect("examSubmitted.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}