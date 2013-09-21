<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
</head>

<body>
<g:form  class="form-horizontal" enctype="multipart/form-data">
<div class="row main-flow">
	<div class="span4">
		<fieldset class="form">
			
			<div class="control-group fieldcontain ${ipError?'error':''}" required>
				<label for="cuenta" class="control-label">Selecciona el plano de tu habitacion</label>
				<span class="help-inline">${ipError}</span>
			</div>
			<div class="control-group fieldcontain ${ipError?'error':''}" required>
					<input type="file" name="file"  id="file"/>
					<span class="help-inline">${message}</span>
			</div>
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
