<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-4-14
  Time: 下午4:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test Form</title>
</head>
<body>
<form name="serForm" action="springUpload.action" method="post"  enctype="multipart/form-data">
    <h1>采用流的方式上传文件</h1>
    <input type="file" name="myfile" />
    <label>
        <input type="text" name="username"/>
    </label>
    <input type="submit" value="upload"/>
</form>
</body>
</html>
