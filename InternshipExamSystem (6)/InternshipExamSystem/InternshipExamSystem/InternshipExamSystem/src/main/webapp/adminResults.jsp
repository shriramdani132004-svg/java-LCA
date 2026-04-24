<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Results</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>

<body>

<h2>Student Results</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card">
        <a href="<%= request.getContextPath() %>/adminDashboard.jsp">⬅ Dashboard</a>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>

    <!-- Table -->
    <div class="card">

        <table>
            <tr>
                <th>Student</th>
                <th>Company</th>
                <th>Exam</th>
                <th>Correct</th>
                <th>Wrong</th>
                <th>Total</th>
                <th>Submitted Time</th>
            </tr>

            <%
            List<Map<String, Object>> list =
                (List<Map<String, Object>>) request.getAttribute("results");

            if (list != null && !list.isEmpty()) {

                for (Map<String, Object> r : list) {

                    int correct = (r.get("correct") == null) ? 0 : Integer.parseInt(r.get("correct").toString());
                    int wrong   = (r.get("wrong") == null) ? 0 : Integer.parseInt(r.get("wrong").toString());
                    int total   = correct + wrong;

                    String company = (r.get("company") != null) ? r.get("company").toString() : "-";
                    String time    = (r.get("time") != null) ? r.get("time").toString() : "-";
            %>

            <tr>
                <td><%= r.get("name") %></td>
                <td><%= company %></td>
                <td><%= r.get("exam") %></td>
                <td><%= correct %></td>
                <td><%= wrong %></td>
                <td><%= total %></td>
                <td><%= time %></td>
            </tr>

            <%
                }
            } else {
            %>

            <tr>
                <td colspan="7" style="text-align:center; color:#dc2626;">
                    No results available
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