
<%@ page import="arduito.Sensor" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'sensor.label', default: 'Sensor')}" />
	<title>Sensor</title>
</head>
<body>
<section id="list-sensor" class="first">
<g:if test="${sensorInstanceList.size() == 0}">
	<div class="alert alert-info"> no hay sensores </div>
</g:if>
<g:else>
	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="tipo" title="${message(code: 'sensor.tipo.label', default: 'Tipo')}" />
			
				<g:sortableColumn property="descripcion" title="${message(code: 'sensor.descripcion.label', default: 'Descripcion')}" />
				
				<td>Valores Referencia</td>
			</tr>
		</thead>
		<tbody>
		<g:each in="${sensorInstanceList}" status="i" var="sensorInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${sensorInstance.id}">${fieldValue(bean: sensorInstance, field: "tipo")}</g:link></td>
			
				<td>${fieldValue(bean: sensorInstance, field: "descripcion")}</td>
				
				<td>${'De :' + sensorInstance.valorMinimo + ' ' + sensorInstance.unidades + ' a: ' + sensorInstance.valorMaximo + ' ' + sensorInstance.unidades}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${sensorInstanceTotal}" />
	</div>
</g:else>
</section>

</body>

</html>
