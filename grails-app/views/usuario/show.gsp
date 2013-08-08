
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

<section id="show-usuario" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.username.label" default="Username" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "username")}</td>
				
			</tr>
			<sec:ifAllGranted roles="ROLE_ARDUITO">		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.cuenta.label" default="Cuenta" /></td>
				
				<td valign="top" class="value"><g:link controller="cuenta" action="show" id="${usuarioInstance?.cuenta?.id}">${usuarioInstance?.cuenta?.encodeAsHTML()}</g:link></td>
				
			</tr>
			</sec:ifAllGranted>		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.email.label" default="Email" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: usuarioInstance, field: "email")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.accountExpired.label" default="Account Expired" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.accountExpired}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.accountLocked.label" default="Account Locked" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.accountLocked}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.enabled.label" default="Enabled" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.enabled}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="usuario.passwordExpired.label" default="Password Expired" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${usuarioInstance?.passwordExpired}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
