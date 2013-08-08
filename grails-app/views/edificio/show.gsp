
<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'edificio.label', default: 'Edificio')}" />
	<title>Edificio</title>
</head>

<body>

<section id="show-edificio" class="first">

	<table class="table">
		<tbody>
		<sec:ifAllGranted roles="ROLE_ARDUITO">
			<tr class="prop">
				<td valign="top" class="name"><g:message code="edificio.owner.label" default="Owner" /></td>
				
				<td valign="top" class="value"><g:link controller="cuenta" action="show" id="${edificioInstance?.owner?.id}">${edificioInstance?.owner?.encodeAsHTML()}</g:link></td>
				
			</tr>
		</sec:ifAllGranted>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="edificio.direccion.label" default="Direccion" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: edificioInstance, field: "direccion")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="edificio.nombre.label" default="Nombre" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: edificioInstance, field: "nombre")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
