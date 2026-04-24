<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
String ctx = request.getContextPath();
Integer internshipId = (Integer) session.getAttribute("internshipId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Application Success</title>
<link rel="stylesheet" href="<%= ctx %>/style.css">
</head>

<body>

<h2 style="color:#16a34a;">Application Submitted Successfully</h2>

<div class="container">

    <div class="card">

        <p>Your application has been submitted successfully.</p>

        <!-- 🔴 FIX: FORCE SERVLET CALL -->
        <form action="<%= ctx %>/startExam" method="get">
            <button type="submit">Start Exam</button>
        </form>

        <br>

        <% if (internshipId != null) { %>
            <a href="<%= ctx %>/removeApplication?internshipId=<%= internshipId %>">
                Revoke Application
            </a>
        <% } %>

        <br><br>

        <a href="<%= ctx %>/studentDashboard.jsp">Back to Dashboard</a>

    </div>

</div>

</body>
</html>