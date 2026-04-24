package com.project.servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.project.util.DBConnection;

@MultipartConfig
public class UploadExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String examName = request.getParameter("examName");

        Part filePart = request.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("File not uploaded");
            return;
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(filePart.getInputStream()));

        int examId = 0;

        try (Connection con = DBConnection.getConnection()) {

            // ✅ INSERT EXAM
            PreparedStatement examPs = con.prepareStatement(
                "INSERT INTO exams (exam_name, duration) VALUES (?, 30)",
                Statement.RETURN_GENERATED_KEYS
            );

            examPs.setString(1, examName);
            examPs.executeUpdate();

            ResultSet rs = examPs.getGeneratedKeys();
            if (rs.next()) {
                examId = rs.getInt(1);
            }

            String line;
            String question = null;
            String[] options = new String[4];
            int optIndex = 0;
            String answer = null;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                // QUESTION
                if (line.matches("\\d+\\..*")) {
                    question = line.substring(line.indexOf(".") + 1).trim();
                    optIndex = 0;
                }

                // OPTIONS
                else if (line.startsWith("A)") || line.startsWith("B)") ||
                         line.startsWith("C)") || line.startsWith("D)")) {

                    if (optIndex < 4) {
                        options[optIndex++] = line.substring(2).trim();
                    }
                }

                // ANSWER
                else if (line.startsWith("Answer:")) {

                    answer = line.substring(line.indexOf(":") + 1).trim();

                    // INSERT QUESTION
                    PreparedStatement qps = con.prepareStatement(
                        "INSERT INTO questions (exam_id, question_text, type, marks) VALUES (?, ?, 'MCQ', 1)",
                        Statement.RETURN_GENERATED_KEYS
                    );

                    qps.setInt(1, examId);
                    qps.setString(2, question);
                    qps.executeUpdate();

                    ResultSet qrs = qps.getGeneratedKeys();
                    qrs.next();
                    int questionId = qrs.getInt(1);

                    // INSERT OPTIONS
                    for (int i = 0; i < 4; i++) {

                        PreparedStatement ops = con.prepareStatement(
                            "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)"
                        );

                        boolean isCorrect = (char)('A' + i) == answer.charAt(0);

                        ops.setInt(1, questionId);
                        ops.setString(2, options[i]);
                        ops.setBoolean(3, isCorrect);

                        ops.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
            return;
        }

        // ✅ SAFE REDIRECT
        response.sendRedirect("adminDashboard.jsp");
    }
}