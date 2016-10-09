<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Profile - Digital Library</title>
<%@ include file="style.html" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<body>
<div class="container">

  <c:set var="cp" value="5" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Profile</h2>

<div class="row">
  <div class="col-md-12">
    <form id="profileForm" action="register" method="post">
      <div class="form-group">
        <label for="inputUsername">Username</label>
        <input class="form-control" type="text" name="username" id="inputUsername" value="${user.username}" readonly required>
      </div>
      <div class="form-group">
        <label for="inputNickname">Nickname</label>
        <input class="form-control" type="text" name="nickname" id="inputNickname" value="${user.nickname}" readonly required>
      </div>
      <div class="row">
      <div class="form-group col-md-6 col-sm-6 col-xs-6">
        <label for="inputFirstName">First Name</label>
        <input class="form-control" type="text" name="firstName" id="inputFirstName" value="${user.firstName}" readonly required>
      </div>
      <div class="form-group col-md-6 col-sm-6 col-xs-6">
        <label for="inputLastName">Last Name</label>
        <input class="form-control" type="text" name="lastName" id="inputLastName" value="${user.lastName}" readonly required>
      </div>
      </div>
      <div class="form-group">
        <label for="inputEmail">Email</label>
        <input class="form-control" type="email" name="email" id="inputEmail" value="${user.email}" readonly required>
      </div>
      <div class="form-group">
        <label for="inputYear">Birth Year</label>
        <input class="form-control" type="number" name="birthYear" id="inputYear" value="${user.birthYear}" readonly required>
      </div>
      <div class="form-group">
        <label for="inputAddress">Address</label>
        <textarea class="form-control" type="text" name="address" id="inputAddress" readonly required>${user.address}</textarea>
      </div>
      <div class="form-group">
        <label for="inputCreditCard">Credit Card Number</label>
        <input class="form-control" type="number" name="creditCard" id="inputCreditCard" value="${user.creditCard}" readonly required>
      </div>
      <!--
      <div class="form-group">
        <label for="inputType">Type</label>
        <select class="form-control" name="type" id="inputType" readonly>
          <option value="0" ${user.type==0 ? 'selected' : ''}>Customer</option>
          <option value="1" ${user.type==1 ? 'selected' : ''}>Book Seller</option>
          <option value="2" ${user.type==2 ? 'selected' : ''}>Admin</option>
        </select>
      </div>
      -->
      <button id="editButton" class="btn btn-default" style="width:20%">Edit</button>
      <button id="submitButton" class="btn btn-default" style="width:20%">Submit</button>
      <button id="cancelButton" class="btn btn-default" style="width:20%">Cancel</button>
    </form>
  </div>
</div>

<div id="otherWarning" class="alert alert-danger" role="alert" hidden></div>

</div> <!-- container -->

<script type="text/javascript">
$(function() {
    $otherWarning = $('#otherWarning');
    $profileForm = $('#profileForm');
    $editButton = $('#editButton');
    $submitButton = $('#submitButton');
    $cancelButton = $('#cancelButton');
    $inputUsername = $('#inputUsername');
    $inputAddress = $('#inputAddress');

    $submitButton.hide();
    $cancelButton.hide();

    var map = {};
    $("#profileForm input, #profileForm textarea, #profileForm select").each(function() {
        map[$(this).attr("name")] = $(this).val();
    });

    $editButton.click(function(e) {
        e.preventDefault();
        $profileForm.find('input').prop('readonly', false);
        $inputAddress.prop('readonly', false);
        $inputUsername.prop('readonly', true);
        $(this).hide();
        $submitButton.show();
        $cancelButton.show();
    });

    $cancelButton.click(function(e) {
        e.preventDefault();
        $profileForm.find('input').prop('readonly', true);
        $inputAddress.prop('readonly', true);
        $(this).hide();
        $submitButton.hide();
        $editButton.show();
        $("#profileForm input, #profileForm textarea, #profileForm select").each(function() {
             $(this).val(map[$(this).attr("name")]);
        });
    });

    $profileForm.submit(function() {
        $.post("profile", $(this).serialize(), function(data, textStatus) {
            console.log(data);
            console.log(textStatus);
            location.reload();
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
