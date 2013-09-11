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
			
			<div class="control-group fieldcontain ${ipError?'error':''}" required>
				<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="ip" /></label>
				<div class="controls">
					<g:textField name="ip" value="${ip}"/>
				</div>
				<span class="help-inline">${ipError}</span>
			</div>
		</fieldset>
		<g:submitButton class="btn btn-primary" name="agregarCamara" value="Agregar" />
		
	</div>
	<div class="span4"></div>
</div>
<div class="form-actions">
   	<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" />
   	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
</div>
</g:form>

<div class="span4">
		<g:if test="${!camaras}">
			<div class="alert alert-info"> Camaras </div>
		</g:if>
		<g:else>
			<ul>
				<g:each in="${camaras}" var="camara" >
				<g:form>
				<g:hiddenField name="ip" value="${camara}" />
				<li> ${camara} <g:submitButton class="btn btn-primary" name="eliminarCamara" value="eliminar" /></li>
				</g:form>
				</g:each>
			</ul>
		</g:else>

	</div>
</body>

</html>
