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
          <label for="userName">Username:</label>
          <input type="text" placeholder="Username" id="userName" name="userName" value="${sessionScope.username}"/>
          <label for="password">Password:</label>
          <input type="password" placeholder="Password" id="password" name="password" value="${sessionScope.password}"/>
          <%--<input type="submit" value="Log in"/>--%>
          <button type="submit" class="proceed">Log in</button>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>