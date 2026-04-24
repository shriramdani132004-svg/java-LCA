<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();
String studentName = (String) session.getAttribute("name");
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
    font-size:14px;
}
</style>

</head>

<body>

<h2 style="text-align:center;">Your Applications</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card">
        <a href="<%= ctx %>/studentDashboard.jsp">⬅ Dashboard</a>
        <a href="<%= ctx %>/logout">Logout</a>
    </div>

    <!-- Table -->
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

            if(list != null && !list.isEmpty()){

                for(Map<String,Object> r : list){

                    String status = (r.get("status") != null)
                            ? r.get("status").toString().trim()
                            : "PENDING";

                    String display =
                        status.equalsIgnoreCase("APPROVED") ? "Approved" :
                        status.equalsIgnoreCase("REJECTED") ? "Rejected" :
                        "Pending";

                    String cssClass =
                        status.equalsIgnoreCase("APPROVED") ? "status-approved" :
                        status.equalsIgnoreCase("REJECTED") ? "status-rejected" :
                        "status-pending";
            %>

            <tr>
                <td><%= r.get("company") %></td>
                <td><%= r.get("role") %></td>

                <td class="<%= cssClass %>">

                    <%= display %>

                    <!-- ✅ GENERATE CERTIFICATE BUTTON -->
                    <% if(status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("SELECTED")) { %>

                        <br>
                        <a class="cert-btn"
                           href="<%= ctx %>/generateCertificate?company=<%= r.get("company") %>&student=<%= studentName %>">
                           Generate Certificate
                        </a>

                    <% } %>

                </td>
            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="3" style="text-align:center; color:#dc2626;">
                    No Applications Found
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