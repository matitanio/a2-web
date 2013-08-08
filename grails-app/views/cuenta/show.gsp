
<%@ page import="arduito.Cuenta" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'cuenta.label', default: 'Cuenta')}" />
	<title>Cuenta</title>
</head>

<body>

<section id="show-cuenta" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cuenta.owner.label" default="Owner" /></td>
				
				<td valign="top" class="value"><g:link controller="usuario" action="show" id="${cuentaInstance?.owner?.id}">${cuentaInstance?.owner?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cuenta.nombre.label" default="Nombre" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cuentaInstance, field: "nombre")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
