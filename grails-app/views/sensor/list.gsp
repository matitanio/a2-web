
<%@ page import="arduito.Sensor" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'sensor.label', default: 'Sensor')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-sensor" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="tipo" title="${message(code: 'sensor.tipo.label', default: 'Tipo')}" />
			
				<g:sortableColumn property="descripcion" title="${message(code: 'sensor.descripcion.label', default: 'Descripcion')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${sensorInstanceList}" status="i" var="sensorInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${sensorInstance.id}">${fieldValue(bean: sensorInstance, field: "tipo")}</g:link></td>
			
				<td>${fieldValue(bean: sensorInstance, field: "descripcion")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${sensorInstanceTotal}" />
	</div>
</section>

</body>

</html>
