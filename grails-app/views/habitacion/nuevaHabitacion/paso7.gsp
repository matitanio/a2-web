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
	<div class="span8">
		<g:link event="plano"><h4>modificar ubicacion</h4></g:link>
	</div>
</div>
<div class="row">
	<div class="span4">
		<g:link event="sensores"><h4>sensores</h4></g:link>
		<g:each in="${sensores}" var="${unSensor}">
				${unSensor}
		</g:each>
	</div>
	<div class="span4">
		<g:link event="camaras"><h4>camaras</h4></g:link>
		<g:each in="${camaras}" var="${unaCamara}">
				${unaCamara}
		</g:each>
	</div>
</div>
<div class="row">
	<div class="span4">
		tiene rfeed - ir a rfeed
	</div>
	<div class="span4">
		notificacbles - <g:link event="notificaciones"><h4>cambiar</h4></g:link>
	</div>
</div>
<div class="form-actions">
   	<g:submitButton class="btn btn-primary" name="siguiente" value="Terminar" />
   	<g:submitButton class="btn btn-primary" name="atras" value="Atras" />
   	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
</div>
</g:form>
</body>

</html>
