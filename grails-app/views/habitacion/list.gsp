<%@ page import="arduito.Habitacion" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'cuenta.label', default: 'Cuenta')}" />
	<title>Habitacion</title>
</head>

<body>
	
<section id="list-cuenta" class="first">
<g:if test="${habitacionInstanceList.size() == 0}">
	<div class="sin-datos"> no hay Habitaciones </div>
</g:if>
<g:else>
	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th>id</th>
			
				<g:sortableColumn property="ip" title="ip" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${habitacionInstanceList}" status="i" var="habitacionInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
<%--				<td><g:link action="show" id="${cuentaInstance.id}">${fieldValue(bean: cuentaInstance, field: "owner")}</g:link></td>--%>
				<td>${habitacionInstance.id}</td>
				<td>${habitacionInstance.ip}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${habitacionInstanceTotal}" />
	</div>
</g:else>
</section>

</body>

</html>
