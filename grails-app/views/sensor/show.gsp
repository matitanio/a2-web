
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

<section id="show-sensor" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="sensor.tipo.label" default="Tipo" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: sensorInstance, field: "tipo")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="sensor.descripcion.label" default="Descripcion" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: sensorInstance, field: "descripcion")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
