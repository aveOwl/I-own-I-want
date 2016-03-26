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
    Cookie[] theCookies = request.getCookies();

    if (theCookies != null) {
      for (Cookie tempCoockie : theCookies) {

        if ("ioiw.username".equals(tempCoockie.getName())) {
          request.getSession().setAttribute("username", tempCoockie.getValue());
        }
        if ("ioiw.password".equals(tempCoockie.getName())) {
          request.getSession().setAttribute("password", tempCoockie.getValue());
        }
      }
    }
%>

<c:choose>
  <c:when test="${applicationScope.token eq 'logged'}">
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