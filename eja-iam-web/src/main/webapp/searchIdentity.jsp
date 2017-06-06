<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>E J A: Search identity</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function reloadPage() {
		window.location = "searchIdentity.jsp";
	}
</script>
</head>
<body>
<div xmlns="http://www.w3.org/1999/xhtml" class="bs-example">
    <div class="container">
        <h2 class="text-info">Identity Search </h2>
        <p>
        <a href="welcome.jsp">&lt;&lt; back</a>
		</p>
        <h3 class="text-info">Search Criteria</h3>
    </div>
	<c:choose>
		<c:when test="${ sessionScope.role.equals('user')}">
		 	<c:set var = "disabled" scope = "session" value = "disabled=\"disabled\""/>
		 	<c:set var = "createRestrictionMsg" scope = "session" value = "Sorry, you don't have rights to modify or delete identity."/>
		</c:when>    
		<c:otherwise>
		 	<c:set var = "disabled" scope = "session" value = ""/>
		 	<c:set var = "createRestrictionMsg" scope = "session" value = ""/>
		</c:otherwise>
	</c:choose>

    <form class="form-horizontal" role="form" action="search" method="post">
        <div class="form-group">
            <div class="col-sm-offset-1 col-md-10">
                <input type="text" class="form-control" name="searchCriteria" id="searchCriteriaInput" placeholder="Search identity by its login, e-mail or birth date..." />
        	</div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-9 text-right">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </div>
    </form>
    
    <div class="container">
        <h3 class="text-info">Search Results</h3>
		<form class="form-horizontal" method="post" action="control">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Selection</th>
                        <th>UID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Birth date</th>
                    </tr>
                    </thead>
                    
                    <tbody>
                    <c:forEach items="${identities}" var="id">
					<tr>      
						<td> <input name="selection" type="radio" value="${id.uid}" <c:out value = "${disabled}"/> required/></td>
						<td>${id.uid}</td>
						<td>${id.displayName}</td>
						<td>${id.email}</td>
						<td>${id.DOB}</td>  
					</tr>
					</c:forEach>
                   	
                    </tbody>
                </table>
            </div>
            <div class="form-group">
	        	<div class="col-sm-offset-2 col-sm-10 text-right">
		            <button type="submit" class="btn btn-success" name="action" value="update" <c:out value = "${disabled}"/>>Modify</button>
		            <button type="submit" class = "btn btn-danger"name="action" value="delete" <c:out value = "${disabled}"/>>Delete</button>
		            <button type="button" class="btn btn-default" id="cancelButton" onclick="javascript:reloadPage()">Cancel</button>
		            <h5 style="color:#5A5B5E;"><c:out value = "${createRestrictionMsg}"/></h5>
	            </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>