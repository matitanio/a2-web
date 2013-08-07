
<%@ page import="arduito.Cuenta" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'cuenta.label', default: 'Cuenta')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-cuenta" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th><g:message code="cuenta.owner.label" default="Owner" /></th>
			
				<g:sortableColumn property="nombre" title="${message(code: 'cuenta.nombre.label', default: 'Nombre')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${cuentaInstanceList}" status="i" var="cuentaInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${cuentaInstance.id}">${fieldValue(bean: cuentaInstance, field: "owner")}</g:link></td>
			
				<td>${fieldValue(bean: cuentaInstance, field: "nombre")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${cuentaInstanceTotal}" />
	</div>
</section>

</body>

</html>
