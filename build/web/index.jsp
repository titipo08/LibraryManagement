<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library</title>
    <style>
        body{
            background-image: linear-gradient(rgba(0,0,0,0.3),rgba(0,0,0,0.3)),url("pic/librarypic.jpg");
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            font-family:Arial;
        }
        #toggle{ display:none; }
        .place-card{
            width:300px;
            background-color:rgb(222,184,135,0.5);
            backdrop-filter:blur(20px);
            box-shadow:0 10px 30px rgba(0,0,0,0.3);
            border-radius:10px;
            text-align:center;
            padding:20px;
        }
        .btn:hover{
            transform:scale(1.1);
            background:#6F4E37;
        }
        #loginForm{ display:none; }
        #toggle:checked ~ #registerForm{ display:none; }
        #toggle:checked ~ #loginForm{ display:block; }
        .switch{
            color:blue;
            cursor:pointer;
            text-decoration:underline;
        }
    </style>
</head>
<body>
<%
    // ✅ Read code from SESSION (set by loginservelet)
    String code = (String) session.getAttribute("code");
    // ✅ Read error from REQUEST ATTRIBUTE (set by signinservelet forward)
    String error = (String) request.getAttribute("error");
    // Also check URL param as fallback
    if (error == null) error = request.getParameter("error");
%>
<input type="checkbox" id="toggle"
<%= (code != null || error != null) ? "checked" : "" %> >

<!-- REGISTER FORM (shown first - fill name + code to sign in) -->
<form action="signinservelet" method="post" id="registerForm">
    <div class="place-card">
        <h2>WELCOME TO LIBRARY</h2>
        <p>Name: <input type="text" name="name" required></p>
        <p>Unique code: <input type="text" name="code"></p>
        <% if (error != null) { %>
            <p style="color:red;">Invalid user name or code!</p>
        <% } %>
        <input type="submit" value="submit" class="btn">
        <br><br>
        <label for="toggle" class="switch">login</label>
    </div>
</form>

<!-- LOGIN FORM (shown after toggle - register new user) -->
<form action="loginservelet" method="post" id="loginForm">
    <div class="place-card">
        <h2>WELCOME TO LIBRARY</h2>
        <p>Name: <input type="text" name="name" required></p>
        <% if (code != null) { %>
            <p style="color:black;">Your Unique Code is: <strong><%= code %></strong></p>
        <% } %>
        <input type="submit" value="login" class="btn">
        <br><br>
        <label for="toggle" class="switch">back</label>
    </div>
</form>
</body>
</html>
