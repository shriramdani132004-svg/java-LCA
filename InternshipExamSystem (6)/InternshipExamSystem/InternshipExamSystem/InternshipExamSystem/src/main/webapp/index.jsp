<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Internship & Exam System</h2>

<div class="container">

    <div class="card">
        <h3>Welcome</h3>
        <p>This system allows students to apply for internships and take online exams.</p>
    </div>

    <div class="card">
        <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
    </div>

</div>

</body>
</html>