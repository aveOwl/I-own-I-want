        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Main Page</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel="stylesheet">
  <link href="style/front-style.css" rel='stylesheet'>
</head>
<body>
<%--<%
  Cookie[] theCookies = request.getCookies();

  String user_id = null;
  String token = null;

  if (theCookies != null) {
    for (Cookie tempCoockie : theCookies) {

     /* if ("ioiw.username".equals(tempCoockie.getName())) {
        username = tempCoockie.getValue();
        request.getServletContext().setAttribute("username", username);
//        session.setAttribute("username", tempCoockie.getValue());
      }
      if ("ioiw.password".equals(tempCoockie.getName())) {
        password = tempCoockie.getValue();
        request.getServletContext().setAttribute("password", password);
//        session.setAttribute("password", tempCoockie.getValue());
      }*/



      if ("ioiw.user_id".equals(tempCoockie.getName())) {
        user_id = tempCoockie.getValue();
        request.getServletContext().setAttribute("user_id", user_id);
      }
      if ("ioiw.token".equals(tempCoockie.getName())) {
        token = tempCoockie.getValue();
        request.getServletContext().setAttribute("token", token);
      }

    }
  }
%>--%>

<jsp:include page="header.jsp"/>
<div class="wrapper">
  <div class="front">
    <div class="container">
      <div class="banner">
        <h1>manage yourself</h1>
        <%--add choose tag??--%>
        <a class="banner-action" href="welcome" role="button">get started</a>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>