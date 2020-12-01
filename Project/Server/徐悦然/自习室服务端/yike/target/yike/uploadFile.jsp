<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/23
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/updateHead" method="post" enctype="multipart/form-data">
        名称<input type="text" name="email"><br/>
        文件1<input type="file" name="file"><br/>
        <input type="submit" value="提交">
    </form>
</body>
</html>
