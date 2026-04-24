<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Management</title>
    <link rel="stylesheet" href="<%= ctx %>/style.css">
</head>

<body>

<h2>Student Management</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card">
        <a href="<%= ctx %>/adminDashboard.jsp">Dashboard</a>
        <a href="<%= ctx %>/logout">Logout</a>
    </div>

    <!-- Error -->
    <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
    %>
        <div class="card">
            <p style="color:#dc2626; font-weight:600;"><%= error %></p>
        </div>
    <%
    }
    %>

    <!-- Table -->
    <div class="card">

        <table>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>CGPA</th>
                <th>Course</th>
                <th>Action</th>
            </tr>

            <%
            List<Map<String,Object>> list =
                (List<Map<String,Object>>) request.getAttribute("students");

            if (list != null && !list.isEmpty()) {

                for (Map<String,Object> s : list) {
            %>

            <tr>
                <td><%= s.get("name") %></td>
                <td><%= s.get("email") %></td>
                <td><%= s.get("cgpa") %></td>
                <td><%= s.get("course") %></td>
                <td>
                    <a href="<%= ctx %>/deleteStudent?id=<%= s.get("id") %>">Delete</a>
                </td>
            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="5" style="text-align:center; color:#dc2626;">
                    No students found
                </td>
            </tr>

            <%
            }
            %>

        </table>

    </div>

    <!-- Add Student -->
    <div class="card">

        <h3>Add Student</h3>

        <form action="<%= ctx %>/addStudent" method="post">

            <input type="text" name="name" placeholder="Name" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="text" name="password" placeholder="Password" required>
            <input type="number" step="0.01" name="cgpa" placeholder="CGPA" required>
            <input type="text" name="course" placeholder="Course" required>

            <input type="submit" value="Add Student">

        </form>

    </div>

</div>

</body>
</html>