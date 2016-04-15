<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" ng-app="application">
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel='stylesheet'>
  <link href="style/login-style.css" rel='stylesheet'>
  <script type="text/javascript" src="scripts/angular.min.js"></script>
  <script type="text/javascript">
    (function () {
      var app = angular.module('application', []);
      app.controller('MainController', function () {
        this.user = {
          userName: "${sessionScope.username}",
          password: "${sessionScope.password}",
          valid: "${valid}"
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
        <form name="frm" action="loginServlet" method="post" class="login" novalidate>
          <div>
            <label for="userName">User Name:</label>
            <input type="text" name="userName" id="userName" ng-model="ctrl.user.userName" ng-minlength="3" required />
            <span ng-show="frm.userName.$dirty && frm.userName.$error.minlength">Username is too short.</span>
          </div>
          <div>
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" ng-model="ctrl.user.password" ng-minlength="5" required />
            <span ng-show="frm.password.$dirty && frm.password.$error.minlength">Password is too short.</span>
          </div>
          <button type="submit" class="proceed" ng-disabled="frm.$invalid">Log in</button>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>