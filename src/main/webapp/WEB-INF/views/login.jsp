<%--
  Created by IntelliJ IDEA.
  User: hjsoft
  Date: 2019/7/16
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <title>login page</title>
    <%--<link href="../statics/css/login_style.css" rel="stylesheet">--%>
</head>
<body>
    <h1>Clear login Form </h1>
    <div>
        <form action="/login/toHome" method="post">
            <div>
                <input type="text" name="username" placeholder="Enter your name " required="">
                <input type="password" name="password" placeholder="Enter your password " required="">
            </div>
            <div>
                <input type="submit" value="Login">
            </div>
        </form>
    </div>
</body>
</html>
