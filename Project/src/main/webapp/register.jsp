<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Register</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel="stylesheet">
  <link href="style/register-style.css" rel='stylesheet'>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper">
  <div class="front">
    <div class="container">
      <div class="banner">
        <form action="registrationServlet" method="post" class="login">
          <label for="firstName">First Name:</label>
          <input type="text" id="firstName" name="firstName"/>
          <label for="lastName">Last Name:</label>
          <input type="text" id="lastName" name="lastName"/>
          <label for="userName">User Name:</label>
          <input type="text" id="userName" name="userName" required/>
          <label for="email">Email:</label>
          <input type="email" id="email" name="email" required/>
          <label for="password">Password:</label>
          <input type="password" id="password" name="password" required/>
          <%--<input type="submit" value="Register"/>--%>
          <button type="submit" class="proceed">Register</button>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>