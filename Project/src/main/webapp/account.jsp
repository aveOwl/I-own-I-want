<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" ng-app="application">
<head>
  <title>Profile</title>
  <meta charset="utf-8">
  <link href="style/account-style.css" rel="stylesheet">
  <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script type="text/javascript" src="scripts/angular.min.js"></script>
  <script type="text/javascript" src="scripts/account.js"></script>
  <script type="text/javascript" src="scripts/account-test.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper" ng-controller="MainController as ctrl">
  <form name="frm" action="updateAccountServlet" method="post" novalidate>
    <fieldset>
      <legend><span class="number">1</span>Profile info</legend>
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
        <input type="text" name="userName" id="userName" ng-model="ctrl.user.userName" ng-minlength="3" required />
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
        <label for="monthSalary">MonthSalary:</label>
        <input type="number" name="monthSalary" id="monthSalary" ng-model="ctrl.user.monthSalary" required />
        <span ng-show="frm.monthSalary.$dirty && frm.monthSalary.$error.number">Enter a number.</span>
      </div>

      <div>
        <label for="current_password">Current Password:</label>
        <input type="password" name="current_password" id="current_password" ng-model="ctrl.user.current_password" compare-to="ctrl.user.password_check" ng-minlength="5" required />
        <span ng-show="frm.current_password.$dirty && frm.current_password.$error.required">Password is required.</span>
        <span ng-show="frm.current_password.$dirty && frm.current_password.$error.compareTo">Wrong password.</span>
      </div>

      <div class="passButton">
        <a>Change password</a>
      </div>

      <div class="password">
        <div>
          <label for="new_password">New Password:</label>
          <input type="password" name="new_password" id="new_password" ng-model="ctrl.user.new_password" ng-minlength="5" ng-disabled="frm.current_password.$invalid" required />
          <span ng-show="frm.new_password.$dirty && frm.new_password.$error.minlength">Password is too short.</span>
        </div>
        <div>
          <label for="confirm_password">Confirm Password:</label>
          <input type="password" name="confirm_password" id="confirm_password" ng-model="ctrl.user.confirm_password" compare-to="ctrl.user.new_password" ng-disabled="frm.new_password.$invalid" required />
          <span ng-show="frm.confirm_password.$dirty && frm.confirm_password.$error.compareTo">Passwords don't match.</span>
        </div>
      </div>

    </fieldset>
    <button type="submit"
            class="proceed"
            ng-class="{red: frm.email.$invalid || frm.monthSalary.$error.number || frm.current_password.$invalid || frm.confirm_password.$error.compareTo}"
            ng-disabled="frm.email.$invalid || frm.monthSalary.$error.number || frm.current_password.$invalid || frm.confirm_password.$error.compareTo">Save Changes
    </button>
  </form>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
