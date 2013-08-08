<div class="control-group fieldcontain ${hasErrors(bean: dispositivoMovilInstance, field: 'owner', 'error')} required">
	<label for="owner" class="control-label"><g:message code="dispositivoMovil.owner.label" default="Owner" /><span class="required-indicator">*</span></label>
	<div class="controls">
		<g:select id="owner" name="owner.id" from="${usuarios}" optionKey="id" required="" 
		value="${dispositivoMovilInstance?.owner?.id}" class="many-to-one"
		noSelection='["null":"${((usuarios.size() > 0)?'Seleccionar un usuario':'seleccione una cuenta')}"]'/>
		<span class="help-inline">${hasErrors(bean: dispositivoMovilInstance, field: 'owner', 'error')}</span>
	</div>
</div>


