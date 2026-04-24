<%@ page import="java.util.*, com.project.model.ApplicationLog" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Application Logs</title>
    <link rel="stylesheet" href="<%= ctx %>/style.css">
</head>

<body>

<h2>Application Logs</h2>

<div class="container">

    <div class="card">
        <a href="<%= ctx %>/adminDashboard.jsp">Dashboard</a>
        <a href="<%= ctx %>/logout">Logout</a>
    </div>

    <div class="card">

        <table>
            <tr>
                <th>Action</th>
                <th>User</th>
                <th>Time</th>
            </tr>

            <%
            List<ApplicationLog> logs =
                (List<ApplicationLog>) request.getAttribute("logs");

            if (logs != null && !logs.isEmpty()) {

                for (ApplicationLog log : logs) {
            %>

            <tr>
                <td><%= log.getAction() %></td>
                <td><%= log.getUserEmail() %></td>
                <td><%= log.getTimestamp() %></td>
            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="3" style="text-align:center; color:#dc2626;">
                    No logs available
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