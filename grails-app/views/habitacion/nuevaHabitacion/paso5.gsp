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
<div class="row main-flow">
	<div class="span4">
		aca va el mapa
	</div>
	<div class="span4"></div>
</div>
<div class="form-actions">
   	<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" />
   	<g:submitButton class="btn btn-primary" name="atras" value="Atras" />
   	<g:link class="btn btn-danger" role="button" name="cancelar" controller="home">Cancelar</g:link>
</div>
</g:form>

</body>

</html>
