<%@ page import="arduito.DispositivoMovil" %>
<%@ page import="arduito.Usuario" %>
<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>	
	
			<sec:ifAllGranted roles="ROLE_ARDUITO">
			<div class="control-group fieldcontain ${hasErrors(bean: usuarioInstance, field: 'cuenta', 'error')} ">
				<label for="cuenta" class="control-label"><g:message code="usuario.cuenta.label" default="Cuenta" /></label>
				<div class="controls">
					<g:select id="cuenta" name="cuenta.id" from="${arduito.Cuenta.list()}" optionKey="id" 
					value="${usuarioInstance?.cuenta?.id}" class="many-to-one" 
					noSelection="['null':'Seleccionar una cuenta']"
					 onchange="${remoteFunction(
					            controller:'dispositivoMovil', 
					            action:'usuariosPorcuenta', 
					            params:'\'id=\' + escape(this.value)', 
					            update:'owner')}"/>
					<span class="help-inline">${hasErrors(bean: usuarioInstance, field: 'cuenta', 'error')}</span>
				</div>
			</div>
			</sec:ifAllGranted>
			<g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_ARDUITO')}">
				<g:render template="usuarios" model='[usuarios:[]]'/>
			</g:if>
			<g:else>
				<g:set var="usuarios" value="${Usuario.findAllByCuenta(Usuario.get(sec.loggedInUserInfo(field: 'id').toLong()).cuenta)}" />
				<g:render template="usuarios" model='[usuarios:usuarios]'/>
			</g:else>
