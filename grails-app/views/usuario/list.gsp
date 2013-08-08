
<%@ page import="arduito.Usuario" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
	<title>Usuario</title>
</head>

<body>
	
<section id="list-usuario" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="username" title="${message(code: 'usuario.username.label', default: 'Username')}" />
			
				<sec:ifAllGranted roles="ROLE_ARDUITO"><th><g:message code="usuario.cuenta.label" default="Cuenta" /></th></sec:ifAllGranted>
			
				<g:sortableColumn property="email" title="${message(code: 'usuario.email.label', default: 'Email')}" />
			
				<g:sortableColumn property="accountExpired" title="${message(code: 'usuario.accountExpired.label', default: 'Account Expired')}" />
			
				<g:sortableColumn property="accountLocked" title="${message(code: 'usuario.accountLocked.label', default: 'Account Locked')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${usuarioInstanceList}" status="i" var="usuarioInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${usuarioInstance.id}">${fieldValue(bean: usuarioInstance, field: "username")}</g:link></td>
			
				<sec:ifAllGranted roles="ROLE_ARDUITO"><td>${fieldValue(bean: usuarioInstance, field: "cuenta")}</td></sec:ifAllGranted>
			
				<td>${fieldValue(bean: usuarioInstance, field: "email")}</td>
			
				<td><g:formatBoolean boolean="${usuarioInstance.accountExpired}" /></td>
			
				<td><g:formatBoolean boolean="${usuarioInstance.accountLocked}" /></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${usuarioInstanceTotal}" />
	</div>
</section>

</body>

</html>
