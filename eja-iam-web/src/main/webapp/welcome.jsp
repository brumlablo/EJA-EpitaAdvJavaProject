<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>E J A: Welcome page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div xmlns="http://www.w3.org/1999/xhtml" class="bs-example">
    <div class="jumbotron">
        <div class="container">
            <h1 class="text-info">Welcome to E J A - Epita Java Application!</h1>
            <br>
            <h2><p style = "color:#5A5B5E;">Welcome ${sessionScope.user}!</p></h2>
            <a href="logout">disconnect</a>
        </div>
    </div>
    <div class="container">
    <p style = "color:${statusColor};">${statusMsg}</p>
        <div class="row">
            <div class="col-xs-6">
                <h4>Identity Creation</h4>

                <p>Thanks to this action, you can create a brand new Identity, you can click on the button below to
                    begin</p>
                
                  <c:choose>
						<c:when test="${ sessionScope.role.equals('user')}">
						 	<c:set var = "disabled" scope = "session" value = "disabled=\"disabled\""/>
						 	<c:set var = "createRestrictionMsg" scope = "session" value = "Sorry, you don't have rights to create new identity."/>
						</c:when>    
						<c:otherwise>
						 	<c:set var = "disabled" scope = "session" value = ""/>
						 	<c:set var = "createRestrictionMsg" scope = "session" value = ""/>
						</c:otherwise>
				  </c:choose>
                
                <a href="createIdentity.jsp">
                <button type="button" class="btn btn-primary" <c:out value = "${disabled}"/>>Create!</button>
                </a>
                <h5 style="color:#5A5B5E;"><c:out value = "${createRestrictionMsg}"/></h5>
            </div>
            <div class="col-xs-6">
                <h4>Identity Search</h4>

                <p>Thanks to this action, you can search an identity and then access to its information. Through this
                    action, you can also modify or delete the wished identity</p>
                <a href="searchIdentity.jsp">
                	<button type="button" class="btn btn-primary">Search!</button>
                </a>
            </div>
        </div>
    </div>


</div>
</body>
</html>