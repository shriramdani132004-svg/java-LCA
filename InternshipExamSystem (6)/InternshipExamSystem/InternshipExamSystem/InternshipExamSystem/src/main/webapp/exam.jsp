<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<title>Online Exam</title>

<style>
body { font-family: Arial; background:#f3f4f6; margin:0; }

.header {
    background:#1e3a8a;
    color:white;
    padding:10px;
    display:flex;
    justify-content:space-between;
}

.main { display:flex; padding:20px; }

.left {
    width:70%;
    background:white;
    padding:20px;
    border-radius:10px;
}

.right {
    width:30%;
    margin-left:20px;
    background:white;
    padding:15px;
    border-radius:10px;
}

.question-card { display:none; }

.question-card.active { display:block; }

.option {
    display:block;
    padding:10px;
    margin-bottom:8px;
    background:#f1f5f9;
    border-radius:5px;
    cursor:pointer;
}

.nav-grid {
    display:grid;
    grid-template-columns:repeat(5,1fr);
    gap:10px;
}

.nav-btn {
    padding:10px;
    background:#e5e7eb;
    text-align:center;
    border-radius:50%;
    cursor:pointer;
}

.nav-btn.answered {
    background:#22c55e;
    color:white;
}

button {
    padding:10px 15px;
    margin-top:10px;
    border:none;
    background:#2563eb;
    color:white;
    border-radius:5px;
    cursor:pointer;
}

.submit { background:#16a34a; }

</style>

</head>

<body>

<div class="header">
    <div>Online Test</div>
    <div>Time: <span id="timer"></span></div>
</div>

<form action="submitExam" method="post">

<div class="main">

<div class="left">

<%
List<Map<String,Object>> list =
(List<Map<String,Object>>) request.getAttribute("questions");

int index = 0;

if(list != null){
for(Map<String,Object> q : list){

int qid = Integer.parseInt(q.get("id").toString());
String question = q.get("question").toString();

List<Map<String,Object>> opts =
(List<Map<String,Object>>) q.get("options");
%>

<div class="question-card" id="q<%=index%>">

<p><b>Q<%=index+1%>. <%=question%></b></p>

<%
if(opts!=null){
for(Map<String,Object> o : opts){
%>

<label class="option">
<input type="radio" name="q_<%=qid%>" value="<%=o.get("id")%>" onchange="markAnswered(<%=index%>)">
<%=o.get("text")%>
</label>

<%
}}
%>

</div>

<%
index++;
}}
%>

<button type="button" onclick="prevQ()">Previous</button>
<button type="button" onclick="nextQ()">Save & Next</button>
<button type="submit" class="submit">Submit</button>

</div>

<div class="right">

<h3>Questions</h3>

<div class="nav-grid">
<%
for(int i=0;i<index;i++){
%>
<div class="nav-btn" id="nav<%=i%>" onclick="gotoQ(<%=i%>)">
<%=i+1%>
</div>
<%
}
%>
</div>

</div>

</div>

</form>

<script>

let current = 0;
let total = <%=index%>;

function showQ(i){
    document.querySelectorAll(".question-card").forEach(q=>q.classList.remove("active"));
    document.getElementById("q"+i).classList.add("active");
}

function nextQ(){
    if(current < total-1) current++;
    showQ(current);
}

function prevQ(){
    if(current > 0) current--;
    showQ(current);
}

function gotoQ(i){
    current = i;
    showQ(i);
}

function markAnswered(i){
    document.getElementById("nav"+i).classList.add("answered");
}

/* TIMER */
let time = 1800;

function updateTimer(){
    let m=Math.floor(time/60);
    let s=time%60;
    document.getElementById("timer").innerHTML=m+":"+(s<10?"0":"")+s;
    if(time<=0) document.forms[0].submit();
    time--;
}

setInterval(updateTimer,1000);

showQ(0);

</script>

</body>
</html>