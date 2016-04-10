<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel='stylesheet'>
  <link href="style/login-style.css" rel='stylesheet'>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper">
  <div class="front">
    <div class="container">
      <div class="banner">
        <form action="loginServlet" method="post" class="login">
          <input type="text" placeholder="Username" name="userName" value="${sessionScope.username}"/>
          <input type="password" placeholder="Password" name="password" value="${sessionScope.password}"/>
          <input type="submit" value="Log in"/>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>