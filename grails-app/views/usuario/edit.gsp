<%@ page import="arduito.Usuario" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
	<title>Usuario</title>
</head>

<body>

<section id="edit-usuario" class="first">

	<g:hasErrors bean="${usuarioInstance}">
	<div class="alert alert-error">
		<g:renderErrors bean="${usuarioInstance}" as="list" />
	</div>
	</g:hasErrors>
	<ul class="nav nav-tabs" id="usuario-tabs">
	  <li class="active"><a href="#datos-principales"  data-toggle="tab">Datos de la cuenta</a></li>
  	 <li><a href="#roles" data-toggle="tab">Roles</a></li>	
</ul>

 <div class="tab-content">
 <div class="tab-pane active" id="datos-principales">
			<g:form method="post" class="form-horizontal" >
				<g:hiddenField name="id" value="${usuarioInstance?.id}" />
				<g:hiddenField name="version" value="${usuarioInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<div class="form-actions">
					<g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
		            <g:link role="button" class="btn" action="list">Cancelar</g:link>
				</div>
		</g:form>
	</div>
  <div id="roles" hidden="true" class="tab-pane">
	  <g:form action="actualizarRoles">
	  		<g:hiddenField name="id" value="${usuarioInstance?.id}" />
	  		<sec:ifAllGranted roles="ROLE_ARDUITO">
		  		<div class="control-group">
		  			ROL_ARTUIDO <g:checkBox name="rol" value="ROLE_ARDUITO" checked="${usuarioInstance.tienePerfil('ROLE_ARDUITO')}"/> 
		  		</div>
	  		</sec:ifAllGranted>
	  		<div class="control-group">
	  			ROL_ADMIN <g:checkBox name="rol" value="ROLE_ADMIN" checked="${usuarioInstance.tienePerfil('ROLE_ADMIN')}"/> 
 	  		</div>
 	  		<div class="control-group">
 	  			ROL_USER <g:checkBox name="rol" value="ROLE_USER" checked="${usuarioInstance.tienePerfil('ROLE_USER')}"/> 
	  		</div>
	  		<div class="form-actions">
				<g:actionSubmit class="btn btn-primary" action="actualizarRoles" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				<g:link role="button" class="btn" action="list">Cancelar</g:link>
			</div>
	  </g:form>
	 </div>	
</div>

</section>
			
</body>
<script type="text/javascript">
$(function() {

	$('#usuario-tabs a:first').tab('show');
});
</script>
</html>

