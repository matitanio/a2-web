<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
	<style>
	#agregar{
			position:relative;
			left: 179px;
		}
	</style>
</head>

<body>
<g:form  class="form-horizontal">
<div class="row">
	<div class="span4">
		<div class="row">
			<div class="span4">
				<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'tipo', 'error')}" required>
					<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Cuenta" /></label>
					<div class="controls">
						<g:select id="tipo" name="tipo" from="${arduito.Sensor.list()}" optionKey="id" 
						class="many-to-one" noSelection="['null': 'Seleccione un sensor']" value="${paso2Command?.tipo }"/>
						<span class="help-inline">${hasErrors(bean: paso2Command, field: 'tipo', 'Debes seleccionar el tipo de sensor')}</span>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span4">
				<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorMaximo', 'error')}" required>
					<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor máximo" /></label>
					<div class="controls">
						<g:textField name="valorMaximo" id="valorMaximo" value="${paso2Command?paso2Command?.valorMaximo.toString().replace('.',','):''}"/>
					</div>
					<span class="help-inline">
						<g:renderErrors bean="${paso2Command}" field="valorMaximo" as="list" />
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span4">	
				<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'comparador', 'error')}" required>
					<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Comparador" /></label>
					<div class="controls">
						<g:select id="tipo" name="comparador" from="${['>','>=','<','<=','=']}" 
						class="many-to-one" noSelection="['-1': 'Seleccione un comparador']" value="${paso2Command?.comparador}"/>
					</div>
					<span class="help-inline" style="with:150px;">
						<g:renderErrors bean="${paso2Command}" field="comparador" as="list" />
					</span>
				</div>
			</div>
		</div>
			
		<g:submitButton class="btn btn-primary" name="agregarSensor" value="Agregar" id="agregar"/>
		
	</div>
	<div class="span4">
		<div class="row">
			<div class="span4 offset1">	
				<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorMinimo', 'error')}" required>
					<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor mínimo" /></label>
					<div class="controls">
						<g:textField name="valorMinimo" value="${paso2Command?paso2Command?.valorMinimo.toString().replace('.',','):''}" /> 
					</div>
					<span class="help-inline" style="with:150px;">
						<g:renderErrors bean="${paso2Command}" field="valorMinimo" as="list" />
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span4 offset1">	
				<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorAlerta', 'error')}" required>
					<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor Alerta" /></label>
					<div class="controls">
						<g:textField name="valorAlerta" id="valorAlerta" value="${paso2Command?paso2Command?.valorAlerta.toString().replace('.',','):''}"/>
					</div>
					<span class="help-inline" >
						<g:renderErrors bean="${paso2Command}" field="valorAlerta" as="list" />
					</span>
				</div>
			</div>
		</div>
	</div>
	<div class="span4 offset2">
	
		<g:if test="${!sensores}">
			<div class="alert alert-info" style="text-align: center"> Agregar Sensores para contunuar </div>
		</g:if>
		<g:else>
			<h4>Sensores Agregados</h4>
			<ul>
				<g:each in="${sensores}" var="unSensor">
				<li> ${unSensor.nombre +' : '+unSensor.tipo + ' ' + unSensor.min + ' ' +unSensor.max}
					<g:link event="eliminarSensor" params="[uuid:unSensor.uuid]"><i class="icon-remove"></i></g:link>
				</g:each>
			</ul>
		</g:else>
	
	</div>
		
</div>
<div class="form-actions">
	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>
	<g:each in="${sensoresValores}" var="sensor">
		<g:hiddenField name="sensor_${sensor.key}" id="sensor_${sensor.key}"
		value="${sensor.value.valorMaximo}:${sensor.value.valorMinimo}"/>
	</g:each>
<script type="text/javascript">
$(function() {

		
	$('#tipo').on('change',function(){
		var sensor = $('#tipo').val()
		var valores = $('#sensor_'+sensor).val().split(':')
		$('#valorMaximo').val(valores[0]);
		$('#valorMinimo').val(valores[1]);
		});
	
});
</script>
</body>

</html>
