package com.project.servlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.project.util.DBConnection;

public class StartExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try (Connection con = DBConnection.getConnection()) {

            // =========================
            // 1. GET LATEST EXAM
            // =========================
            PreparedStatement ps = con.prepareStatement(
                "SELECT exam_id FROM exams ORDER BY exam_id DESC LIMIT 1"
            );

            ResultSet rs = ps.executeQuery();

            int examId = 0;

            if (rs.next()) {
                examId = rs.getInt("exam_id");
            }

            if (examId == 0) {
                response.getWriter().println("No exam available");
                return;
            }

            session.setAttribute("examId", examId);

            // =========================
            // 2. CHECK EXISTING ATTEMPT
            // =========================
            PreparedStatement check = con.prepareStatement(
                "SELECT attempt_id FROM exam_attempts WHERE user_id=? AND exam_id=?"
            );
            check.setInt(1, userId);
            check.setInt(2, examId);

            ResultSet rsCheck = check.executeQuery();

            int attemptId = 0;

            if (rsCheck.next()) {
                attemptId = rsCheck.getInt("attempt_id");
            } else {

                // =========================
                // 3. CREATE NEW ATTEMPT
                // =========================
                PreparedStatement attempt = con.prepareStatement(
                    "INSERT INTO exam_attempts(user_id, exam_id, start_time, status) VALUES (?, ?, NOW(), 'IN_PROGRESS')",
                    Statement.RETURN_GENERATED_KEYS
                );

                attempt.setInt(1, userId);
                attempt.setInt(2, examId);
                attempt.executeUpdate();

                ResultSet gen = attempt.getGeneratedKeys();
                gen.next();
                attemptId = gen.getInt(1);

                // =========================
                // 4. CREATE EMPTY ANSWERS (🔥 CRITICAL FIX)
                // =========================
                PreparedStatement qps = con.prepareStatement(
                    "SELECT question_id FROM questions WHERE exam_id=?"
                );
                qps.setInt(1, examId);

                ResultSet qrs = qps.executeQuery();

                while (qrs.next()) {

                    PreparedStatement insertAns = con.prepareStatement(
                        "INSERT INTO answers(attempt_id, question_id) VALUES(?,?)"
                    );

                    insertAns.setInt(1, attemptId);
                    insertAns.setInt(2, qrs.getInt("question_id"));
                    insertAns.executeUpdate();
                }
            }
            System.out.println("Redirecting to: " + request.getContextPath() + "/viewExam");
            // =========================
            // 5. REDIRECT TO EXAM
            // =========================
            response.sendRedirect(request.getContextPath() + "/viewExam");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error starting exam");
        }
    }
}