<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Info - Digital Library</title>
<%@ include file="style.html" %>
</head>
<body>
<div class="container">

  <c:set var="cp" value="3" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Info</h2>

  <table class="table table-bordered">
    <tbody>
      <tr>
        <td style="width:15%">title</td>
        <td><span class="title">${publ.title}</span></td>
      </tr>
      <tr>
        <td>type</td>
        <td>${publ.type}</td>
      </tr>
      <tr>
        <td>author</td>
        <td>
          <c:forEach var="author" items="${publ.authors}" varStatus="status">
            <span class="author">${author.name}</span>${status.last ? '' : ','}
          </c:forEach>
        </td>
      </tr>
      <tr>
        <td>seller</td>
        <td>${publ.seller.username}</td>
      </tr>
      <tr>
        <td>price</td>
        <td><strong class="text-danger">$${publ.price}</strong></td>
      </tr>
      <tr>
        <td>venue</td>
        <td>${publ.venue}</td>
      </tr>
      <tr>
        <td>year</td>
        <td>${publ.year}</td>
      </tr>
      <tr>
        <td>image</td>
        <td><img src="${publ.imagePath == null ? 'static/default.jpg' : publ.imagePath}" style="height:360px; max-width:100%"/></td>
      </tr>
      <tr>
        <td>description</td>
        <td><pre>${publ.description}</pre></td>
      </tr>
    </tbody>
  </table>

  <c:if test="${param.view != 'admin'}">
    <c:if test="${user != null && user.type == 0}">
      <button class="btn btn-default add-cart" publicationId="${publ.id}">Add to cart</button>
    </c:if>
    <c:if test="${user != null && user.type == 1 && user.id == publ.sellerId}">
      <c:choose>
        <c:when test="${publ.removed == false}">
          <a class="btn btn-default remove" publicationId="${publ.id}">Remove</a>
        </c:when>
        <c:otherwise>
          <a class="btn btn-default restore" publicationId="${publ.id}">Restore</a>
        </c:otherwise>
      </c:choose>
    </c:if>
  </c:if>

  <br>

  <div class="panel panel-default" hidden>
    <div class="panel-heading"><h3 class="panel-title">Analytics</h3></div>
    <div class="panel-body">
      <p>You have visited this item for ${numVisited} time${numVisited > 1 ? 's' : ''}</p>
      This item has been searched for ${lit.numSearched} time${lit.numSearched > 1 ? 's' : ''}
    </div>
  </div>
</div>

<script>
$(function() {
    $('.add-cart').click(function() {
        var publicationId = $(this).attr('publicationId');
        $.post("/cart/add?id=" + publicationId, function() {
            location.reload();
        });
    });

    $('a.remove, a.restore').click(function(e) {
        e.preventDefault();
        var publicationId = $(this).attr('publicationId');
        $.post("/seller/toggleremoved?id=" + publicationId, function() {
            location.reload();
        });
    });
});
</script>
</body>
</html>
