<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Verification - Digital Library</title>
<%@ include file="style.html" %>
<body>
<div class="container">

  <c:set var="cp" value="6" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Verification</h2>

<c:choose>
<c:when test="${success == true}">
<div class="panel panel-success" id="successPanel">
  <div class="panel-heading">
    <h3 class="panel-title">Verification Succeeded</h3>
  </div>
  <div class="panel-body">Please set your password first</div>
</div>

<div class="row">
  <div class="col-md-12">
    <form id="passwordForm">
      <div class="form-group">
        <label for="inputPassword">Password</label>
        <input class="form-control" type="password" name="password" id="inputPassword" required>
        <input type="text" name="oldPassword" value="" hidden>
      </div>
      <button class="btn btn-default" style="width:20%">Submit</button>
    </form>
  </div>
</div>

<div id="otherWarning" class="alert alert-danger" role="alert" hidden></div>
</c:when>
<c:otherwise>
<div class="panel panel-warning" id="warningPanel">
  <div class="panel-heading">
    <h3 class="panel-title">Verification Failed</h3>
  </div>
  <div class="panel-body">Something is wrong. Please use the Forget Password function to complete verification.</div>
</div>
</c:otherwise>
</c:choose>

</div> <!-- container -->

<script type="text/javascript">
$(function() {
    $otherWarning = $('#otherWarning');

    $('#passwordForm').submit(function() {
        $.post("password", $(this).serialize(), function(data, textStatus) {
            location.href = 'home';
        })
        .fail(function(xhr) {
            $otherWarning.text(xhr.status + ' ' + xhr.statusText + ': ' + xhr.responseText);
            $otherWarning.show();
        });
        return false;
    });
});
</script>
</body>
</html>
