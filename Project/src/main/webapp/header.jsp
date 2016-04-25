<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Header</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="style/header-footer.css">
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
  <script type="text/javascript" src="scripts/header.js"></script>
</head>
<body>

<%
    Cookie[] theCookies = request.getCookies();

    if (theCookies != null) {
      for (Cookie tempCookie : theCookies) {

        if ("ioiw.username".equals(tempCookie.getName())) {
          request.getSession().setAttribute("username", tempCookie.getValue());
        }
        if ("ioiw.password".equals(tempCookie.getName())) {
          request.getSession().setAttribute("password", tempCookie.getValue());
        }
      }
    }
%>

<c:choose>
  <c:when test="${applicationScope.token eq 'logged'}">
    <div class="header">
      <div class="container">
        <ul class="nav">
          <li><a href="main.jsp">Home</a></li>
          <li><a href="#">Team</a></li>
          <li><a href="showGoalsServlet">Goals</a></li>
        </ul>
        <ul class="nav right">
          <div class="dropdown">
            <li class="btn dropdown-toggle" type="button" data-toggle="dropdown">
              <a href="#">${sessionScope.username}</a>
              <span class="caret"></span>
            </li>
            <ul class="dropdown-menu">
              <li><a href="accountServlet" >My account</a></li>
              <li><a href="logoutServlet">Log out</a></li>
            </ul>
          </div>
        </ul>
      </div>
    </div>
  </c:when>
  <c:otherwise>
    <div class="header">
      <div class="container">
        <ul class="nav">
          <li><a href="main.jsp">Home</a></li>
          <li><a href="#">Team</a></li>
          <li><a href="showGoalsServlet">Goals</a></li>
          <li><a href="login.jsp">Log in</a></li>
          <li><a href="register.jsp">Register</a></li>
        </ul>
      </div>
    </div>
  </c:otherwise>
</c:choose>
</body>
</html>