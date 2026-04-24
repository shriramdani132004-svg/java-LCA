package com.project.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/internship_exam_system";
    private static final String USER = "root";     // your MySQL username
    private static final String PASSWORD = "SHRIRAM@shriram"; // your MySQL password

    public static Connection getConnection() {
        Connection con = null;

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}