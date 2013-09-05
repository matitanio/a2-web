<%@ page import="arduito.Sensor" %>



			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'tipo', 'error')} required">
				<label for="tipo" class="control-label"><g:message code="sensor.tipo.label" default="Tipo" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="tipo" required="" value="${sensorInstance?.tipo}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'tipo', 'error')}</span>
				</div>
			</div>
			
			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'unidades', 'error')} required">
				<label for="tipo" class="control-label"><g:message code="sensor.tipo.label" default="Unidades" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="unidades" required="" value="${sensorInstance?.unidades}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'unidades', 'error')}</span>
				</div>
			</div>
			
			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'valorMinimo', 'error')} required">
				<label for="tipo" class="control-label"><g:message code="sensor.tipo.label" default="Valor Minimo" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="valorMinimo" required="" value="${sensorInstance?.valorMinimo}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'valorMinimo', 'error')}</span>
				</div>
			</div>
			
			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'valorMaximo', 'error')} required">
				<label for="tipo" class="control-label"><g:message code="sensor.tipo.label" default="Valor Máximo" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="valorMaximo" required="" value="${sensorInstance?.valorMaximo}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'valorMaximo', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'descripcion', 'error')} required">
				<label for="descripcion" class="control-label"><g:message code="sensor.descripcion.label" default="Descripcion" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="descripcion" required="" value="${sensorInstance?.descripcion}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'descripcion', 'error')}</span>
				</div>
			</div>

