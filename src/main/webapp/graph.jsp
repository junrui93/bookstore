<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    var resultList = []
  	
    function draw(graph) {
      // create people.
      // value corresponds with the age of the person

      /*
      nodes = [
				{id: 1,  value: 2,  label: 'Algie' },
				{id: 2,  value: 31, label: 'Alston'},
				{id: 3,  value: 12, label: 'Barney'},
			];
      edges = [
				{from: 1, to: 2, value: 3, title: '3 emails per week'},
				{from: 1, to: 3, value: 5, title: '5 emails per week'},
			];
      */

      nodes = graph.nodes;
      edges = graph.links;

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
<body>

<div class="container">

<%@ include file="nav.jsp" %>
<h2>Graph Search</h2>

<form id="searchForm" action="/graph" method="POST">
<div class="row">
  <div class="col-md-2">
    <select class="form-control" name="type">
      <option value="0">publication</option>
      <option value="1">author</option>
      <option value="2">venue</option>
    </select>
  </div>
  <div class="col-md-10">
    <div class="input-group">
      <input type="text" class="form-control" name="keyword">
      <div class="input-group-btn">
        <button class="btn btn-default">Search</button>
      </div>
    </div>
  </div>


</div>
</form>

<!-- <svg width="960" height="600" style="border:1px solid;"></svg> -->
<div id="mynetwork"></div>
</div>

<script src="https://d3js.org/d3.v4.min.js"></script>
<script>

$("#searchForm").submit(function(e) {
    e.preventDefault();
    $.post('/graph', $(this).serialize(), function(data) {
        console.log(data);
        draw(data);
        if (data.nodes.length == 0) {
            alert("nothing found");
        }
    });
});

//d3.json("static/miserables.json", function(error, graph) {
//  if (error) throw error;
function render(graph) {

    var svg = d3.select("svg"),
        width = +svg.attr("width"),
        height = +svg.attr("height");

    svg.selectAll("*").remove();

    var color = d3.scaleOrdinal(d3.schemeCategory20);

    var simulation = d3.forceSimulation()
        .force("link", d3.forceLink().id(function(d) { return d.id; }))
        .force("charge", d3.forceManyBody().strength(-10))
        .force("center", d3.forceCenter(width / 2, height / 2));

    var link = svg.append("g")
          .attr("class", "links")
        .selectAll("line")
        .data(graph.links)
        .enter().append("line")
          .attr("stroke-width", function(d) { return Math.sqrt(d.value); });

    var gnodes = svg.append("g")
          .attr("class", "gnodes")
        .selectAll("g.gnode")
        .data(graph.nodes)
        .enter()
        .append("g")
          .attr("class", "gnode")
        .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended));

    var node = gnodes
        .append("circle")
          .attr("r", 5)
          .attr("fill", function(d) { return color(d.class); });

    node.append("title")
        .text(function(d) {
            var result = d.origin_id;
            if (d.title)
                result += "\n" + d.title;
            return result;
        });

    gnodes.append("text")
        .text(function(d) {
            return d.name;
        });

    simulation
        .nodes(graph.nodes)
        .on("tick", ticked);

    simulation.force("link")
        .links(graph.links);

    function ticked() {
        link
            .attr("x1", function(d) { return d.source.x; })
            .attr("y1", function(d) { return d.source.y; })
            .attr("x2", function(d) { return d.target.x; })
            .attr("y2", function(d) { return d.target.y; });

        gnodes.attr("transform", function(d) {
            return 'translate(' + [d.x, d.y] + ')';
        });
    }

    function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
    }

    function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
    }

    function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0);
        d.fx = null;
        d.fy = null;
    }
}

</script>

</body>
</html>