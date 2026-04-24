package com.project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.util.DBConnection;

/**
 * Servlet implementation class TestDBServlet
 */

public class TestDBServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestDBServlet() {
        super();
    }

    /**
     * Handles GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response type
        response.setContentType("text/html");

        // Get writer to print output
        PrintWriter out = response.getWriter();

        out.println("<h2>Testing Database Connection...</h2>");

        // Get DB connection
        Connection con = DBConnection.getConnection();

        // Check connection
        if (con != null) {
            out.println("<h3 style='color:green;'>Database Connected Successfully!</h3>");
        } else {
            out.println("<h3 style='color:red;'>Connection Failed!</h3>");
        }
    }

    /**
     * Handles POST request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}