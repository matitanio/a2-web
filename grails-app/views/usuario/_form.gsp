<%@ page import="arduito.Usuario" %>
  
  	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'username', 'error')} required">
		<label for="username" class="control-label"><g:message code="usuario.username.label" default="Username" /><span class="required-indicator">*</span></label>
		<div class="controls">
			<g:textField name="username" required="" value="${usuarioInstance?.username}"/>
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'username', 'error')}</span>
		</div>
	</div>
	
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'password', 'error')} required">
		<label for="password" class="control-label"><g:message code="usuario.password.label" default="Password" /><span class="required-indicator">*</span></label>
		<div class="controls">
			<g:field type="password" name="password" required="" value="${usuarioInstance?.password}"/>
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'password', 'error')}</span>
		</div>
	</div>
	<sec:ifAllGranted roles="ROLE_ARDUITO">
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'cuenta', 'error')} ">
		<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Cuenta" /></label>
		<div class="controls">
			<g:select id="cuenta" name="cuenta.id" from="${arduito.Cuenta.list()}" optionKey="id" value="${usuarioInstance?.cuenta?.id}" class="many-to-one" noSelection="['null': '']"/>
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'cuenta', 'error')}</span>
		</div>
	</div>
	</sec:ifAllGranted>
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'email', 'error')} required">
		<label for="email" class="control-label"><g:message code="usuario.email.label" default="Email" /><span class="required-indicator">*</span></label>
		<div class="controls">
			<g:field type="email" name="email" required="" value="${usuarioInstance?.email}"/>
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'email', 'error')}</span>
		</div>
	</div>
	
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'accountExpired', 'error')} ">
		<label for="accountExpired" class="control-label"><g:message code="usuario.accountExpired.label" default="Account Expired" /></label>
		<div class="controls">
			<bs:checkBox name="accountExpired" value="${usuarioInstance?.accountExpired}" />
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'accountExpired', 'error')}</span>
		</div>
	</div>
	
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'accountLocked', 'error')} ">
		<label for="accountLocked" class="control-label"><g:message code="usuario.accountLocked.label" default="Account Locked" /></label>
		<div class="controls">
			<bs:checkBox name="accountLocked" value="${usuarioInstance?.accountLocked}" />
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'accountLocked', 'error')}</span>
		</div>
	</div>
	
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'enabled', 'error')} ">
		<label for="enabled" class="control-label"><g:message code="usuario.enabled.label" default="Enabled" /></label>
		<div class="controls">
			<bs:checkBox name="enabled" value="${usuarioInstance?.enabled}" />
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'enabled', 'error')}</span>
		</div>
	</div>
	
	<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'passwordExpired', 'error')} ">
		<label for="passwordExpired" class="control-label"><g:message code="usuario.passwordExpired.label" default="Password Expired" /></label>
		<div class="controls">
			<bs:checkBox name="passwordExpired"/>
			<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'passwordExpired', 'error')}</span>
		</div>
	</div>
  