<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Goals</title>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="style/goals.css">
  <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script src="scripts/goals.js"></script>
</head>
<body>
<div class="articles container">
  <c:forEach items="${goals_list}" var="goal">
    <div class="article">
      <div class="item">
        <div class="row">
          <p class="title">${goal.title}</p>
        </div>
        <div class="item">
          <p class="source">${goal.description}</p>
        </div>
        <div class="item">
          <p class="pubdate">${goal.pubdate}</p>
        </div>
      </div>

      <div class="description">
        <div class="">&nbsp;</div>
        <h1>${goal.notes}</h1>
        <div class="">&nbsp;</div>
      </div>
    </div>
  </c:forEach>
</div>
</body>
</html>