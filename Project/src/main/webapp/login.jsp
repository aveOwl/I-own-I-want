<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" ng-app="application">
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <link href="style/login-style.css" rel='stylesheet'>
  <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script type="text/javascript" src="scripts/angular.min.js"></script>
  <script type="text/javascript" src="scripts/login.js"></script>
  <script type="text/javascript">
    (function () {
      var app = angular.module('application', []);
      app.controller('MainController', function () {
        this.user = {
          userName: "${sessionScope.username}",
          password: "${sessionScope.password}",
        };
      })
    })();
  </script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper">
  <div class="front">
    <div class="container">
      <div class="banner" ng-controller="MainController as ctrl">
        <form name="frm" id="frm" action="loginServlet" method="post" class="login" novalidate>
          <div>
            <label for="userName">User Name:</label>
            <input type="text" name="userName" id="userName" ng-model="ctrl.user.userName" ng-minlength="3" required />
            <span class="status"></span>
          </div>
          <div>
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" ng-model="ctrl.user.password" ng-minlength="5" required />
          </div>
          <button type="submit"
                  class="proceed"
                  ng-class="{red: frm.$invalid}"
                  ng-disabled="frm.$invalid">Log in
          </button>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>