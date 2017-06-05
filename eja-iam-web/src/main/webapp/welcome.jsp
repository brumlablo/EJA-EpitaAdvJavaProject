<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>E J A Welcome page</title>
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
                 <a href="createIdentity.jsp">
                <button class="btn">Create!</button>
                </a>
            </div>
            <div class="col-xs-6">
                <h4>Identity Search</h4>

                <p>Thanks to this action, you can search an identity and then access to its information. Through this
                    action, you can also modify or delete the wished identity</p>
                <button>Search!</button>
            </div>
        </div>
    </div>


</div>
</body>
</html>