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
			
			seleccion de a quien notifica cada cosa		
		</fieldset>
	</div>
	<div class="span4"></div>
</div>
<div class="form-actions">
   	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>
</body>

</html>
