<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="home">Digital Library</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="graph">Graph Search</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="register">Sign up</a></li>
        <c:choose>
        <c:when test="${user == null}">
          <li><a href="#" role="button" data-toggle="modal" data-target="#login-modal">Log in</a></li>
        </c:when>
        <c:otherwise>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user.username} <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="profile">Profile</a></li>
              <li><a href="password">Change Password</a></li>
              <li role="separator" class="divider"></li>
              <c:if test="${user.type == 0}">
                <li><a href="cart">Shopping Cart</a></li>
              </c:if>
              <c:if test="${user.type == 1}">
                <li><a href="seller?action=add">Publication Management</a></li>
              </c:if>
              <li role="separator" class="divider"></li>
              <li><a id="logout" href="logout">Logout</a></li>
            </ul>
          </li>
        </c:otherwise>
        </c:choose>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<!-- BEGIN # MODAL LOGIN -->
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
	  <div class="modal-header" align="center">
	    <img class="img-circle" id="img_logo" src="http://bootsnipp.com/img/logo.jpg">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        </button>
      </div>

  <!-- Begin # DIV Form -->
  <div id="div-forms">

    <!-- Begin # Login Form -->
    <form id="login-form">
      <div class="modal-body">
        <div id="div-login-msg">
          <div id="icon-login-msg" class="glyphicon glyphicon-chevron-right"></div>
          <span id="text-login-msg">Login</span>
        </div>
        <input id="login_username" class="form-control" type="text" name="username" placeholder="Username" required>
        <input id="login_password" class="form-control" type="password" name="password" placeholder="Password" required>
      </div>
      <div class="modal-footer">
        <div>
          <button type="submit" class="btn btn-primary btn-lg btn-block">Login</button>
        </div>
        <div>
          <button id="login_lost_btn" type="button" class="btn btn-link">Lost Password?</button>
        </div>
      </div>
    </form>
    <!-- End # Login Form -->

    <!-- Begin | Lost Password Form -->
    <form id="lost-form" style="display:none;">
      <div class="modal-body">
        <div id="div-lost-msg">
          <div id="icon-lost-msg" class="glyphicon glyphicon-chevron-right"></div>
          <span id="text-lost-msg">Type your e-mail.</span>
        </div>
        <input id="lost_email" class="form-control" type="text" placeholder="E-Mail (type ERROR for error effect)" required>
      </div>
      <div class="modal-footer">
        <div>
          <button type="submit" class="btn btn-primary btn-lg btn-block">Send</button>
        </div>
        <div>
          <button id="lost_login_btn" type="button" class="btn btn-link">Log In</button>
        </div>
      </div>
    </form>
    <!-- End | Lost Password Form -->

  </div>
  <!-- End # DIV Form -->

  </div>
  </div>
</div>
<!-- END # MODAL LOGIN -->


<script>
/* #####################################################################
   #
   #   Project       : Modal Login with jQuery Effects
   #   Author        : Rodrigo Amarante (rodrigockamarante)
   #   Version       : 1.0
   #   Created       : 07/29/2015
   #   Last Change   : 08/04/2015
   #
   ##################################################################### */

$(function() {

    $("#logout").click(function (e) {
        e.preventDefault();
        $.post("logout", function() {
            location.reload();
        });
    });

    var $formLogin = $('#login-form');
    var $formLost = $('#lost-form');
    var $formRegister = $('#register-form');
    var $divForms = $('#div-forms');
    var $modalAnimateTime = 300;
    var $msgAnimateTime = 150;
    var $msgShowTime = 2000;

    $("#login-form").submit(function () {
        var $lg_username=$('#login_username').val();
        var $lg_password=$('#login_password').val();
        $.post("login", $(this).serialize(), function() {
            msgChange($('#div-login-msg'), $('#icon-login-msg'), $('#text-login-msg'), "success", "glyphicon-ok", "Login OK");
            location.reload();
        })
        .fail(function(xhr) {
            msgChange($('#div-login-msg'), $('#icon-login-msg'), $('#text-login-msg'), "error", "glyphicon-remove", xhr.responseText);
        });
        return false;
    });

    $("#lost-form").submit(function () {
        var $ls_email=$('#lost_email').val();
        if ($ls_email == "ERROR") {
            msgChange($('#div-lost-msg'), $('#icon-lost-msg'), $('#text-lost-msg'), "error", "glyphicon-remove", "Send error");
        } else {
            msgChange($('#div-lost-msg'), $('#icon-lost-msg'), $('#text-lost-msg'), "success", "glyphicon-ok", "Send OK");
        }
        return false;
    });

    $('#login_lost_btn').click( function () { modalAnimate($formLogin, $formLost); });
    $('#lost_login_btn').click( function () { modalAnimate($formLost, $formLogin); });

    function modalAnimate ($oldForm, $newForm) {
        var $oldH = $oldForm.height();
        var $newH = $newForm.height();
        $divForms.css("height",$oldH);
        $oldForm.fadeToggle($modalAnimateTime, function(){
            $divForms.animate({height: $newH}, $modalAnimateTime, function(){
                $newForm.fadeToggle($modalAnimateTime);
            });
        });
    }

    function msgFade ($msgId, $msgText) {
        $msgId.fadeOut($msgAnimateTime, function() {
            $(this).text($msgText).fadeIn($msgAnimateTime);
        });
    }

    function msgChange($divTag, $iconTag, $textTag, $divClass, $iconClass, $msgText) {
        var $msgOld = $divTag.text();
        msgFade($textTag, $msgText);
        $divTag.addClass($divClass);
        $iconTag.removeClass("glyphicon-chevron-right");
        $iconTag.addClass($iconClass + " " + $divClass);
        setTimeout(function() {
            msgFade($textTag, $msgOld);
            $divTag.removeClass($divClass);
            $iconTag.addClass("glyphicon-chevron-right");
            $iconTag.removeClass($iconClass + " " + $divClass);
  		}, $msgShowTime);
    }
});
</script>

