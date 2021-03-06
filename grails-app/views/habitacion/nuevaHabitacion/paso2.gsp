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
	input {
		width: 160px;
	}
	.unidades{
		margin-left:10px;
		right:0px;
	}
	</style>
</head>

<body>
<g:form  class="form-horizontal">
<g:hiddenField name="id" value="${paso2Command?.id}"/>
<g:hiddenField name="uuid" value="${paso2Command?.uuid}"/>
<div class="row">
	<div class="span8">
		<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'tipo', 'error')}" required>
			<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Cuenta" /></label>
			<div class="controls">
				<g:select id="tipo" name="tipo" from="${arduito.Sensor.list()}" optionKey="id" 
				class="many-to-one" noSelection="['null': 'Seleccione un sensor']" value="${paso2Command?.tipo }"/>
				<span class="help-inline">${hasErrors(bean: paso2Command, field: 'tipo', 'Debes seleccionar el tipo de sensor')}</span>
			</div>
		</div>
		<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorMaximo', 'error')}" required>
			<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor máximo" /></label>
			<div class="controls">
				<g:textField name="valorMaximo" id="valorMaximo" value="${paso2Command?paso2Command?.valorMaximo.toString().replace('.',','):''}"/><span class='unidades'></span>
			</div>
			<span class="help-inline">
				<g:renderErrors bean="${paso2Command}" field="valorMaximo" as="list" />
			</span>
		</div>
		<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorMinimo', 'error')}" required>
			<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor mínimo" /></label>
			<div class="controls">
				<g:textField name="valorMinimo" value="${paso2Command?paso2Command?.valorMinimo.toString().replace('.',','):''}" /><span class='unidades'></span>
			</div>
			<span class="help-inline" style="with:150px;">
				<g:renderErrors bean="${paso2Command}" field="valorMinimo" as="list" />
			</span>
		</div>
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
		<div class="control-group fieldcontain ${hasErrors(bean: paso2Command, field: 'valorAlerta', 'error')}" required>
			<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Valor Alerta" /></label>
			<div class="controls">
				<g:textField name="valorAlerta" id="valorAlerta" value="${(paso2Command&&paso2Command.valorAlerta)?paso2Command?.valorAlerta.toString().replace('.',','):''}"/><span class='unidades'></span>
			</div>
			<span class="help-inline" >
				<g:renderErrors bean="${paso2Command}" field="valorAlerta" as="list" />
			</span>
		</div>
		<g:submitButton class="btn btn-primary" name="agregarSensor" value="${paso2Command?.id?'Editar':'Agregar'}" id="agregar"/>
	</div>
	
	<div class="span4">
	
		<g:if test="${!sensores}">
			<div class="alert alert-info" style="text-align: center"> Agregar Sensores para contunuar </div>
		</g:if>
		<g:else>
			
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Sensores Agregados</th>
					</tr>
					<tr>
						<th>tipo</th>
						<th>valores</th>
						<th>acciones</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${sensores}" var="unSensor">
					<tr>
						<td>${unSensor.nombre}</td>
						<td>${unSensor.min + '-' +unSensor.max}</td>
						<td>
							<g:if test="${unSensor.id}">
								<g:link event="cambiarEstadoSensor" params="[id:unSensor.id,estado:unSensor.estado]"><i class="${unSensor.estado?'icon-ok-sign':'icon-minus-sign'}"></i></g:link>
							</g:if>
							<g:else>
								<i class="icon-remove-sign"></i>
							</g:else>
							<g:link event="editarSensor" params="[uuid:unSensor.uuid]"><i class="icon-edit"></i></g:link>
							<g:link event="eliminarSensor" params="[uuid:unSensor.uuid]"><i class="icon-remove"></i></g:link>
							
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
		</g:else>
		
	</div>
</div>
<div class="form-actions">
	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>

<g:each in="${sensoresValores}" var="sensor">
	<g:hiddenField name="sensor_${sensor.key}" id="sensor_${sensor.key}"
	value="${sensor.value.valorMaximo}:${sensor.value.valorMinimo}:${sensor.value.unidades}"/>
</g:each>
<script type="text/javascript">
$(function() {

		
	$('#tipo').on('change',function(){
		var sensor = $('#tipo').val()
		var valores = $('#sensor_'+sensor).val().split(':')
		$('#valorMaximo').val(valores[0]);
		$('#valorMinimo').val(valores[1]);
		$('.unidades').html(valores[2]);
		});
	
});
</script>
</body>

</html>
