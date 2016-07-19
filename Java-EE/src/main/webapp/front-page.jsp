<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Main Page</title>
  <meta charset="utf-8">
  <link href="style/header-footer.css" rel="stylesheet">
  <link href="style/front-style.css" rel='stylesheet'>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="wrapper">

  <div class="banner">
    <h1>manage yourself</h1>
    <a class="banner-action" href="showGoalsServlet" role="button">get started</a>
  </div>

  <div class="container marketing">

    <hr class="featurette-divider">

    <div class="row featurette">
      <div class="col-md-7">
        <h2 class="featurette-heading">First featurette heading. <span class="text-muted">It'll blow your mind.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>
      <div class="col-md-5">
        <img class="featurette-image img-responsive center-block img-circle" src="style/img/goal-1.jpg" alt="Generic placeholder image">
      </div>
    </div>

    <hr class="featurette-divider">

    <div class="row featurette">
      <div class="col-md-7 col-md-push-5">
        <h2 class="featurette-heading">Oh yeah, it's that good. <span class="text-muted">See for yourself.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>
      <div class="col-md-5 col-md-pull-7">
        <img class="featurette-image img-responsive center-block img-circle" src="style/img/goal-2.jpg" alt="Generic placeholder image">
      </div>
    </div>

    <hr class="featurette-divider">

    <div class="row featurette">
      <div class="col-md-7">
        <h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>
      <div class="col-md-5">
        <img class="featurette-image img-responsive center-block img-circle" src="style/img/goal-3.jpg" alt="Generic placeholder image">
      </div>
    </div>

    <hr class="featurette-divider">

  </div>

</div>

<div class="push"></div>

<jsp:include page="footer.jsp"/>

</body>
</html>