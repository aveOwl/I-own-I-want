<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
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
          <input type="text" placeholder="Name" name="firstName"/>
          <input type="text" placeholder="LastName" name="lastName"/>
          <input type="text" placeholder="UserName" name="userName" required/>
          <input type="email" placeholder="Email" name="email" required/>
          <input type="password" placeholder="Password" name="password" required/>
          <input type="submit" value="Finish"/>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>