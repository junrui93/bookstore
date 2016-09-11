<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sign Up - Digital Library</title>
<%@ include file="style.html" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<body>
<div class="container">

  <%@ include file="nav.jsp" %>

  <h2>Sign up</h2>

<div class="row">
  <div class="col-md-12">
    <form id="registerForm" action="register" method="post">
      <div class="form-group">
        <label for="inputUsername">Username</label>
        <input class="form-control" type="text" name="username" id="inputUsername" required>
      </div>
      <div class="form-group">
        <label for="inputNickname">Nickname</label>
        <input class="form-control" type="text" name="nickname" id="inputNickname" required>
      </div>
      <div class="row">
      <div class="form-group col-md-6 col-sm-6 col-xs-6">
        <label for="inputFirstName">First Name</label>
        <input class="form-control" type="text" name="firstName" id="inputFirstName" required>
      </div>
      <div class="form-group col-md-6 col-sm-6 col-xs-6">
        <label for="inputLastName">Last Name</label>
        <input class="form-control" type="text" name="lastName" id="inputLastName" required>
      </div>
      </div>
      <div class="form-group">
        <label for="inputEmail">Email</label>
        <input class="form-control" type="email" name="email" id="inputEmail" required>
      </div>
      <div class="form-group">
        <label for="inputYear">Birth Year</label>
        <input class="form-control" type="number" name="birthYear" id="inputYear" required>
      </div>
      <div class="form-group">
        <label for="inputAddress">Address</label>
        <textarea class="form-control" type="text" name="address" id="inputAddress" required></textarea>
      </div>
      <div class="form-group">
        <label for="inputCreditCard">Credit Card Number</label>
        <input class="form-control" type="number" name="creditCard" id="inputCreditCard" required>
      </div>
      <div class="form-group">
        <label for="inputType">Type</label>
        <select class="form-control" name="type" id="inputType">
          <option value="0">Customer</option>
          <option value="1">Book Seller</option>
        </select>
      </div>
      <button class="btn btn-default" style="width:20%">Submit</button>
    </form>
  </div>
</div>

<div class="panel panel-success" id="successPanel" hidden>
  <div class="panel-heading">
    <h3 class="panel-title">Registration Succeeded</h3>
  </div>
  <div class="panel-body">An email is sent to <span id="email"></span> to confirm the registration.</div>
</div>

<div id="otherWarning" class="alert alert-danger" role="alert" hidden></div>

</div> <!-- container -->

<script type="text/javascript">
$(function() {
    $otherWarning = $('#otherWarning');
    $inputUsername = $('#inputUsername');
    $email = $('#email');
    $successPanel = $('#successPanel');
    $registerForm = $('#registerForm');

    $('#inputYear').datepicker({
        format: "yyyy",
        minViewMode: 2,
        autoclose: true
    });

    $('#registerForm').submit(function() {
        var email = $('#inputEmail').val();
        $.post("/register", $(this).serialize(), function(data, textStatus) {
            console.log(data);
            console.log(textStatus);
            $email.text(email);
            $successPanel.show();
            $registerForm.hide();
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
