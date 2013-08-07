<%@ page import="arduito.Edificio" %>


			<sec:ifAllGranted roles="ROLE_ARDUITO">
			<div class="control-group fieldcontain ${hasErrors(bean: edificioInstance, field: 'owner', 'error')} required">
				<label for="owner" class="control-label"><g:message code="edificio.owner.label" default="Owner" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="owner" name="owner.id" from="${arduito.Cuenta.list()}" optionKey="id" required="" value="${edificioInstance?.owner?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: edificioInstance, field: 'owner', 'error')}</span>
				</div>
			</div>
			</sec:ifAllGranted>
			<div class="control-group fieldcontain ${hasErrors(bean: edificioInstance, field: 'direccion', 'error')} required">
				<label for="direccion" class="control-label"><g:message code="edificio.direccion.label" default="Direccion" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="direccion" required="" value="${edificioInstance?.direccion}"/>
					<span class="help-inline">${hasErrors(bean: edificioInstance, field: 'direccion', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: edificioInstance, field: 'nombre', 'error')} ">
				<label for="nombre" class="control-label"><g:message code="edificio.nombre.label" default="Nombre" /></label>
				<div class="controls">
					<g:textField name="nombre" value="${edificioInstance?.nombre}"/>
					<span class="help-inline">${hasErrors(bean: edificioInstance, field: 'nombre', 'error')}</span>
				</div>
			</div>

