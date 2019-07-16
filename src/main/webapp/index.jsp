<%@ page autoFlush="true" pageEncoding="utf-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath();
    out.println(basePath);
%>
<html>
<body>
<h4>正在加载...</h4>
<%
    //request.getRequestDispatcher("/views/login.jsp").forward(request,response);
    response.sendRedirect(basePath + "/login/toLogin");
%>
</body>
</html>
