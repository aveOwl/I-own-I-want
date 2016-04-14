<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Goals</title>
  <meta charset="UTF-8">
  <link href="style/header-footer.css" rel="stylesheet">
  <link href="style/goals-style.css" rel="stylesheet">
  <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script src="scripts/goals.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper">
  <button class="goal">new Goal</button>
  <div class="articles">

    <div class="insertion">

      <div class="itemForm">

        <div class="form">
          <!--        <div class="item">-->

          <div class="buttonContainer">
            <button class="closeForm">✗</button>
          </div>

          <input type="text" id="title"/>

          <input type="number" id="cost" name="cost"/>

          <textarea id="shorten" name="shorten"></textarea>

          <textarea id="description" name="description"></textarea>

          <input id="confirm" type="submit" value="Confirm"/>
        </div>

      </div>
    </div>


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
          <div class="buttonContainer">
            <button class="close">✗</button>
            <button class="edit">✍</button>
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
  <div class="push"></div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>