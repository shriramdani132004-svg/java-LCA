<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Exam</title>

    <!-- Use context path only for CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">

    <style>
        body {
            background: #f4f6f9;
            font-family: Arial, sans-serif;
        }

        h2 {
            text-align: center;
            margin-top: 20px;
        }

        .container {
            max-width: 700px;
            margin: auto;
        }

        .card {
            background: #fff;
            padding: 20px;
            margin-top: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }

        input[type="text"],
        input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        input[type="submit"] {
            margin-top: 15px;
            padding: 10px;
            width: 100%;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background: #1d4ed8;
        }

        .nav a {
            margin-right: 10px;
            padding: 8px 12px;
            background: #374151;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .nav a:hover {
            background: #111827;
        }

        pre {
            background: #f1f5f9;
            padding: 10px;
            border-radius: 6px;
        }
    </style>
</head>

<body>

<h2>Create Exam</h2>

<div class="container">

    <!-- Navigation -->
    <div class="card nav">
        <a href="adminDashboard.jsp">Dashboard</a>
        <a href="logout">Logout</a>
    </div>

    <!-- Form -->
    <div class="card">

        <!-- IMPORTANT: remove contextPath here -->
        <form action="uploadExam" method="post" enctype="multipart/form-data">

            <label for="examName">Exam Name</label>
            <input type="text" id="examName" name="examName"
                   placeholder="e.g. OOP Test 1" required>

            <label for="file">Upload Questions File (.txt)</label>
            <input type="file" id="file" name="file" accept=".txt" required>

            <input type="submit" value="Create Exam">

        </form>

    </div>

    <!-- Format Guide -->
    <div class="card">
        <h4>File Format (Example)</h4>
<pre>
1. What is OOP?
A) Object-Oriented Programming
B) Other
C) Other
D) Other
Answer: A
</pre>
    </div>

</div>

</body>
</html>