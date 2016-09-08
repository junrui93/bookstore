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
  <c:set var="cp" value="4" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Shopping Cart</h2>
  
  <c:choose>
  <c:when test="${literature.isEmpty()}">
  <div class="alert alert-danger" role="alert">
    Your shopping cart is <strong>empty!</strong>
  </div>
  </c:when>
  
  <c:otherwise>
  <p>Page ${page} of ${size} item${size>1?'s':''} in your shopping cart</p>
  <form id="removeForm" action="search">
  <ul class="list-group">
    <c:forEach var="lit" items="${literature}">
        <li class="list-group-item">
          <div class="row">
            <div class="col-md-1 col-sm-1 col-xs-1">
              <div class="row">
                <div class="col-md-9 col-md-offset-3">
                  <input type="checkbox" name="cart" value="${lit.id}" style="margin-top:20px"/>
                </div>
              </div>
            </div>
            <div class="col-md-11 col-sm-11 col-xs-11">
              <p>
                <span class="label label-default">${lit.type}</span>
                <c:forEach var="author" items="${lit.attr.author}" varStatus="status">
                  <span class="author">${author}</span>${status.last ? ':' : ','}
                </c:forEach>
              </p>
              <a href="search?action=info&id=${lit.id}" target="_blank"><span class="title">${lit.attr.title[0]}</span></a>
            </div>
          </div>
        </li>
     </c:forEach>
  </ul>
  <input name="action" value="removecart" hidden="true" />
  <button id="remove" class="btn btn-default">Remove</button>
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
      <c:forEach var="p" begin="${leftpage}" end="${rightpage}">
        <li class="${p == page ? 'active' : ''}"><a href="${p}" class="${p == page ? 'inactive' : ''}">${p}</a></li>
      </c:forEach>
      <li class="${page == totalpages ? 'disabled' : ''}">
        <a href="${page<totalpages ? page+1 : totalpages}" aria-label="Next" class="${page == totalpages ? 'inactive' : ''}">
          <span aria-hidden="true">&rarr;</span>
        </a>
      </li>
    </ul>
  </nav>
  </c:otherwise>
  </c:choose>
  
  <form id="pageForm" action="search" method="get" hidden="true">
    <input name="action" value="cart" />
    <input name="page" value="${page}" />
  </form>
</div>

<script type="text/javascript">
$("ul.pagination a").click(function(e) {
    e.preventDefault();
    $("#pageForm input[name='page']").val($(this).attr("href"));
    $("#pageForm").submit();
});

$("#checkall").click(function(e) {
	e.preventDefault();
    $("input[type='checkbox']").prop("checked", true);
});

$("#uncheckall").click(function(e) {
	e.preventDefault();
    $("input[type='checkbox']").prop("checked", false);
});

$("#removeForm").submit(function(e) {
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
