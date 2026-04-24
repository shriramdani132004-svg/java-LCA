<%@ page contentType="text/html; charset=UTF-8" %>

<%
String student = (String) request.getAttribute("studentName");
String company = (String) request.getAttribute("company");
%>

<!DOCTYPE html>
<html>
<head>
<title>Certificate</title>

<style>

body {
    margin: 0;
    background: #e5e7eb;
    font-family: 'Georgia', serif;
}

/* MAIN CERTIFICATE */
.certificate {
    width: 1000px;
    height: 650px;
    margin: 40px auto;
    background: white;
    position: relative;
    overflow: hidden;
    box-shadow: 0 10px 25px rgba(0,0,0,0.2);
}

/* TOP CURVE */
.certificate::before {
    content: "";
    position: absolute;
    width: 600px;
    height: 600px;
    background: linear-gradient(135deg, #1e3a8a, #0ea5e9);
    border-radius: 50%;
    top: -300px;
    left: -200px;
}

/* BOTTOM CURVE */
.certificate::after {
    content: "";
    position: absolute;
    width: 600px;
    height: 600px;
    background: linear-gradient(135deg, #0ea5e9, #1e3a8a);
    border-radius: 50%;
    bottom: -300px;
    right: -200px;
}

/* HEADER */
.header {
    text-align: center;
    margin-top: 40px;
    position: relative;
}

.header h2 {
    margin: 0;
    font-size: 26px;
    letter-spacing: 1px;
}

/* MAIN TEXT */
.title {
    text-align: center;
    margin-top: 40px;
    font-size: 22px;
}

.sub {
    text-align: center;
    font-size: 16px;
    margin-top: 10px;
}

/* STUDENT NAME */
.name {
    text-align: center;
    margin-top: 40px;
    font-size: 36px;
    font-weight: bold;
}

/* COMPANY NAME */
.company {
    text-align: center;
    margin-top: 10px;
    font-size: 28px;
    color: #0ea5e9;
    font-weight: bold;
}

/* FOOT BADGE */
.badge {
    width: 250px;
    margin: 40px auto;
    background: #1e3a8a;
    color: white;
    padding: 10px;
    border-radius: 20px;
    text-align: center;
}

/* BUTTON */
.actions {
    text-align: center;
}

button {
    padding: 10px 20px;
    background: #2563eb;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
}

/* PRINT */
@media print {
    button { display: none; }
    body { background: white; }
}

</style>
</head>

<body>

<div class="certificate">

    <div class="header">
        <h2>SOB PLACEMENT CELL</h2>
        <h2>CAMPUS PLACEMENT 2025</h2>
    </div>

    <div class="title">
        <i>Heartiest Congratulations</i>
    </div>

    <div class="sub">
        For getting placed in
    </div>

    <div class="name">
        <%= student %>
    </div>

    <div class="company">
        <%= company %>
    </div>

    <div class="badge">
        8 STUDENTS FROM SOB
    </div>

</div>

<div class="actions">
    <button onclick="window.print()">Download / Print</button>
</div>

</body>
</html>