<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Display Info</title>
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
            .label{ color:#5a3e28; font-size:13px; margin:10px 0 2px; text-transform:uppercase; letter-spacing:1px; }
            .value{ font-size:17px; font-weight:bold; margin:2px 0; color:#2c1a0e; }
            .divider{ border:none; border-top:1px solid rgba(0,0,0,0.15); margin:14px 0; }
            .btn{ display:inline-block; padding:12px 30px; background:#8B5E3C; color:white; text-decoration:none; border-radius:6px; margin-top:20px; }
            .btn:hover{ background:#6F4E37; }
        </style>
    </head>
    <body>
        <div class="card">
            <h2>User Info</h2>
            <hr class="divider">

            <p class="label">User Name</p>
            <p class="value">${not empty userName ? userName : "N/A"}</p>

            <p class="label">Last Reading Time</p>
            <p class="value">${not empty readingTime ? readingTime : "N/A"}</p>

            <hr class="divider">

            <p class="label">Book Slot 1</p>
            <p class="value">${not empty book1Code ? book1Code : "Not Issued"}</p>
            <c:if test="${not empty book1Code}">
                <p class="label">Return Date</p>
                <p class="value">${book1Return}</p>
            </c:if>

            <p class="label">Book Slot 2</p>
            <p class="value">${not empty book2Code ? book2Code : "Not Issued"}</p>
            <c:if test="${not empty book2Code}">
                <p class="label">Return Date</p>
                <p class="value">${book2Return}</p>
            </c:if>

            <hr class="divider">

            <p class="label">Penalty</p>
            <p class="value">${not empty penalty ? penalty : "None"}</p>

            <a href="section1.jsp" class="btn">Back</a>
        </div>
    </body>
</html>
