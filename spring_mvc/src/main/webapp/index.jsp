<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
</head>
<body>
<h2>Login</h2>
<form action="login" method="get">
    <input type="text" required="required" placeholder="用户名"
           name="user" pattern="\w+"/>
    <input type="password" required="required" placeholder="密码" name="password" pattern="\S+"/>
    <button class="but" type="submit" style="cursor: pointer">登录</button>
</form>
<h1>Hello World!</h1>
</body>
</html>
