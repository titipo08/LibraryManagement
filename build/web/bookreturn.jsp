<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Book Return</title>
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
                padding:28px;
                text-align:center;
                border-radius:10px;
                backdrop-filter: blur(20px);
                box-shadow:0 10px 30px rgba(0,0,0,0.3);
            }
            .label{
                color:#5a3e28;
                font-size:13px;
                text-transform:uppercase;
                letter-spacing:1px;
                margin:12px 0 2px;
            }
            .value{
                font-size:17px;
                font-weight:bold;
                color:#2c1a0e;
                margin:2px 0;
            }
            .slot-box{
                background:rgba(255,255,255,0.3);
                border-radius:8px;
                padding:12px;
                margin:10px 0;
            }
            .divider{ border:none; border-top:1px solid rgba(0,0,0,0.15); margin:14px 0; }
            .btn-return{
                padding:10px 24px;
                background:#8B5E3C;
                color:white;
                border:none;
                border-radius:6px;
                font-size:14px;
                cursor:pointer;
                margin-top:8px;
            }
            .btn-return:hover{ background:#6F4E37; }
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
        </style>
    </head>
    <body>
        <div class="card">
            <h2>Book Return</h2>
            <hr class="divider">

            <%
                String book1Code       = (String) request.getAttribute("book1Code");
                String book1ReturnDate = (String) request.getAttribute("book1ReturnDate");
                String book2Code       = (String) request.getAttribute("book2Code");
                String book2ReturnDate = (String) request.getAttribute("book2ReturnDate");
            %>

            <!-- SLOT 1 -->
            <div class="slot-box">
                <p class="label">Slot 1</p>
                <% if (book1Code != null) { %>
                    <p class="value">📖 <%= book1Code %></p>
                    <p>Return by: <strong><%= book1ReturnDate %></strong></p>
                    <form action="BookReturnServlet" method="post">
                        <input type="hidden" name="slot" value="1">
                        <button type="submit" class="btn-return">Return This Book</button>
                    </form>
                <% } else { %>
                    <p class="value">No Book Issued</p>
                <% } %>
            </div>

            <!-- SLOT 2 -->
            <div class="slot-box">
                <p class="label">Slot 2</p>
                <% if (book2Code != null) { %>
                    <p class="value">📖 <%= book2Code %></p>
                    <p>Return by: <strong><%= book2ReturnDate %></strong></p>
                    <form action="BookReturnServlet" method="post">
                        <input type="hidden" name="slot" value="2">
                        <button type="submit" class="btn-return">Return This Book</button>
                    </form>
                <% } else { %>
                    <p class="value">No Book Issued</p>
                <% } %>
            </div>

            <hr class="divider">
            <a href="section1.jsp" class="btn">Back</a>
        </div>
    </body>
</html>
