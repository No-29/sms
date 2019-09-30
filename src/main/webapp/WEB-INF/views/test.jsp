<%@page pageEncoding="UTF-8" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String photoPath = (String)request.getAttribute("photoPath");
    if(StringUtils.isEmpty(photoPath) || StringUtils.equalsIgnoreCase(photoPath,"/uploadFiles/")){
        photoPath = "/uploadFiles/1.jpg";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form id="uploadForm" action="/uploadServlet" method="post" enctype="multipart/form-data" style="text-align:center;">
        <div style="text-align:center;">
            <img src=<%=photoPath%> style="width:100px;height:100px;cursor:pointer;border-radius:50px;" onclick="select()">
        </div>
        <input type="file" id="fileToUpload" name="files" onchange="upload()" style="/*opacity: 0;*/display:none;">
        <input id="submitBtn" type="submit" value="上传" style="display: none;">
    </form>
</body>
<script>
    function select() {
        document.getElementById("fileToUpload").click();
    }
    
    function upload() {
        document.getElementById("submitBtn").click();
    }
</script>
</html>