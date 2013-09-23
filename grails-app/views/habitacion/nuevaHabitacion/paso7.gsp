<%@ page import="arduito.Edificio" %>
<%@ page import="arduito.Cuenta" %>
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
	<div class="span8">
		<g:link event="plano"><h4>modificar ubicacion</h4></g:link>
	</div>
</div>
<div class="row">
	<div class="span4">
		<g:link event="sensores"><h4>sensores</h4></g:link>
		<ul>
		<g:each in="${sensores}" var="${unSensor}">
				<li>${unSensor.nombre} (${unSensor.max} - ${unSensor.min})</li>  
		</g:each>
		</ul>
	</div>
	<div class="span4">
		<g:link event="camaras"><h4>camaras</h4></g:link>
		<ul>
		<g:each in="${camaras}" var="${unaCamara}" status="i">
			<li>Camara ${i+1} con ip:${unaCamara.ip}</li>
		</g:each>
		</ul>
	</div>
</div>
<div class="row">
	<div class="span4">
		<g:link event="general"><h4>General</h4></g:link>
		<ul>
			<li>Cuenta: ${Cuenta.get(paso1Command.cuenta)}</li>
			<li>Edificio: ${Edificio.get(paso1Command.edificio)}</li>
			<li>Rfid: ${paso1Command.rfid?'si':'no'}</li>
		</ul>
	</div>
	<div class="span4">
		<g:link event="notificaciones"><h4>Notificaciones</h4></g:link>
	</div>
</div>
<div class="form-actions">
   	<g:submitButton class="btn btn-primary" name="terminar" value="Terminar" />
   	<g:submitButton class="btn btn-primary" name="atras" value="Atras" />
   	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
</div>
</g:form>
</body>

</html>
