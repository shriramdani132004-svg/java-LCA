<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Exam Submitted</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2 style="color:#16a34a;">Exam Submitted Successfully</h2>

<div class="container">

    <div class="card">
        <p>Your exam has been submitted successfully.</p>

        <a href="<%= request.getContextPath() %>/studentDashboard.jsp">
            Go to Dashboard
        </a>
    </div>

</div>

</body>
</html>