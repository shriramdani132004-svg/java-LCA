<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Applications</title>
    <link rel="stylesheet" href="<%= ctx %>/style.css">

    <style>
        .status-approved { color: #16a34a; font-weight: bold; }
        .status-rejected { color: #dc2626; font-weight: bold; }
        .status-pending { color: #f59e0b; font-weight: bold; }

        .btn {
            padding: 6px 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            color: white;
        }

        .approve { background: #16a34a; }
        .reject { background: #dc2626; }
        .cert { background: #2563eb; margin-top:5px; display:inline-block; }

        table { width:100%; border-collapse: collapse; }
        th { background:#111827; color:white; padding:10px; }
        td { padding:10px; border-bottom:1px solid #ddd; }
    </style>
</head>

<body>

<h2 style="text-align:center;">Applications</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card">
        <a href="<%= ctx %>/adminDashboard.jsp">⬅ Dashboard</a>
        <a href="<%= ctx %>/logout">Logout</a>
    </div>

    <!-- Table -->
    <div class="card">

        <table>
            <tr>
                <th>Name</th>
                <th>Company</th>
                <th>Role</th>
                <th>Status</th>
                <th>Action</th>
            </tr>

            <%
            List<Map<String,Object>> list =
                (List<Map<String,Object>>) request.getAttribute("applications");

            if (list != null && !list.isEmpty()) {

                for (Map<String,Object> r : list) {

                    String status = (r.get("status") != null)
                            ? r.get("status").toString()
                            : "PENDING";

                    String cssClass =
                        status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("SELECTED") ? "status-approved" :
                        status.equalsIgnoreCase("REJECTED") ? "status-rejected" :
                        "status-pending";
            %>

            <tr>
                <td><%= r.get("name") %></td>
                <td><%= r.get("company") %></td>
                <td><%= r.get("role") %></td>

                <td class="<%= cssClass %>">
                    <%= status %>
                </td>

                <td>

                    <!-- APPROVE / REJECT -->
                    <form action="<%= ctx %>/updateStatus" method="post" style="display:inline;">

                        <input type="hidden" name="id" value="<%= r.get("id") %>">

                        <button class="btn approve" type="submit" name="status" value="APPROVED">
                            Approve
                        </button>

                        <button class="btn reject" type="submit" name="status" value="REJECTED">
                            Reject
                        </button>

                    </form>

                    <!-- CERTIFICATE BUTTON -->
                    <%
                    if ("APPROVED".equalsIgnoreCase(status) || "SELECTED".equalsIgnoreCase(status)) {
                    %>

                        <br>
                        <a class="btn cert"
                           href="<%= ctx %>/generateCertificate?company=<%= r.get("company") %>&student=<%= r.get("name") %>">
                           Generate Certificate
                        </a>

                    <%
                    }
                    %>

                </td>
            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="5" style="text-align:center; color:#dc2626;">
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