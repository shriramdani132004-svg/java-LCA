<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Login</h2>

<div class="container">

    <!-- 🔹 Error Message -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="card">
            <p style="color:#dc2626;"><%= error %></p>
        </div>
    <%
        }
    %>

    <!-- 🔹 Login Form -->
    <div class="card">

        <form action="<%= request.getContextPath() %>/login" method="post">

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter password" required>

            <input type="submit" value="Login">

        </form>

    </div>

</div>

</body>
</html>