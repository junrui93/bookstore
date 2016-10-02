<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vis/4.16.1/vis.min.css" /><!-- Vis css -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.16.1/vis.min.js"></script><!-- Vis js -->

<title>Welcome to Digital Library</title>
<%@ include file="style.html" %>

<script type="text/javascript">
    var nodes = null;
    var edges = null;
    var network = null;

    var nFrom, nEdge, nTo;
    var resultList = [];
    <c:forEach var="result" items="${searchResult}">
  		nFrom = "${result.nodeFrom}";
  		nEdge = "${result.nodeEdge}"; 
  		nTo = "${result.nodeTo}";
  	
  		resultList.push(nFrom, nEdge, nTo);
  	</c:forEach> 
  	
    function draw() {
      // create people.
      // value corresponds with the age of the person
      
      nodes = [
				{id: 1,  value: 2,  label: 'Algie' },
				{id: 2,  value: 31, label: 'Alston'},
				{id: 3,  value: 12, label: 'Barney'},
			];
      edges = [
				{from: 1, to: 2, value: 3, title: '3 emails per week'},
				{from: 1, to: 3, value: 5, title: '5 emails per week'},
			];

      // Instantiate our network object.
      var container = document.getElementById('mynetwork');
      var data = {
        nodes: nodes,
        edges: edges
      };
      var options = {
        nodes: {
          shape: 'dot',
          scaling:{
            label: {
              min:8,
              max:20
            }
          }
        }
      };
      network = new vis.Network(container, data, options);
    }
  </script>

</head>
<body onload="draw()">

<div class="container">
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
		<div class="navbar-header">     
	      <a class="navbar-brand" href="home">Digital Library</a>
	    </div>
	   </div>
	</nav>
	<h1>Graph Search</h1>
	<div class="row">
      <div class="col-md-12">
	        <form id="simpleSearchForm" class="form-inline" action="graph" method="get">
	          <div class="form-group" style="width:79.5%">
	            <input class="form-control" type="text" name="keyword" placeholder="Keywords (Author, Journal, Venue.)" style="width:100%">
	          </div>
	          <button class="btn btn-default" style="width:20%">Search</button>
	        </form>
	      </div>
    	</div>
	    
    
	<div id="mynetwork"></div>
	<c:forEach var="result" items="${searchResult}">
		<c:out value="${result.nodeFrom}->${result.nodeEdge}->${result.nodeTo}"/><br/>
    </c:forEach>
	
</div>
</body>
</html>