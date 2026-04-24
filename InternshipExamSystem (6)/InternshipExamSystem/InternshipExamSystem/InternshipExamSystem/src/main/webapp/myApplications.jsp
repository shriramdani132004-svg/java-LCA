<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
String ctx = request.getContextPath();
String name = (String) session.getAttribute("name");
%>

<!DOCTYPE html>
<html>
<head>
<title>Your Applications</title>

<link rel="stylesheet" href="<%= ctx %>/style.css">

<style>
.status-approved { color:#16a34a; font-weight:bold; }
.status-rejected { color:#dc2626; font-weight:bold; }
.status-pending { color:#f59e0b; font-weight:bold; }

.cert-btn {
    display:inline-block;
    margin-top:6px;
    padding:6px 12px;
    background:#2563eb;
    color:white;
    border-radius:5px;
    text-decoration:none;
}
</style>

</head>

<body>

<h2>Your Applications</h2>

<div class="container">

<div class="card">
    <a href="studentDashboard.jsp">Dashboard</a>
    <a href="logout">Logout</a>
</div>

<div class="card">

<table>

<tr>
    <th>Company</th>
    <th>Role</th>
    <th>Status</th>
</tr>

<%
List<Map<String,Object>> list =
(List<Map<String,Object>>) request.getAttribute("applications");

if(list != null){

for(Map<String,Object> r : list){

String status = r.get("status").toString().trim();

String css =
status.equalsIgnoreCase("APPROVED") ? "status-approved" :
status.equalsIgnoreCase("REJECTED") ? "status-rejected" :
"status-pending";
%>

<tr>

<td><%= r.get("company") %></td>
<td><%= r.get("role") %></td>

<td class="<%= css %>">

    <%= status %>

    <% if(status.equalsIgnoreCase("APPROVED")) { %>

        <br>
        <a class="cert-btn"
           href="generateCertificate?company=<%= r.get("company") %>&student=<%= name %>">
           Generate Certificate
        </a>

    <% } %>

</td>

</tr>

<%
}
}
%>

</table>

</div>

</div>

</body>
</html>