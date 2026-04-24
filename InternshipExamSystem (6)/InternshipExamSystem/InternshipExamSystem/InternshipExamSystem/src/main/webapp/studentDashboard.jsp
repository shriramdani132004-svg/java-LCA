<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String role = (String) session.getAttribute("role");

if (role == null || !role.equals("STUDENT")) {
    response.sendRedirect("login.jsp");
    return;
}

String name = (String) session.getAttribute("name");
Double cgpa = (Double) session.getAttribute("cgpa");
String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<title>Student Dashboard</title>
<link rel="stylesheet" href="<%= ctx %>/style.css">
</head>

<body>

<h2>Student Dashboard</h2>

<div class="container">

    <!-- Profile -->
    <div class="card">
        <h3>Welcome, <%= name %></h3>
        <p>CGPA: <%= (cgpa != null ? cgpa : "Not Available") %></p>
    </div>

    <!-- Actions -->
    <div class="card">

        <a href="<%= ctx %>/viewInternships">View Internships</a>

        <a href="<%= ctx %>/studentResults">Results</a>

        <a href="<%= ctx %>/logout">Logout</a>
		<a href="viewMyApplications">My Applications</a>
    </div>

</div>

</body>
</html>