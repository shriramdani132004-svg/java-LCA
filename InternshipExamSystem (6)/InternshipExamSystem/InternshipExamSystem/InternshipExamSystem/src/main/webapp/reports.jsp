<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Reports</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Report: Students Selected Per Company</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card">
        <a href="<%= request.getContextPath() %>/adminDashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>

    <!-- Report Table -->
    <div class="card">

        <table>
            <tr>
                <th>Company</th>
                <th>Total Selected Students</th>
            </tr>

            <%
            List<String[]> list = (List<String[]>) request.getAttribute("report1");

            if (list != null && !list.isEmpty()) {
                for (String[] row : list) {
            %>

            <tr>
                <td><%= row[0] %></td>
                <td><%= row[1] %></td>
            </tr>

            <%
                }
            } else {
            %>

            <tr>
                <td colspan="2" style="text-align:center; color:#dc2626;">
                    No data available
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