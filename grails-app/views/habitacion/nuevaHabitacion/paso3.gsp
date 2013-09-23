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
			
			<div class="control-group fieldcontain ${ipError?'error':''}" required>
				<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="ip" /></label>
				<div class="controls">
					<g:textField name="ip" value="${ip}"/>
				</div>
				<span class="help-inline">${ipError}</span>
			</div>
		</fieldset>
		<g:submitButton class="btn btn-primary" name="agregarCamara" value="Agregar" id="agregar"/>
		
	</div>
	<div class="span4"></div>
</div>
<div class="form-actions">
	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>

<div id="camaras">
		<g:if test="${!camaras}">
			<div class="alert alert-info" style="text-align: center"> Agregar camaras para continuar</div>
		</g:if>
		<g:else>
			<h4>Camaras agregadas</h4>
			<ul>
				<g:each in="${camaras}" var="camara" >
					<li> ${camara.ip}<g:link event="eliminarCamara" params="[ip:camara.ip]"><i class="icon-remove"></i></g:link></li>
				</g:each>
			</ul>
		</g:else>

	</div>
</body>

</html>
