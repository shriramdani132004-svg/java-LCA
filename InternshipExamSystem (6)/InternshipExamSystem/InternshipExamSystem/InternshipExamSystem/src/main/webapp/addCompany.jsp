<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Company</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Add Company</h2>

<div class="container">

    <!-- 🔹 Navigation -->
    <div class="card">
        <a href="<%= request.getContextPath() %>/adminDashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>

    <!-- 🔹 Messages -->
    <%
        String error = (String) request.getAttribute("error");
        String success = (String) request.getAttribute("success");
    %>

    <% if (error != null) { %>
        <div class="card">
            <p style="color:#dc2626; font-weight:600;"><%= error %></p>
        </div>
    <% } %>

    <% if (success != null) { %>
        <div class="card">
            <p style="color:#16a34a; font-weight:600;"><%= success %></p>
        </div>
    <% } %>

    <!-- 🔹 Form -->
    <div class="card">

        <form action="<%= request.getContextPath() %>/addCompany" method="post">

            <label for="name">Company Name</label>
            <input type="text" id="name" name="name" placeholder="e.g. TCS, Infosys" required>

            <label for="location">Location</label>
            <input type="text" id="location" name="location" placeholder="e.g. Pune, Bangalore" required>

            <label for="cgpa">Eligibility CGPA</label>
            <input type="number" step="0.01" min="0" max="10" id="cgpa" name="cgpa" placeholder="e.g. 7.5" required>

            <input type="submit" value="Add Company">

        </form>

    </div>

</div>

</body>
</html>