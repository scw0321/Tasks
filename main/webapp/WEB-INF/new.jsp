<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 
<h1>New Task</h1>

<p><c:out value="${error}"/></p>
<form:form action="/tasks/new" method="post" modelAttribute="task">

		<form:input type="hidden" path="creator"/>
    <p>
        <form:label path="task_name">Task</form:label>
        <form:errors path="task_name"/>
        <form:input path="task_name"/>
    </p>    
    <p>
    	  	
        <form:label path="assignee">Assignee: </form:label>
        <form:errors path="assignee"/>        
        <form:select path="assignee">   
        <c:forEach items="${user}" var="u">        
        	<form:option value="${u.name}"><c:out value="${u.name}"/></form:option>        
        </c:forEach>  
        </form:select>        
 		     
    </p>
    <p>
        <form:label path="priority">Priority</form:label> 
        <form:errors path="priority"/>       
        <form:select path="priority">
        	<option value="high">High</option>
        	<option value="medium">Medium</option>
        	<option value="low">Low</option>
        	</form:select>                  	
    </p>
       
    <input type="submit" value="Submit"/>
</form:form>    