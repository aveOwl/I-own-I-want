<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
ng-app directory creates an angular application by running module
with the specific name (in this case application) when the document loads
just by adding it its going to treat the HTML inside of this element
as part of the angular app. So we can start wring expressions
--%>

<%--expressions allow to insert dynamic expressions into HTML--%>
<html ng-app="application">
<head>
  <title>Goals</title>
  <meta charset="UTF-8">
  <link href="style/goals-style.css" rel="stylesheet">
  <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
  <script type="text/javascript" src="scripts/angular.min.js"></script>
  <script src="scripts/goals.js"></script>
  <script src="scripts/goals-valid.js"></script> <%--include piece of angular js code--%>
</head>
<body>
<jsp:include page="header.jsp"/>
<%--
In angular we add behaviour to our html through directives. Directive is a marker on an HTML tag that
tells angular to run or reference some javascript
--%>

<%--
ng-controller directive allows us to attach our controller to this element
inside of this html
as the value of this attribute we will write a controller name
ctrl specifies allias for the chosen controller, which we will use inside of our expressions
--%>
<div class="wrapper" ng-controller="MainController as ctrl">
  <button class="goal">new Goal</button>
  <div class="insertion">
    <div class="itemForm">
      <form name="frm" novalidate>
        <div class="buttonContainer">
          <button class="closeForm">✗</button>
        </div>
        <div>
          <label for="title">Title:</label>
          <%--ng-model directive binds the form element value to the property--%>
          <%--required - to mark fields that are requierd for our form--%>
          <%--$ means that we are referencing a built-in property from angular--%>
          <input type="text" id="title" name="title" ng-model="ctrl.goal.title" required/>
          <span ng-show="frm.title.$dirty && frm.title.$error.required">Title is required.</span>
        </div>
        <div>
          <label for="cost">Cost:</label>
          <%--ng-model directive binds the form element value to the property--%>
          <%--required - to mark fields that are requierd for our form--%>
          <input type="number" id="cost" name="cost" ng-model="ctrl.goal.cost" required/>
          <%--directive ng-show will only show the element if the value of the
          expression is true--%>
          <span ng-show="frm.cost.$dirty && frm.cost.$error.required">Cost is required.</span>
          <span ng-show="frm.cost.$dirty && frm.cost.$error.number">Enter a number.</span>
        </div>
        <div>
          <label for="shorten">Short Description:</label>
          <%--ng-model directive binds the form element value to the property--%>
          <textarea id="shorten" name="shorten" ng-model="ctrl.goal.shortDescr"></textarea>
        </div>
        <div>
          <label for="description">Description:</label>
          <%--ng-model directive binds the form element value to the property--%>
          <textarea id="description" name="description" ng-model="ctrl.goal.description"></textarea>
        </div>
        <button type="submit"
                id="confirm"
                class="proceed"
                <%--
                red - name of the class to add
                after the colon an expression to evaluate. If true, set class
                to redm otherwise nothing
                --%>
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
            <p class="title"><span id="forDeletion">${goal.v_id} </span> ${goal.title}</p>
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