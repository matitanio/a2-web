
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
	
<section id="list-edificio" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<sec:ifAllGranted roles="ROLE_ARDUITO"><th><g:message code="edificio.owner.label" default="Owner" /></th></sec:ifAllGranted>
			
				<g:sortableColumn property="direccion" title="${message(code: 'edificio.direccion.label', default: 'Direccion')}" />
			
				<g:sortableColumn property="nombre" title="${message(code: 'edificio.nombre.label', default: 'Nombre')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${edificioInstanceList}" status="i" var="edificioInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<sec:ifAllGranted roles="ROLE_ARDUITO"><td><g:link action="show" id="${edificioInstance.id}">${fieldValue(bean: edificioInstance, field: "owner")}</g:link></td></sec:ifAllGranted>
			
				<td>${fieldValue(bean: edificioInstance, field: "direccion")}</td>
			
				<td>${fieldValue(bean: edificioInstance, field: "nombre")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${edificioInstanceTotal}" />
	</div>
</section>

</body>

</html>
