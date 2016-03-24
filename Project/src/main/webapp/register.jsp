<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <link href="style/register-page-style.css" rel='stylesheet'>
  <meta charset="utf-8">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="front">
  <div class="container">
    <div class="banner">
      <form action="registrationServlet" method="post" class="login">
        <input type="text" placeholder="Name" name="name"/>
        <input type="text" placeholder="Surname" name="surname"/>
        <input type="text" placeholder="Nickname" name="nickname"/>
        <input type="email" placeholder="Email" name="email"/>
        <input type="password" placeholder="Password" name="password"/>
        <input type="submit" value="Finish"/>
      </form>
    </div>
  </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
