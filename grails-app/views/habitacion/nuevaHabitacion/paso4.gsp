<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
	<g:javascript src="bootstrap.file-input.js" />
</head>

<body>
<g:form  class="form-horizontal" enctype="multipart/form-data">
<div class="row">
	<div class="span8">
		<div class="control-group fieldcontain  ${!(errorPlano)?:'error'}">
				<label for="ip" class="control-label">Plano</label>
				<div class="controls" style="overflow:auto;">
					<input type="file" name="file" id="file" title="Selecciona el plano de tu habitacion">	
					<span class="help-inline">${errorPlano}</span>
				</div>
			</div>
	</div>
	<div class="span4">
	</div>
</div>
<div class="form-actions">
   	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>
<script type="text/javascript">


$(document).ready(function(){
	$('input[type=file]').bootstrapFileInput();
	$('.file-inputs').bootstrapFileInput();
});

</script>
</body>

</html>
