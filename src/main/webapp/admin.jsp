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

  <h2>Admin</h2>
  <ul class="nav nav-tabs" style="margin-bottom:15px;">
    <li role="presentation" class="${param.action == 'remove' ? 'active' : ''}"><a href="/admin?action=remove">Remove Publication</a></li>
    <li role="presentation" class="${param.action == 'user' ? 'active' : ''}"><a href="/admin?action=user">User Info</a></li>
    <li role="presentation"><a href="/admin/logout">Logout</a></li>
  </ul>

  <c:if test="${param.action == 'remove'}">

    <form action="/admin" method="get" style="margin-bottom:15px;">
      <div class="input-group">
        <input type="text" class="form-control" name="keyword" placeholder="keyword" value="${param.keyword}">
        <span class="input-group-btn">
          <button class="btn btn-default">Search</button>
        </span>
      </div>
      <input type="text" name="action" value="${param.action}" hidden>
    </form>

    <c:choose>
      <c:when test="${maxPage == 0}">
        <div class="alert alert-danger" role="alert">Nothing found</div>
      </c:when>

      <c:otherwise>

        <p>Page ${page} of ${resultSize} result${resultSize>1?'s':''}</p>

        <table class="table table-striped">
          <tr>
            <th style="width:5%">id</th>
            <th style="width:40%">title</th>
            <th style="width:30%">authors</th>
            <th style="width:10%">venue</th>
            <th style="width:5%">year</th>
            <th style="width:5%">status</th>
            <th style="width:5%">action</th>
          </tr>
          <c:forEach var="publ" items="${publications}">
            <tr>
              <td>${publ.id}</td>
              <td><a href="/info?id=${publ.id}&view=admin" target="_blank">${publ.title}</a></td>
              <td>
                <c:forEach var="author" items="${publ.authors}" varStatus="status">
                  ${author.name}${status.last ? '' : ','}
                </c:forEach>
              </td>
              <td>${publ.venue}</td>
              <td>${publ.year}</td>
              <td>${publ.removed == true ? 'paused' : ''}</td>
              <td><button class="btn btn-default remove-publ" publicationId="${publ.id}">remove</button></td>
            </tr>
          </c:forEach>
        </table>

        <nav aria-label="Page navigation">
          <ul class="pagination" style="margin:0;">
            <li class="${page == 1 ? 'disabled' : ''}">
              <a href="${page>1 ? page-1 : ''}" aria-label="Previous" class="${page == 1 ? 'inactive' : ''}">
                <span aria-hidden="true">&larr;</span>
              </a>
            </li>
            <c:forEach var="p" begin="${leftPage}" end="${rightPage}">
              <li class="${p == page ? 'active' : ''}"><a href="${p}" class="${p == page ? 'inactive' : ''}">${p}</a></li>
            </c:forEach>
            <li class="${page == maxPage ? 'disabled' : ''}">
              <a href="${page<maxPage ? page+1 : maxPage}" aria-label="Next" class="${page == totalpages ? 'inactive' : ''}">
                <span aria-hidden="true">&rarr;</span>
              </a>
            </li>
          </ul>
      </nav>

        <form id="condForm" action="admin" method="get" hidden="true">
          <input name="action" value="${param.action}" />
          <input name="keyword" value="${param.keyword}" />
          <input name="page" value="${page}" />
        </form>

      </c:otherwise>
    </c:choose>
  </c:if>

  <c:if test="${param.action == 'user'}">
    <c:choose>
      <c:when test="${customers == null || customers.isEmpty()}">
        <div class="alert alert-danger" role="alert">No customers</div>
      </c:when>
      <c:otherwise>
        <table class="table table-striped">
          <tr>
            <th style="width:5%">id</th>
            <th style="width:15%">username</th>
            <th style="width:25%">email</th>
            <th style="width:15%">nickname</th>
            <th style="width:10%">first name</th>
            <th style="width:10%">last name</th>
            <th style="width:10%">verified</th>
            <th style="width:10%">action</th>
          </tr>
          <c:forEach var="customer" items="${customers}">
            <tr class="${customer.banned == true ? 'warning' : ''}">
              <td>${customer.id}</td>
              <td><a href="/admin/userstat?id=${customer.id}" target="_blank">${customer.username}</a></td>
              <td>${customer.email}</td>
              <td>${customer.nickname}</td>
              <td>${customer.firstName}</td>
              <td>${customer.lastName}</td>
              <td>${customer.password == null ? 'false' : ''}</td>
              <td>
                <button class="btn btn-primary togglebanned" customerId="${customer.id}">
                  ${customer.banned == true ? 'restore' : 'ban'}
                </button>
              </td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>
  </c:if>


</div>

<script type="text/javascript">
$(function() {
    $("ul.pagination a").click(function(e) {
        e.preventDefault();
        $("#condForm input[name='page']").val($(this).attr("href"));
        $("#condForm").submit();
    });

    $('.togglebanned').click(function (e) {
        e.preventDefault();
        var customerId = $(this).attr('customerId');
        var thisButton = $(this);
        $.post("/admin/togglebanned?id=" + customerId, function () {
            location.reload();
        });
    });

    $('.remove-publ').click(function (e) {
        e.preventDefault();
        var publicationId = $(this).attr('publicationId');
        $.post("/admin/remove?id=" + publicationId, function () {
            location.reload();
        });
    });
});
</script>
</body>
