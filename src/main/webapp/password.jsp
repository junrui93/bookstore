<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Password - Digital Library</title>
<%@ include file="style.html" %>
<body>
<div class="container">

  <c:set var="cp" value="6" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Change Password</h2>

<div class="row">
  <div class="col-md-12">
    <form id="passwordForm">
      <div class="form-group">
        <label for="inputOldPassword">Old Password</label>
        <input class="form-control" type="password" name="oldPassword" id="inputOldPassword" required>
      </div>
      <div class="form-group">
        <label for="inputPassword">New Password</label>
        <input class="form-control" type="password" name="password" id="inputPassword" required>
      </div>
      <button class="btn btn-default" style="width:20%">Submit</button>
    </form>
  </div>
</div>

<div id="updateSucceeded" class="alert alert-success" role="alert" hidden>update password succeeded</div>
<div id="otherWarning" class="alert alert-danger" role="alert" hidden></div>

</div> <!-- container -->

<script type="text/javascript">
$(function() {
    $otherWarning = $('#otherWarning');
    $updateSucceeded = $('#updateSucceeded');

    $('#passwordForm').submit(function() {
        $.post("password", $(this).serialize(), function(data, textStatus) {
            $otherWarning.hide();
            $updateSucceeded.show();
        })
        .fail(function(xhr) {
            $updateSucceeded.hide();
            $otherWarning.text(xhr.status + ' ' + xhr.statusText + ': ' + xhr.responseText);
            $otherWarning.show();
        })
        .always(function() {
            $('#inputOldPassword').val("");
            $('#inputPassword').val("");
        });
        return false;
    });
});
</script>
</body>
</html>
