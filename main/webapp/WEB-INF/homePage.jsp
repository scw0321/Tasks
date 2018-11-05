<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welcome</title>
	<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Welcome, <c:out value="${user.name}" /></h1>
	
	<a href="#">Priority High - Low</a>
	<a href="#">Priority Low - High</a>
	
	<a href="/logout">Logout</a>

<div class="container">
             
  <table class="table table-striped">
    <thead>
        <tr>
            <th>Task</th>
            <th>Creator</th>
            <th>Assignee</th>
            <th>Priority</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${task}" var="task">
        <tr>
            <td><a href="/tasks/<c:out value="${task.id}"/>"><c:out value="${task.task_name}"/></a></td>
            <td><c:out value="${task.creator}"/></td>
            <td><c:out value="${task.assignee}"/></td>
            <td><c:out value="${task.priority}"/></td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</div>
<a href="/tasks/new">Create Task</a>



</body>

</html>