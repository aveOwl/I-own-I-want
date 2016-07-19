<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" ng-app="application">
<head>
  <title>Register</title>
  <meta charset="utf-8">
  <link href="style/register-style.css" rel="stylesheet">
  <script type="text/javascript" src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script type="text/javascript" src="scripts/register.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper" ng-controller="MainController as ctrl">
  <form name="frm" action="registrationServlet" method="post" class="register" onSubmit="return myFunc()" novalidate>
    <div>
      <label for="firstName">First Name:</label>
      <input type="text" name="firstName" id="firstName" ng-model="ctrl.user.firstName" />
    </div>
    <div>
      <label for="lastName">Last Name:</label>
      <input type="text" name="lastName" id="lastName" ng-model="ctrl.user.lastName" />
    </div>
    <div>
      <label for="userName">User Name:</label>
      <input type="text" name="userName" id="userName" ng-minlength="3" ng-model="ctrl.user.userName" required />
      <span ng-show="frm.userName.$dirty && frm.userName.$error.required">Username is required.</span>
      <span ng-show="frm.userName.$dirty && frm.userName.$error.minlength">Username is too short.</span>
    </div>
    <div>
      <label for="email">Email:</label>
      <input type="email" name="email" id="email" ng-model="ctrl.user.email" required />
      <span ng-show="frm.email.$dirty && frm.email.$error.required">Email is required.</span>
      <span ng-show="frm.email.$dirty && frm.email.$error.email">Enter a valid email.</span>
    </div>
    <div>
      <label for="password">Password:</label>
      <input type="password" name="password" id="password" ng-minlength="5" ng-model="ctrl.user.password" required />
      <span ng-show="frm.password.$dirty && frm.password.$error.required">Password is required.</span>
      <span ng-show="frm.password.$dirty && frm.password.$error.minlength">Password is too short.</span>
    </div>
    <div>
      <label for="confirmPassword">Confirm Password:</label>
      <input type="password" name="confirmPassword" id="confirmPassword" ng-model="ctrl.user.confirmPassword" compare-to="ctrl.user.password" required />
      <span ng-show="frm.confirmPassword.$dirty && frm.confirmPassword.$error.compareTo">Passwords don't match.</span>
    </div>
    <button type="submit"
            class="proceed"
            ng-class="{red: frm.$invalid}"
            ng-disabled="frm.$invalid">Register
    </button>
  </form>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>