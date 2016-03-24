<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Main Page</title>
  <link href="style/main-page-style.css" rel='stylesheet'>
  <meta charset="utf-8">
</head>
<body>
<div class="header">
  <div class="container">
    <ul class="nav">
      <a href="#" id="row"><li>Home</li></a>
      <a href="#"><li>Team</li></a>
      <a href="#"><li>Dashboard</li></a>
      <a href="login.jsp"><li>Log in</li></a>
      <a href="register.jsp"><li>Register</li></a>
    </ul>
    <ul class="nav right">
      <a href="#"><li>My account</li></a>
      <a href="#"><li>Log out</li></a>
    </ul>
  </div>
</div>
<div class="front">
  <div class="container">
    <div class="banner">
      <h1>manage yourself</h1>
      <a class="banner-action" href="welcome" role="button">get started</a>
    </div>
  </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
