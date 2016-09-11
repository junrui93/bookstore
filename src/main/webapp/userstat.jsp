<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin - Digital Library</title>
<%@ include file="style.html" %>
</head>
<body>
<div class="container">

  <h2>Admin - User Statistics</h2>

  <h3>User</h3>
  <p>id: ${user.id}</p>
  <p>username: ${user.username}</p>
  <p>email: ${user.email}</p>

  <h3>Items Bought</h3>
  <table class="table table-striped">
    <tr>
      <th style="width:50%">title</th>
      <th style="width:25%">timestamp</th>
      <th style="width:15%">price</h>
      <th style="width:10%">seller</th>
    </tr>
    <c:forEach var="order" items="${orders}">
      <c:if test="${order.commitTs != null}">
        <tr>
          <td><a href="/info?id=${order.publ.id}&view=admin" target="_blank">${order.publ.title}</a></td>
          <td>${order.commitTs}</td>
          <td>${order.publ.price} x ${order.number} = ${order.publ.price * order.number}</td>
          <td>${order.publ.seller.username}</td>
        </tr>
      </c:if>
    </c:forEach>
  </table>

  <h3>Items Removed</h3>
  <table class="table table-striped">
    <tr>
      <th style="width:50%">title</th>
      <th style="width:25%">add timestamp</th>
      <th style="width:25%">remove timestamp</h>
    </tr>
    <c:forEach var="order" items="${orders}">
      <c:if test="${order.removeTs != null}">
        <tr>
          <td><a href="/info?id=${order.publ.id}&view=admin" target="_blank">${order.publ.title}</a></td>
          <td>${order.addTs}</td>
          <td>${order.removeTs}</td>
        </tr>
      </c:if>
    </c:forEach>
  </table>

</div>

</body>
