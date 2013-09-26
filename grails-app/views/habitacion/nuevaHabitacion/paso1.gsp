<%@ page import="arduito.Edificio" %>
<%@ page import="arduito.Cuenta" %>
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
					<span class="help-inline">${hasErrors(bean: paso1Command, field: 'cuenta', 'Debes seleccionar una cuenta')}</span>
				</div>
			</div>
			<div id="edificios-div">
				<g:set  var="edificios" value="${((paso1Command?.cuenta)?(Edificio.findAllByOwner(Cuenta.get(paso1Command?.cuenta as Long))):[])}"/>
				<g:render template="edificios" model='[edificios:edificios,edificio:paso1Command?.edificio]'/>
			</div>
			<div class="control-group fieldcontain  ${hasErrors(bean: paso1Command, field: 'ip', 'error')}">
				<label for="ip" class="control-label">Ip</label>
				<div class="controls">
					<g:textField name="ip" id="ip" 
					value="${paso1Command?.ip}"/>
					<span class="help-inline">${hasErrors(bean: paso1Command, field: 'ip', 'La ip de la habitacion no es valida')}</span>
				</div>
			</div>
		</div>
		<div class="span2"></div>
		<div class="span4">
			<div class="control-group fieldcontain  ${hasErrors(bean: paso1Command, field: 'piso', 'error')}">
				<label for="piso" class="control-label">Piso</label>
				<div class="controls">
					<g:textField name="piso" id="piso" 
					value="${paso1Command?.piso}"/>
					<span class="help-inline">${hasErrors(bean: paso1Command, field: 'piso', 'error')}</span>
				</div>
			</div>
			<div class="control-group fieldcontain  ${hasErrors(bean: paso1Command, field: 'numero', 'error')}">
				<label for="numero" class="control-label">Numero</label>
				<div class="controls">
					<g:textField name="numero" id="numero" 
					value="${paso1Command?.numero}"/>
					<span class="help-inline">${hasErrors(bean: paso1Command, field: 'numero', 'Debe ingresar un numero de habitacion')}</span>
				</div>
			</div>
			<div class="control-group fieldcontain  ${hasErrors(bean: paso1Command, field: 'rfid', 'error')}">
				<label for="rfid" class="control-label">Rfid</label>
				<div class="controls">
					<bs:checkBox name="rfid" id="rfid" 
					value="${paso1Command?.rfid}"/>
					<span class="help-inline">${hasErrors(bean: paso1Command, field: 'rfid', 'error')}</span>
				</div>
			</div>
		
		</div>
		
	</div>
			<div class="form-actions">
				
			    	<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" />
			    	<g:if test="${resumen}">
						<g:submitButton class="btn btn-primary" name="resumen" value="Resumen" />
					</g:if>
			    	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
				
			</div>
</g:form>
</body>

</html>
