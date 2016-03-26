<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Header</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel='stylesheet'>
</head>
<body>
<%
  Integer user_id = null;
  String token = null;

  if(user_id == null) {

    Cookie[] theCookies = request.getCookies();

    if (theCookies != null) {
      for (Cookie tempCoockie : theCookies) {

        if ("ioiw.user_id".equals(tempCoockie.getName())) {
          user_id =  Integer.valueOf(tempCoockie.getValue());
          request.getServletContext().setAttribute("user_id", user_id);
        }
        if ("ioiw.token".equals(tempCoockie.getName())) {
          token =  tempCoockie.getValue();
          request.getServletContext().setAttribute("token", token);
        }
      }
    }
  }
%>

<c:choose>
  <c:when test="${token eq 'logged'}">
    <div class="header">
      <div class="container">
        <ul class="nav">
          <a href="main.jsp"><li>Home</li></a>
          <a href="#"><li>Team</li></a>
          <a href="goalServlet"><li>Goals</li></a>
        </ul>
        <ul class="nav right">
          <a href="#"><li>My account</li></a>
          <a href="logoutServlet"><li>Log out</li></a>
        </ul>
      </div>
    </div>
  </c:when>
  <c:otherwise>
    <div class="header">
      <div class="container">
        <ul class="nav">
          <a href="main.jsp"><li>Home</li></a>
          <a href="#"><li>Team</li></a>
          <a href="goalServlet"><li>Goals</li></a>
          <a href="login.jsp"><li>Log in</li></a>
          <a href="register.jsp"><li>Register</li></a>
        </ul>
      </div>
    </div>
  </c:otherwise>
</c:choose>
</body>
</html>