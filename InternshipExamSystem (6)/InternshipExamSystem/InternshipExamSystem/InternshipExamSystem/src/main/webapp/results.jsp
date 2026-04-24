<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>My Applications</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>My Applications</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card">
        <a href="<%= request.getContextPath() %>/studentDashboard.jsp">Dashboard</a>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>

    <!-- Applications Table -->
    <div class="card">

        <table>
            <tr>
                <th>Company</th>
                <th>Role</th>
                <th>Status</th>
            </tr>

            <%
            List<Map<String,Object>> list =
                (List<Map<String,Object>>) request.getAttribute("results");

            if (list != null && !list.isEmpty()) {

                for (Map<String,Object> r : list) {

                    String status = (r.get("status") != null) ? r.get("status").toString() : "PENDING";
            %>

            <tr>
                <td><%= r.get("company") %></td>
                <td><%= r.get("role") %></td>
                <td class="status-<%= status.toLowerCase() %>">
                    <%= status %>
                </td>
            </tr>

            <%
                }
            } else {
            %>

            <tr>
                <td colspan="3" style="text-align:center; color:#dc2626;">
                    No applications found
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