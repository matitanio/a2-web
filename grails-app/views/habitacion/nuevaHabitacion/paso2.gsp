<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
</head>

<body>
<g:form  class="form-horizontal">
<div class="row">
	<div class="span4">
		<fieldset class="form">
			<div class="control-group fieldcontain" required>
				<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Cuenta" /></label>
				<div class="controls">
					<g:select id="tipo" name="tipo" from="${arduito.Sensor.list()}" optionKey="id" 
					class="many-to-one" noSelection="['null': 'Seleccione un sensor']" value=""/>
				</div>
			</div>
			<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorMaximo', 'error')}" required>
				<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor máximo" /></label>
				<div class="controls">
					<g:textField name="valorMaximo" value="${paso2Command?.valorMaximo }"/>
				</div>
				<span class="help-inline">${hasErrors(bean: paso2Command, field: 'valorMaximo', 'error')}</span>
			</div>
			<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorMinimo', 'error')}" required>
				<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor mínimo" /></label>
				<div class="controls">
					<g:textField name="valorMinimo" />
				</div>
				<span class="help-inline">${hasErrors(bean: paso2Command, field: 'valorMinimo', 'error')}</span>
			</div>
			
		</fieldset>
		<g:submitButton class="btn btn-primary" name="agregarSensor" value="Agregar" />
		
	</div>
	<div class="span4"></div>
	<div class="span4">
	${sensores}
		<g:if test="${!sensores}">
			<div class="alert alert-info"> Agregar Sensores para contunuar </div>
		</g:if>
		<g:else>
			<ul>
				<g:each in="${sensores}" var="sensor">
				<li> ${sensor.tipo + ' ' + sensor.min + ' ' +sensor.max}</li>
				</g:each>
			</ul>
		</g:else>

	</div>
</div>
<div class="form-actions">
   	<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" />
   	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
</div>
</g:form>
</body>

</html>
