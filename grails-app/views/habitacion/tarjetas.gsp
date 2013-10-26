<%@ page import="arduito.Edificio" %>
<%@ page import="arduito.Cuenta" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<title>Editar Tarjetas</title>
</head>

<body>
	
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Tarjeta</th>
				<th>Accion</th>
			</tr>
		</thead>
		<g:each in="${habitacion.rfeed.tarjetasConAcceso}" var="${unaTarjeta}">
			<tr>
				<td>${unaTarjeta.acceso}</td>
				<td><g:link action='eliminarTarjeta' params='[id:habitacion.id,tarjetaId:unaTarjeta.id]'><i class="icon-remove"></i></g:link></td>
			</tr>
		</g:each>
	</table>
	<g:link action="list" role="button" class="btn btn-primary">Volver</g:link>
	<div id="nueva-modal"
		class="modal hide fade in">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<H4>Nueva tarjeta</H4>
		</div>
		<div class="modal-body ">
			
			<g:form action="nuevaTarjeta" class="form-inline">
				<g:hiddenField name="id" value="${habitacion.id}"/>
				 <div class="form-group">
    				<label for="acceso">Numero Tarjeta</label>
					<g:textField name="acceso"/>
					<g:submitButton name="Agregar" class="btn btn-primary"/>
				</div>
			</g:form>
			
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Cerrar</a>
		</div>
	</div>
<script type="text/javascript">
$(function() {
	$('#btn-listado').hide();
	$('#btn-nuevo').attr('href','#nueva-modal');
	$('#btn-nuevo').attr('data-toggle','modal');
	if(${error}){
		$('#mensaje-generico').removeClass('alert-info');
		$('#mensaje-generico').addClass('alert-error');
	}
	
	
	
});
</script>
</body>
</html>
