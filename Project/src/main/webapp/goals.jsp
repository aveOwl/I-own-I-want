<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="application">
<head>
  <title>Goals</title>
  <meta charset="UTF-8">
  <link href="style/goals-style.css" rel="stylesheet">
  <script type="text/javascript" src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular.min.js"></script>
  <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script src="scripts/goals.js"></script>
  <script src="scripts/goals-valid.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper" ng-controller="MainController as ctrl">
  <button class="goal">new Goal</button>
  <div class="insertion">
    <div class="itemForm">
      <form name="frm" id="frm" novalidate>
        <div class="buttonContainer">
          <button class="closeForm">✗</button>
        </div>
        <div>
          <label for="title">Title:</label>
          <input type="text" id="title" name="title" ng-model="ctrl.goal.title" required/>
          <span ng-show="frm.title.$dirty && frm.title.$error.required">Title is required.</span>
        </div>
        <div>
          <label for="cost">Cost:</label>
          <input type="number" id="cost" name="cost" ng-model="ctrl.goal.cost" required/>
          <span ng-show="frm.cost.$dirty && frm.cost.$error.required">Cost is required.</span>
          <span ng-show="frm.cost.$dirty && frm.cost.$error.number">Enter a number.</span>
        </div>
        <div>
          <label for="shorten">Short Description:</label>
          <textarea id="shorten" name="shorten" ng-model="ctrl.goal.shortDescr"></textarea>
        </div>
        <div>
          <label for="description">Description:</label>
          <textarea id="description" name="description" ng-model="ctrl.goal.description"></textarea>
        </div>
        <button type="submit"
                id="confirm"
                class="proceed"
                ng-class="{red: frm.$invalid}"
                ng-disabled="frm.$invalid">Confirm</button>
      </form>
    </div>
  </div>

  <div class="articles">
    <c:forEach items="${goals_list}" var="goal">
      <div class="article">
        <div class="item">
          <div class="row">
            <p class="title"><span class="hideME">${goal.id}</span> #${goal.v_id} ${goal.title}</p>
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