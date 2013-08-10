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
<fieldset class="form">
	<div class="control-group fieldcontain ${hasErrors(bean: paso1Command, field: 'cuenta', 'error')}" required>
		<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Cuenta" /></label>
		<div class="controls">
			<g:select id="cuenta" name="cuenta" from="${arduito.Cuenta.list()}" optionKey="id" 
			class="many-to-one" noSelection="['null': 'Seleccione una cuenta']"
			value="${paso1Command?.cuenta}"
			 onchange="${remoteFunction(
					            controller:'habitacion', 
					            action:'edificiosPorCuenta', 
					            params:'\'id=\' + escape(this.value)', 
					            update:'edificios-div')}"/>
			<span class="help-inline">${hasErrors(bean: paso1Command, field: 'cuenta', 'error')}</span>
		</div>
	</div>
	<div id="edificios-div">
		<g:render template="edificios" model='[edificios:[]]'/>
	</div>
	<div class="control-group fieldcontain  ${hasErrors(bean: paso1Command, field: 'ip', 'error')}">
		<label for="ip" class="control-label">Ip</label>
		<div class="controls">
			<g:textField name="ip" id="ip" 
			value="${paso1Command?.ip}"/>
			<span class="help-inline">${hasErrors(bean: paso1Command, field: 'ip', 'error')}</span>
		</div>
	</div>
	<div class="form-actions">
		
	    	<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" />
	    	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
		
	</div>
</fieldset>
</g:form>
</body>

</html>
