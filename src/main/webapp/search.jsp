<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Result - Digital Library</title>
<%@ include file="style.html" %>
</head>
<body>
<div class="container">
  <c:set var="cp" value="2" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Result</h2>
  
  <c:choose>
  <c:when test="${maxPage == 0}">
  <div class="alert alert-danger" role="alert">
    <strong>Nothing found</strong>, try to search again! <a href="home">Back to home page</a>
  </div>
  </c:when>
  
  <c:otherwise>
  <p>Page ${page} of ${resultSize} result${resultSize>1?'s':''}</p>
  <form id="addForm" action="search">
  <ul class="list-group">
    <c:forEach var="publ" items="${publications}">
        <li class="list-group-item">
          <div class="row">
            <div class="col-md-1 col-sm-1 col-xs-1">
              <div class="row">
                <div class="col-md-9 col-md-offset-3">
                  <input type="checkbox" name="cart" value="${publ.id}" style="margin-top:20px" />
                </div>
              </div>
            </div>
            <div class="col-md-11 col-sm-11 col-xs-11">
              <p>
                <span class="label label-default">${publ.type}</span>
                <c:forEach var="author" items="${publ.authors}" varStatus="status">
                  <span class="author">${author.name}</span>${status.last ? ':' : ','}
                </c:forEach>
              </p>
              <a href="search?action=info&id=${publ.id}" target="_blank"><span class="title">${publ.title}</span></a>
            </div>
          </div>
        </li>
     </c:forEach>
  </ul>
  <input name="action" value="addcart" hidden="true" />
  <button id="addcart" class="btn btn-default">Add to Shopping Cart</button>
  <button id="checkall" class="btn btn-default">Check All</button>
  <button id="uncheckall" class="btn btn-default">Uncheck All</button>
  </form>

  <nav aria-label="Page navigation">
    <ul class="pagination">
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
  </c:otherwise>
  </c:choose>

  <form id="condForm" action="search" method="get" hidden="true">
    <c:choose>
      <c:when test="${param.keyword != null}">
        <input name="keyword" value="${param.keyword}" />
      </c:when>
      <c:otherwise>
        <input name="title" value="${param.title}" />
        <input name="author" value="${param.author}" />
        <input name="venue" value="${param.venue}" />
        <input name="yearfrom" value="${param.yearfrom}" />
        <input name="yearto" value="${param.yearto}" />
        <c:forEach var="type" items="${paramValues.type}">
            <input name="type" value="${type}" />
        </c:forEach>
      </c:otherwise>
    </c:choose>
    <input name="action" value="result" />
    <input name="page" value="${page}" />
  </form>
</div>

<script type="text/javascript">
$("ul.pagination a").click(function(e) {
    e.preventDefault();
    $("#condForm input[name='page']").val($(this).attr("href"));
    $("#condForm").submit();
});

$("#checkall").click(function(e) {
	e.preventDefault();
    $("input[type='checkbox']").prop("checked", true);
});

$("#uncheckall").click(function(e) {
	e.preventDefault();
    $("input[type='checkbox']").prop("checked", false);
});

$("#addForm").submit(function(e) {
    e.preventDefault();
    if ($("input[type='checkbox']:checked").length > 0) {
        $.post($(this).attr("action"), $(this).serialize(), function(data, status) {
            if (status == "success") {
                location.reload();
            }
        });
    }
});
</script>
</body>
