<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String role = (String) session.getAttribute("role");
String name = (String) session.getAttribute("name");

if (role == null || !role.equals("ADMIN")) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Admin Dashboard</h2>

<div class="container">

    <!-- 🔹 Welcome -->
    <div class="card">
        <h3>Welcome, <%= name %></h3>
        <p>Manage internships, exams, and student activities efficiently.</p>
    </div>

    <!-- 🔹 Main Actions -->
    <div class="card">

        <!-- 🔥 Primary Actions -->
        <h3>Management</h3>

        <a href="<%= request.getContextPath() %>/viewApplications">📄 View Applications</a>
        <a href="<%= request.getContextPath() %>/viewResults">📊 Exam Results</a>
        <a href="<%= request.getContextPath() %>/viewStudents">👨‍🎓 Students</a>
        <a href="<%= request.getContextPath() %>/viewLogs">📜 Logs</a>

        <hr>

        <!-- 🔥 Setup Actions -->
        <h3>Setup</h3>

        <a href="<%= request.getContextPath() %>/addCompany.jsp">🏢 Add Company</a>
        <a href="<%= request.getContextPath() %>/addInternship.jsp">💼 Add Internship</a>
        <a href="<%= request.getContextPath() %>/createExam.jsp">📝 Create Exam</a>

        <hr>

        <!-- 🔥 Reports -->
        <h3>Analytics</h3>

        <a href="<%= request.getContextPath() %>/reports">📈 Reports</a>

        <hr>

        <!-- 🔥 Logout -->
        <a href="<%= request.getContextPath() %>/logout">🚪 Logout</a>

    </div>

</div>

</body>
</html>