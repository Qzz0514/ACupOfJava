
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/Login.css">
</head>
<body>
<div id="login">
    <h1>Login</h1>
    <form action="${pageContext.request.contextPath}/Web/login" method="post">
        <div class="input">
            <input type="text" id="name" required="required" placeholder="Input your username" name="email">
        </div>
        <div class="input">
            <input type="password" id="pwd" required="required" placeholder="Input your password" name="password">
        </div>
        <div class="btn login-btn">
            <input type="submit" value="登录">
        </div>
    </form>
</div>
</body>
</html>
