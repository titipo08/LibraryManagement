<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Issue</title>
    <style>
        body{
            background-image: linear-gradient(rgba(0,0,0,0.3),rgba(0,0,0,0.3)),
                url("pic/librarypic.jpg");
            display:flex;
            justify-content:center;
            align-items:center;
            min-height:100vh;
            font-family:Arial;
        }
        .card{
            width:560px;
            background-color:rgba(222,184,135,0.5);
            padding:28px;
            text-align:center;
            border-radius:10px;
            backdrop-filter: blur(20px);
            box-shadow:0 10px 30px rgba(0,0,0,0.3);
        }
        h2{ margin:6px 0; }
        .label{ color:#5a3e28; font-size:13px; text-transform:uppercase; letter-spacing:1px; margin:12px 0 2px; }
        .value{ font-size:16px; font-weight:bold; color:#2c1a0e; }
        .divider{ border:none; border-top:1px solid rgba(0,0,0,0.15); margin:16px 0; }
        select, .btn-issue{
            padding:10px 16px;
            border-radius:6px;
            border:none;
            font-size:14px;
            margin:6px 4px;
            cursor:pointer;
        }
        select{ width:60%; background:#fff8f0; }
        .btn-issue{ background:#8B5E3C; color:white; }
        .btn-issue:hover{ background:#6F4E37; }
        .btn{
            display:inline-block;
            padding:12px 30px;
            background:#8B5E3C;
            color:white;
            text-decoration:none;
            border-radius:6px;
            margin-top:20px;
        }
        .btn:hover{ background:#6F4E37; }
        .slot-box{
            background:rgba(255,255,255,0.3);
            border-radius:8px;
            padding:12px;
            margin:10px 0;
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Book Issue</h2>
    <hr class="divider">

    <%
        String name         = (String) session.getAttribute("name");
        String book1Code    = (String) request.getAttribute("book1Code");
        String book1Issue   = (String) request.getAttribute("book1IssueDate");
        String book1Return  = (String) request.getAttribute("book1ReturnDate");
        String book2Code    = (String) request.getAttribute("book2Code");
        String book2Issue   = (String) request.getAttribute("book2IssueDate");
        String book2Return  = (String) request.getAttribute("book2ReturnDate");
        List<String[]> books = (List<String[]>) request.getAttribute("availableBooks");
    %>

    <p class="label">User</p>
    <p class="value"><%= name != null ? name : "N/A" %></p>

    <!-- SLOT 1 -->
    <div class="slot-box">
        <p class="label">Slot 1</p>
        <% if (book1Code != null) { %>
            <p class="value">📖 <%= book1Code %></p>
            <p>Issued: <%= book1Issue %> &nbsp;|&nbsp; Return by: <%= book1Return %></p>
        <% } else { %>
            <p class="value">Not Issued</p>
            <form action="BookIssueServlet" method="post">
                <input type="hidden" name="slot" value="1">
                <select name="bookCode" required>
                    <option value="">-- Select a Book --</option>
                    <% if (books != null) { for (String[] b : books) { %>
                        <option value="<%= b[0] %>"><%= b[0] %> — <%= b[1] %> by <%= b[2] %></option>
                    <% } } %>
                </select>
                <button type="submit" class="btn-issue">Issue</button>
            </form>
        <% } %>
    </div>

    <!-- SLOT 2 -->
    <div class="slot-box">
        <p class="label">Slot 2</p>
        <% if (book2Code != null) { %>
            <p class="value">📖 <%= book2Code %></p>
            <p>Issued: <%= book2Issue %> &nbsp;|&nbsp; Return by: <%= book2Return %></p>
        <% } else { %>
            <p class="value">Not Issued</p>
            <form action="BookIssueServlet" method="post">
                <input type="hidden" name="slot" value="2">
                <select name="bookCode" required>
                    <option value="">-- Select a Book --</option>
                    <% if (books != null) { for (String[] b : books) { %>
                        <option value="<%= b[0] %>"><%= b[0] %> — <%= b[1] %> by <%= b[2] %></option>
                    <% } } %>
                </select>
                <button type="submit" class="btn-issue">Issue</button>
            </form>
        <% } %>
    </div>

    <hr class="divider">
    <a href="section1.jsp" class="btn">Back</a>
</div>
</body>
</html>
