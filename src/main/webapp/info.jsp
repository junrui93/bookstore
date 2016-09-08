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

  <table class="table">
    <thead>
      <tr>
        <th style="width:15%"></th>
        <th></th>
    </thead>
    <tbody>
      <tr>
        <td>type</td>
        <td>${lit.type}</td>
      </tr>
      <c:forEach var="key" items="${lit.attr.keySet()}">
        <tr>
          <td>${key}</td>
          <td>
            <c:forEach var="value" items="${lit.attr.get(key)}">
              <c:choose>
                <c:when test="${key == 'title'}">
                  <strong>${value}</strong>
                </c:when>
                <c:when test="${key == 'ee'}">
                  <a href="${value}">${value}</a>
                </c:when>
                <c:when test="${key == 'url' || key == 'cite'}">
                  <a href="http://dblp.uni-trier.de/${value}">${value}</a>
                </c:when>
                <c:otherwise>
                  ${value}
                </c:otherwise>
              </c:choose>
              <br>
            </c:forEach>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div class="panel panel-default">
    <div class="panel-heading"><h3 class="panel-title">Analytics</h3></div>
    <div class="panel-body">
      <p>You have visited this item for ${numVisited} time${numVisited > 1 ? 's' : ''}</p>
      This item has been searched for ${lit.numSearched} time${lit.numSearched > 1 ? 's' : ''}
    </div>
  </div>
</div>
</body>
</html>
