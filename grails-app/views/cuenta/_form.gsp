<%@ page import="arduito.Cuenta" %>



			<div class="control-group fieldcontain ${hasErrors(bean: cuentaInstance, field: 'owner', 'error')} required">
				<label for="owner" class="control-label"><g:message code="cuenta.owner.label" default="Owner" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="owner" name="owner.id" from="${arduito.Cuenta.findAllByOwnerIsNull()}" optionKey="id" required="" value="${cuentaInstance?.owner?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: cuentaInstance, field: 'owner', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: cuentaInstance, field: 'nombre', 'error')} required">
				<label for="nombre" class="control-label"><g:message code="cuenta.nombre.label" default="Nombre" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="nombre" required="" value="${cuentaInstance?.nombre}"/>
					<span class="help-inline">${hasErrors(bean: cuentaInstance, field: 'nombre', 'error')}</span>
				</div>
			</div>

