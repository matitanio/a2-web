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

<section id="create-usuario" class="first">

	<g:hasErrors bean="${usuarioInstance}">
	<div class="alert alert-error">
		<g:renderErrors bean="${usuarioInstance}" as="list" />
	</div>
	</g:hasErrors>
	
	<g:form action="save" class="form-horizontal" >
		<fieldset class="form">
			<g:render template="form"/>
		</fieldset>
		<div class="form-actions">
			<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            <g:link class="btn" type="reset" role="button" action="list">Cancelar</g:link>
		</div>
	</g:form>
	
</section>
		
</body>

</html>
