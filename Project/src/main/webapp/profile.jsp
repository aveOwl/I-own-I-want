<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Profile</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel="stylesheet">
  <link href="style/profile-style.css" rel='stylesheet'>
  <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script src="scripts/profile.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="line"></div>
<div class="wrapper">
  <div class="front">
    <div class="container">
      <div class="banner">
        <form action="accountServlet" method="post">
          <fieldset>
            <legend><span class="number">1</span>Profile info</legend>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" value="${firstName}">

            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="${lastName}">

            <label for="userName">User Name:</label>
            <input type="text" id="userName" name="userName" value="${userName}">

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${email}">

            <a class="passButton">Change password</a>
            <div class="password">
              <label for="current_password">Current password:</label>
              <input type="password" id="current_password" name="password">

              <label for="new_password">New password:</label>
              <input type="password" id="new_password" name="password">

              <label for="confirm_password">Confirm password:</label>
              <input type="password" id="confirm_password" name="password">
            </div>

          </fieldset>
          <button type="submit">Update</button>
        </form>
      </div>
    </div>
  </div>
  <div class="push"></div>
</div>
<div class="line"></div>
<jsp:include page="footer.jsp"/>
</body>
</html>
