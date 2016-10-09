<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Search Result - Digital Library</title>
<%@ include file="style.html" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
</head>
<body>
<div class="container">
  <c:set var="cp" value="2" scope="request" />
  <%@ include file="nav.jsp" %>

  <h2>Publication Management</h2>

  <ul class="nav nav-tabs" style="margin-bottom:15px;">
    <li role="presentation" class="${param.action == 'add' ? 'active' : ''}"><a href="seller?action=add">Add</a></li>
    <li role="presentation" class="${param.action == 'remove' ? 'active' : ''}"><a href="seller?action=remove">Pause</a></li>
    <li role="presentation" class="${param.action == 'restore' ? 'active' : ''}"><a href="seller?action=restore">Restore</a></li>
  </ul>

  <c:if test="${param.action == 'add'}">
    <form id="addForm" action="/seller/add" method="POST" enctype="multipart/form-data">
      <div class="form-group">
        <label for="inputType">Type</label>
            <select id="inputType" class="form-control" name="typeId">
              <option value="0">article</option>
              <option value="1">inproceedings</option>
              <option value="2">proceedings</option>
              <option value="3">book</option>
              <option value="4">incollection</option>
              <option value="5">phdthesis</option>
              <option value="6">masterthesis</option>
              <option value="7">www</option>
            </select>
      </div>
      <div class="form-group">
        <label for="inputTitle">Title</label>
        <input id="inputTitle" class="form-control" name="title" required>
      </div>
      <div id="form-group-authors" class="form-group">
        <label for="inputAuthors">Authors</label>
        <div class="input-group">
          <input id="inputAuthors" class="form-control" name="authors" required>
          <span class="input-group-btn">
            <button class="btn btn-default btn-add" type="button" style="width:40px;">+</button>
          </span>
        </div>
      </div>
      <div class="form-group">
        <label for="inputVenue">Venue</label>
        <input id="inputVenue" class="form-control" name="venue" required>
      </div>
      <div class="form-group">
        <label for="inputYear">Year</label>
        <input id="inputYear" class="form-control" name="year" required>
      </div>
      <div class="form-group">
        <label for="inputDescription">Description</label>
        <textarea id="inputDescription" class="form-control" name="description"></textarea>
      </div>
      <div class="form-group">
        <label for="inputPrice">Price</label>
        <input id="inputPrice" class="form-control" name="price" required>
      </div>
      <div class="form-group">
        <label for="inputImage">Image</label>
        <input id="inputImage" class="form-control" type="file" name="image">
      </div>
      <button class="btn btn-default">Submit</button>
    </form>
    <div id="success" class="alert alert-success" role="alert" hidden>publication added</div>
    <div id="danger" class="alert alert-danger" role="alert" hidden></div>
  </c:if>

  <c:if test="${param.action == 'remove' || param.action == 'restore'}">

  <c:choose>
    <c:when test="${maxPage == 0}">
    <div class="alert alert-danger" role="alert">
      Nothing found
    </div>
    </c:when>

    <c:otherwise>
      <p>Page ${page} of ${resultSize} result${resultSize>1?'s':''}</p>
      <form id="addForm" action="search">
      <ul class="list-group">
        <c:forEach var="publ" items="${publications}">
          <li class="list-group-item">
            <div class="row">
              <div class="col-md-2">
                <img src="${publ.imagePath == null ? 'static/default.jpg' : publ.imagePath}" class="center-block" style="height:160px; max-width:100%"/>
              </div>
              <div class="col-md-10">
                <p>
                  <span class="label label-default">${publ.type}</span>
                  <a href="info?id=${publ.id}" target="_blank"><span class="title">${publ.title}</span></a>
                </p>
                <p class="author text-muted">
                  <c:forEach var="author" items="${publ.authors}" varStatus="status">
                    <span class="author">${author.name}</span>${status.last ? '' : ','}
                  </c:forEach>
                </p>
                <p class="price text-danger strong"><strong>$${publ.price}</strong></p>
                <c:if test="${param.action == 'remove'}">
                  <a class="btn btn-default remove" publicationId="${publ.id}">Pause</a>
                </c:if>
                <c:if test="${param.action == 'restore'}">
                  <a class="btn btn-default restore" publicationId="${publ.id}">Restore</a>
                </c:if>
              </div>
            </div>
          </li>
        </c:forEach>
      </ul>
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

  <form id="condForm" action="seller" method="get" hidden="true">
    <input name="page" value="${page}" />
    <input name="action" value="${param.action}" hidden/>
  </form>
  </c:if>

</div>

<script type="text/javascript">
$(function() {
    $("ul.pagination a").click(function(e) {
        e.preventDefault();
        $("#condForm input[name='page']").val($(this).attr("href"));
        $("#condForm").submit();
    });

    $('a.remove, a.restore').click(function(e) {
        e.preventDefault();
        var publicationId = $(this).attr('publicationId');
        $.post("seller/toggleremoved?id=" + publicationId, function() {
            location.reload();
        });
    });

    $('#form-group-authors').on('click', '.btn-add', function(e) {
        e.preventDefault();
        $('#form-group-authors').append('<div class="input-group"><input id="inputAuthors" class="form-control" name="authors" required><span class="input-group-btn"><button class="btn btn-default btn-add" type="button" style="width:40px;">+</button></span></div>');
        $(this).removeClass('btn-add');
        $(this).addClass('btn-remove');
        $(this).text('-');
    });

    $('#form-group-authors').on('click', '.btn-remove', function(e) {
        e.preventDefault();
        $(this).parent().parent().remove();
    });

    $("#inputYear").datepicker({
        format: "yyyy",
        minViewMode: 2,
        autoclose: true
    });

    $("#addForm").submit(function(e) {
        e.preventDefault();
        var fd = new FormData($(this)[0]);
        $.ajax({
            url: "seller/add",
            type: 'post',
            data: fd,
            contentType: false,
            processData: false,
            success: function() {
                $('#success').show();
                $('#danger').hide();
                $("#addForm")[0].reset();
            },
            error: function(xhr) {
                $('#success').hide();
                $('#danger').show();
                $('#danger').text(xhr.status + ' ' + xhr.statusText + ': ' + xhr.responseText);
            }
        });
        return false;
    });
});
</script>
</body>
