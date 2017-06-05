<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div xmlns="http://www.w3.org/1999/xhtml" class="bs-example">

    <div class="container">
        <h2 class="text-info">New Identity Creation </h2>
        <br>
        <a href="welcome.jsp">&lt;&lt; back</a>
        <br>
    </div>
    
    <form class="form-horizontal" role="form" action="create" method="post">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">User name</label>

            <div class="col-sm-10">
                <input required="true" type="text" class="form-control" id="username" name="username" placeholder="User name" />
            </div>
        </div>
        
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">User password</label>

            <div class="col-sm-10">
                <input required="true" type="password" class="form-control" id="password" name="password" placeholder="User password" />
            </div>
        </div>
       
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email</label>

            <div class="col-sm-10">
                <input required="true" type="email" class="form-control" id="email" name="email" placeholder="Email" />
            </div>
        </div>

        <div class="form-group">
            <label for="date" class="col-sm-2 control-label">Birth Date</label>

            <div class="col-sm-10">
                <input type="date" class="form-control" id="date" name="date" placeholder="Birth Date" />
            </div>
        </div>

		<div class="form-group">
			<label for="usertype" class="col-sm-2 control-label">Admin?</label>
				<input type="checkbox" id="usertype" name="usertype" value="admin">
			<div class="col-sm-10">
            </div>
		</div>


        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn" type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>
    </form>

</div>

</body>
</html>