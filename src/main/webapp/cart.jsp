<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Shopping Cart - Digital Library</title>
<%@ include file="style.html" %>
</head>
<body>
<div class="container">
  <c:set var="cp" value="2" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Shopping Cart</h2>
  
  <c:choose>
  <c:when test="${orders == null || orders.isEmpty()}">
  <div class="alert alert-danger" role="alert">
    Your shopping cart is empty, try to add something! <a href="home">Back to home page</a>
  </div>
  </c:when>
  
  <c:otherwise>
  <form id="cartForm" action="search">
  <ul class="list-group">
    <c:forEach var="order" items="${orders}">
      <li class="list-group-item">
        <div class="row">
          <div class="col-md-2">
            <img src="${order.publ.imagePath == null ? 'static/default.jpg' : imagePath}" class="center-block" style="height:160px; max-width:100%"/>
          </div>
          <div class="col-md-10">
            <p>
              <span class="label label-default">${order.publ.type}</span>
              <a href="info?id=${order.publ.id}" target="_blank"><span class="title">${order.publ.title}</span></a>
            </p>
            <p class="author text-muted">
              <c:forEach var="author" items="${order.publ.authors}" varStatus="status">
                <span class="author">${author.name}</span>${status.last ? '' : ','}
              </c:forEach>
            </p>
            <p class="price text-danger strong"><strong>$${order.publ.price} x ${order.number} = $${order.number * order.publ.price}</strong></p>
            <button class="btn btn-default cart-action" action="remove" orderId="${order.id}">Remove</button>
            <button class="btn btn-default cart-action" action="decrement" orderId="${order.id}" ${order.number <= 1 ? 'disabled' : ''}>-</button>
            <button class="btn btn-default cart-action" action="increment" orderId="${order.id}">+</button>
          </div>
        </div>
      </li>
    </c:forEach>
  </ul>
  <button class="btn btn-default" style="width:20%" id="buyButton1">Buy</button>
  </form>

  <div id="confirmTable" hidden>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">Items</h3>
      </div>
          <c:set var="totalPrice" value="${0}" />
          <table id="confirmTable" class="table table-striped">
            <tr>
              <th style="width:40%">title</th>
              <th style="width:40%">authors</th>
              <th style="width:8%">unit price</th>
              <th style="width:4%">number</th>
              <th style="width:8%">price</th>
            </tr>
            <c:forEach var="order" items="${orders}">
            <tr>
              <td>${order.publ.title}</td>
              <td>
                <c:forEach var="author" items="${order.publ.authors}" varStatus="status">
                  ${author.name}${status.last ? '' : ','}
                </c:forEach>
              </td>
              <td>$${order.publ.price}</td>
              <td>${order.number}</td>
              <td>$${order.number * order.publ.price}</td>
            </tr>
            <c:set var="totalPrice" value="${totalPrice + order.number * order.publ.price}" />
            </c:forEach>
            <tr>
              <td colspan="2"></td>
              <td colspan="2" class="info"><strong>total price<strong></td>
              <td colspan="3" class="info">$${totalPrice}</td>
            </tr>
          </table>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">User Information</h3>
      </div>
      <div class="panel-body">
        Username: ${user.username}<br>
        Full Name: ${user.firstName}, ${user.lastName}
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title" style="white-space: pre-wrap">Address</h3>
      </div>
      <div class="panel-body">${user.address}</div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">Payment</h3>
      </div>
      <div class="panel-body">
        Credit Card: ${user.creditCard}
      </div>
    </div>
  <button class="btn btn-default" id="buyButton2" style="width:20%">Buy</button>
  <button class="btn btn-default" id="cancelButton" style="width:20%">Cancel</button>
  </div>
    <div class="alert alert-success" role="alert" id="purchaseSuccess" hidden>
      Purchase succeeded. Your order has been emailed to the booksellers.
    </div>
  </c:otherwise>
  </c:choose>
</div>

<script type="text/javascript">
$(function() {

    $('.cart-action').click(function(e) {
        e.preventDefault();

        var orderId = $(this).attr('orderId');
        var action = $(this).attr('action');
        $.post("/cart/" + action + "?id=" + orderId, function() {
            location.reload();
        });
    });

    $cartForm = $('#cartForm');
    $confirmTable = $('#confirmTable');

    $('#buyButton1').click(function(e) {
        e.preventDefault();
        $cartForm.hide();
        $confirmTable.show();
    });

    $('#cancelButton').click(function(e) {
        e.preventDefault();
        $cartForm.show();
        $confirmTable.hide();
    });

    $('#buyButton2').click(function(e) {
        e.preventDefault();
        $.post("/cart/commit?id=0", function() {
            $cartForm.hide();
            $confirmTable.hide();
            $('#purchaseSuccess').show();
        });
    });
});
</script>
</body>
