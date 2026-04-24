<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Error</h2>

<div class="container">

    <div class="card">
        <h3>Something went wrong</h3>
        <p>Please try again or contact admin.</p>
    </div>

    <div class="card">
        <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
        <a href="<%= request.getContextPath() %>/adminDashboard.jsp">Admin Dashboard</a>
        <a href="<%= request.getContextPath() %>/studentDashboard.jsp">Student Dashboard</a>
    </div>

</div>

</body>
</html>