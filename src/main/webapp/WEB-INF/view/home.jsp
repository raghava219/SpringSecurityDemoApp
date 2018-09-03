<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>luv2code Company Home Page</title>
</head>
<body>
	<h2>Luv2code Company Home Page - Yahoo!!!</h2>
	
	<hr /> 
	<p>
	Welcome to Luv2 code company home page!
	</p>
	
	<hr/>
	<!-- display username and roles -->
	<p>
		User: <security:authentication property="principal.username"/>
		<br/><br/>
		Role(s): <security:authentication property="principal.authorities"/>
	</p>
	<hr/>
	<!-- Add a link to point to /leaders ... this is for managers -->
	<security:authorize access="hasRole('MANAGER')">
	<p>
		<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>(Only for Manager Peeps)
	</p>
	</security:authorize>
	
	<!-- Add a link to point to /systems ... this is for admin -->
	<security:authorize access="hasRole('ADMIN')">
	<p>
		<a href="${pageContext.request.contextPath}/systems">Admins Meeting</a>(Only for System Admin)
	</p>
	</security:authorize>
	 
	<hr/>
	<form:form action="${pageContext.request.contextPath}/logout"
			   method="POST">

		<input type="submit" value="Logout" />

	</form:form>
</body>
</html>