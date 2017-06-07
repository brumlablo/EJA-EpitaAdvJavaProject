<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>E J A: Modify selected identity</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>



<form xmlns="http://www.w3.org/1999/xhtml" class="form-horizontal" role="form" method="post" action="modify">
		<div xmlns="http://www.w3.org/1999/xhtml" class="container">
        <h2 class="text-info">Identity Modification</h2>
        <p>
        <a href="searchIdentity.jsp">&lt;&lt; back</a>
        </p>
    </div>
		<div class="form-group">
            <label class="col-sm-2 control-label" for="uid">UID</label>

            <div class="col-sm-10">
                <input readonly="readonly" class="form-control" id="uid" name="uid" type="text" value="${identity.uid}" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="userName">Username</label>

            <div class="col-sm-10">
                <input readonly="readonly" class="form-control" id="userName" name="userName" type="text" value="${identity.displayName}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="email">Email</label>

            <div class="col-sm-10">
                <input class="form-control" id="email" name="email" type="email" placeholder="Email" value="${identity.email}" required/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="password">Password</label>

            <div class="col-sm-10">
                <input class="form-control" id="password" type="password" name="password" placeholder="Insert a new password or leave empty to keep the old one."/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="dob">Birthdate</label>

            <div class="col-sm-10">
                <input class="form-control" id="dob" name="dob" type="date" placeholder="yyyy-MM-dd" value="${identity.DOB}" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="userType">User role</label>

            <div class="col-sm-10">
                <select class="form-control" id="role" name="role"> 
                	<!-- multiple options selector -->
                	<option value="admin" ${identity.role == 'admin' ? 'selected' : ''}>Admin</option>
					<option value="user" ${identity.role == 'user' ? 'selected' : ''}>User</option>
                </select>
            </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default active" type="submit">Submit</button>
            </div>
        </div>
    </form>


</body>
</html>