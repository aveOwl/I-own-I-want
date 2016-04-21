<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" ng-app="application">
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <link href="style/login-style.css" rel='stylesheet'>
  <script type="text/javascript" src="scripts/angular.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>
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
      <div id="logError" class="error hideME">
        <b>ERROR:</b> User name or password is incorrect.
      </div>
      <div class="banner" ng-controller="MainController as ctrl">
        <form name="frm" onSubmit="return myFunc()" action="loginServlet" method="post" class="login" novalidate>
          <div>
            <label for="userName">User Name:</label>
            <input type="text" name="userName" id="userName" ng-model="ctrl.user.userName" ng-minlength="3" required />
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