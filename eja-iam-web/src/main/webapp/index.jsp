<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>E J A: Login page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
	<h2 class="title">EJA Epita Java Application - log in!</h2>
		
		<br>
		<p style = "color:${loginMsgColor};">${loginMsg}</p>
		<br>
		<form action="authenticate" method="post" role="form">
			<div class="form-group">
				<label>Login</label>
				<input class="form-control" name="login" type="text" placeholder="Login">
			</div>
			<div class="form-group">
				<label>Password</label>
				<input class="form-control" name="pwd" type="password" placeholder="Password" >
			</div>
			<button type="submit" class="btn btn-default active">Login</button>
		</form>
	</div>
</body>
</html>