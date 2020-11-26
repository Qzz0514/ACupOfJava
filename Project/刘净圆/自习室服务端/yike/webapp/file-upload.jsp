<%--
  Created by IntelliJ IDEA.
  User: yuange
  Date: 2020/11/24
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/shop/demo" method="post" enctype="multipart/form-data" >
        <input type="file" name="upload" />
        <input type="submit" value="submit" />
    </form>
</body>
</html>
