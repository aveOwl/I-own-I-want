<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <link href="style/login-page-style.css" rel='stylesheet'>
  <meta charset="utf-8">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="front">
  <div class="container">
    <div class="banner">
      <form action="loginServlet" method="post" class="login">
        <input type="text" placeholder="Username" name="username"/>
        <input type="password" placeholder="Password" name="password"/>
        <input type="submit" value="Log in"/>
      </form>
    </div>
  </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>