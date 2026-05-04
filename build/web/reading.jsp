<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reading Section</title>

    <style>
        body{
            background-image: linear-gradient(rgba(0,0,0,0.3),rgba(0,0,0,0.3)),
                url("pic/librarypic.jpg");
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            font-family:Arial;
        }

        .card{
            width:500px;
            background-color:rgba(222,184,135,0.5);
            padding:20px;
            text-align:center;
            border-radius:10px;
            backdrop-filter: blur(20px);
            box-shadow:0 10px 30px rgba(0,0,0,0.3);
        }

        .btn{
            display:inline-block;
            padding:12px 30px;
            background:#8B5E3C;
            color:white;
            text-decoration:none;
            border-radius:6px;
            margin-top:20px;
        }

        .btn:hover{
            background:#6F4E37;
        }
    </style>
</head>

<body>

<div class="card">

    <h1>You are logged in!</h1>

    <%
        // ✅ Get data from servlet
        String info = (String) request.getAttribute("info");
        if(info != null){
    %>
        <h2><%= info %></h2>
    <%
        }
    %>

    <h2>1 Hour Session Alloted</h2>

    <!-- ✅ Back to JSP (not HTML) -->
    <a href="section1.jsp" class="btn">Back</a>

</div>

</body>
</html>