<%@ page import="java.util.*, com.project.model.Internship" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();

Integer userId = (Integer) session.getAttribute("userId");
String name = (String) session.getAttribute("name");
Double cgpa = (Double) session.getAttribute("cgpa");

if (userId == null) {
    response.sendRedirect(ctx + "/login.jsp");
    return;
}

/* 🔴 READ ERROR FROM URL */
String err = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Internships</title>
    <link rel="stylesheet" href="<%= ctx %>/style.css">
</head>

<body>

<h2>Available Internships</h2>

<div class="container">

    <!-- Top Bar -->
    <div class="card">
        <b>Welcome, <%= name %></b>
        <% if (cgpa != null) { %>
            | CGPA: <%= cgpa %>
        <% } %>

        <div style="float:right;">
            <a href="<%= ctx %>/studentDashboard.jsp">Dashboard</a>
            <a href="<%= ctx %>/logout">Logout</a>
        </div>
    </div>

    <!-- Error Message -->
    <%
    if (err != null) {
        String message =
            "NotEligible".equals(err) ? "Not Eligible (CGPA too low)" :
            "AlreadyApplied".equals(err) ? "Already Applied" :
            "NoStudent".equals(err) ? "Student not found" :
            "ServerError".equals(err) ? "Server Error" :
            err;
    %>
        <div class="card">
            <p style="color:#dc2626;"><%= message %></p>
        </div>
    <%
    }
    %>

    <!-- Table -->
    <div class="card">

        <table>
            <tr>
                <th>Company</th>
                <th>Role</th>
                <th>Stipend</th>
                <th>Deadline</th>
                <th>Required CGPA</th>
                <th>Action</th>
            </tr>

            <%
            List<Internship> list =
                (List<Internship>) request.getAttribute("internships");

            if (list != null && !list.isEmpty()) {

                for (Internship i : list) {
            %>

            <tr>
                <td><%= i.getCompanyName() %></td>
                <td><%= i.getRole() %></td>
                <td><%= i.getStipend() %></td>
                <td><%= i.getDeadline() %></td>
                <td><%= i.getRequiredCgpa() %></td>

                <td>
                    <form action="<%= ctx %>/applyInternship" method="post">
                        <input type="hidden" name="internshipId" value="<%= i.getInternshipId() %>">
                        <button type="submit">Apply</button>
                    </form>
                </td>
            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="6" style="text-align:center; color:#dc2626;">
                    No internships available
                </td>
            </tr>

            <%
            }
            %>

        </table>

    </div>

</div>

</body>
</html>