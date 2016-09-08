<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Digital Library</title>
<%@ include file="style.html" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2-bootstrap-theme/0.1.0-beta.6/select2-bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<body>
<div class="container">

  <c:set var="cp" value="1" scope="request" />
  <%@ include file="nav.jsp" %>

  <c:choose>
  <c:when test="${param.adv == null || param.adv != '1'}">
  <div id="simpleSearch">
    <h2>Search</h2>
    <p><a href="home?adv=1" class="toggle-search">Shift to Advanced Search</a></p>
    <div class="row">
      <div class="col-md-12">
        <form id="simpleSearchForm" class="form-inline" action="search" method="get">
          <div class="form-group" style="width:79.5%">
            <input class="form-control" type="text" name="keyword" placeholder="Keywords (Title, Author, Journal, etc.)" style="width:100%">
          </div>
          <button class="btn btn-default" style="width:20%">Search</button>
        </form>
      </div>
    </div>
  </div>
  </c:when>
  
  <c:otherwise>
  <div id="advancedSearch">
    <h2>Search</h2>
    <p><a href="home" class="toggle-search">Back to Simple Search</a></p>
    <div class="row">
      <div class="col-md-12">
        <form id="advSearchForm" action="search" method="get">
          <div class="form-group">
            <label for="inputTitle">Title</label>
            <input class="form-control" type="text" name="title" id="inputTitle">
          </div>
          <div class="form-group">
            <label for="inputAuthor">Author</label>
            <input class="form-control" type="text" name="author" id="inputAuthor">
          </div>
          <div class="form-group">
            <label for="inputJournal">Venue</label>
            <input class="form-control" type="text" name="venue" id="inputJournal">
          </div>
          <div class="form-group">
            <label for="inputYear">Year</label>
            <div class="input-group input-datarange">
            <input class="form-control" type="text" name="yearfrom" id="inputYearFrom">
            <span class="input-group-addon">to</span>
            <input class="form-control" type="text" name="yearto" id="inputYearTo">
            </div>
          </div>
          <div class="form-group">
            <label for="inputType">Type</label>
            <select class="js-example-basic-multiple form-control" name="type" id="inputType" multiple="multiple">
              <option value="article">article</option>
              <option value="inproceedings">inproceedings</option>
              <option value="proceedings">proceedings</option>
              <option value="book">book</option>
              <option value="incollection">incollection</option>
              <option value="phdthesis">phdthesis</option>
              <option value="masterthesis">masterthesis</option>
              <option value="www">www</option>
            </select>
          </div>
          <button class="btn btn-default" style="width:20%">Search</button
        </form>
      </div>
    </div>
  </div>
  </c:otherwise>
  </c:choose>
  
  <div class="row">
    <div class="col-md-12">
      <h2>Random 10</h2>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <ul class="list-group">
        <c:forEach var="lit" items="${literature}">
          <li class="list-group-item">
            <p>
            <span class="label label-default">${lit.type}</span>
            <c:forEach var="author" items="${lit.attr.author}" varStatus="status">
              <span class="author">${author}</span>${status.last ? ':' : ','}
            </c:forEach>
            </p>
            <a href="search?action=info&id=${lit.id}"><span class="title">${lit.attr.title[0]}</span></a>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>

<script type="text/javascript">
/* $("a.toggle-search").click(function() {
    $("#simpleSearch").toggle();
    $("#advancedSearch").toggle();
}); */

$("#inputYearFrom").datepicker({
    format: "yyyy",
    minViewMode: 2,
    autoclose: true
}).on('changeDate', function(e){
    var startDate =  $("#inputYearFrom").val();
    var inputYearTo = $('#inputYearTo');
    inputYearTo.datepicker('setStartDate', startDate);
    if (inputYearTo.val() && parseInt(inputYearTo.val()) < parseInt(startDate)) {
    	inputYearTo.val(startDate);
    }
});

$("#inputYearTo").datepicker({
    format: "yyyy",
    minViewMode: 2,
    autoclose: true
});

$(".js-example-basic-multiple").select2({
    width: "100%"
});

$("#simpleSearchForm").submit(function(e) {
    var inputKeyword = $(this).find("input[name='keyword']");
    if (inputKeyword.val().startsWith("type:")) {
        var type = inputKeyword.val().substr(5).trim();
        location.href = "search?action=result&type=" + type;
        e.preventDefault();
    }
});
</script>
</div>
</body>
</html>
