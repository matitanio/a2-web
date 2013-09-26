<div class="control-group fieldcontain ${hasErrors(bean: paso1Command, field: 'edificio', 'error')}" required>
	<label for="owner" class="control-label">Edificio<span class="required-indicator">*</span></label>
	<div class="controls">
		<g:select id="edificio" name="edificio" from="${edificios}" optionKey="id" required=""
		value="${edificio}" class="many-to-one"
		noSelection='["null":"${((edificios.size() > 0)?'seleccione un edificio':'Seleccionar una cuenta')}"]'/>
		<span class="help-inline">
		${hasErrors(bean: paso1Command, field: 'edificio', 'Debes elegir un edificio')}
		</span>
	</div>
</div>
