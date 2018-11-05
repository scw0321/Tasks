<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>

<!DOCTYPE html>
<html>
<head>


<Title>Tasks</Title>
<meta charset = "UFT-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript" src="js/app.js"></script>
</head>

<body>
   
<h1>Task:<c:out value="${task.task_name}"/></h1>
<p>Creator: <c:out value="${task.creator}"/></p>
<p>Assignee: <c:out value="${task.assignee}"/></p>
<p>Priority: <c:out value="${task.priority}"/></p>

<a href="/tasks/${task.id}/edit">Edit Task</a>

<form action="/tasks/${task.id}/edit" method="post">
    <input type="hidden" name="_method" value="edit">
    <input type="submit" value="Edit">
</form>

<form action="/tasks/${task.id}" method="post">
    <input type="hidden" name="_method" value="delete">
    <input type="submit" value="Delete">
</form>

<form action="/tasks/${task.id}" method="post">
    <input type="hidden" name="_method" value="complete">
    <input type="submit" value="Complete">
</form>


</body>
</html>
