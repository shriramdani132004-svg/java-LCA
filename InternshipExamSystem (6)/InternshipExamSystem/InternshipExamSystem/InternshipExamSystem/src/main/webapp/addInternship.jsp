<%@ page import="java.sql.*, com.project.util.DBConnection" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Internship</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Add Internship</h2>

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

        <form action="<%= request.getContextPath() %>/addInternship" method="post">

            <label for="company">Select Company</label>
            <select id="company" name="companyId" required>

                <option value="">-- Select Company --</option>

                <%
                try (Connection con = DBConnection.getConnection();
                     Statement st = con.createStatement();
                     ResultSet rs = st.executeQuery("SELECT company_id, company_name FROM companies")) {

                    while (rs.next()) {
                %>
                    <option value="<%= rs.getInt("company_id") %>">
                        <%= rs.getString("company_name") %>
                    </option>
                <%
                    }

                } catch (Exception e) {
                %>
                    <option disabled>Error loading companies</option>
                <%
                    e.printStackTrace();
                }
                %>

            </select>

            <label for="role">Role</label>
            <input type="text" id="role" name="role"
                   placeholder="e.g. Java Developer Intern" required>

            <label for="stipend">Stipend (₹)</label>
            <input type="number" id="stipend" name="stipend"
                   placeholder="e.g. 15000" min="0" required>

            <label for="deadline">Application Deadline</label>
            <input type="date" id="deadline" name="deadline" required>

            <input type="submit" value="Add Internship">

        </form>

    </div>

</div>

</body>
</html>