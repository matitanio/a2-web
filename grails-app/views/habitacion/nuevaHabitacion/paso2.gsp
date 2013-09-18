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
<div class="row main-flow">
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
					<g:textField name="valorMaximo" id="valorMaximo" value="${paso2Command?.valorMaximo }"/>
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
		<g:submitButton class="btn btn-primary" name="agregarSensor" value="Agregar" id="agregar"/>
		
	</div>
	<div class="span4"></div>

</div>
<div class="form-actions">
   	<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" />
   	<g:submitButton class="btn btn-primary" name="atras" value="Atras" />
   	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
</div>
</g:form>
	<div id='sensores'>
		<g:if test="${!sensores}">
			<div class="alert alert-info" style="text-align: center"> Agregar Sensores para contunuar </div>
		</g:if>
		<g:else>
			<ul>
				<g:each in="${sensores}" var="sensor">
				<g:form>
				<li> ${sensor.nombre +' : '+sensor.tipo + ' ' + sensor.min + ' ' +sensor.max}
				<g:link event="eliminarSensor" params="[uuid:sensor.uuid]"><i class="icon-remove"></i></g:link>
				</g:form>
				</g:each>
			</ul>
		</g:else>

	</div>
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
