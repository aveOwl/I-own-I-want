<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Profile</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel="stylesheet">
  <link href="style/account-style.css" rel='stylesheet'>
  <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script src="scripts/account.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper">
  <form action="updateAccountServlet" method="post">
    <fieldset>
      <legend><span class="number">1</span>Profile info</legend>
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" value="${user.firstName}">

      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" value="${user.lastName}">

      <label for="userName">User Name:</label>
      <input type="text" id="userName" name="userName" value="${user.userName}">

      <label for="email">Email:</label>
      <input type="email" id="email" name="email" value="${user.email}">

      <label for="monthSalary">Month Salary:</label>
      <input type="text" id="monthSalary" name="monthSalary" value="${user.monthSalary}">

      <div class="passButton">
      <a>Change password</a>
      </div>
      <div class="password">
        <label for="current_password">Current password:</label>
        <input type="password" id="current_password" name="current_password">

        <label for="new_password">New password:</label>
        <input type="password" id="new_password" name="new_password">

        <label for="confirm_password">Confirm password:</label>
        <input type="password" id="confirm_password" name="confirm_password">
      </div>

    </fieldset>
    <button type="submit" class="proceed">Save Changes</button>
  </form>
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
