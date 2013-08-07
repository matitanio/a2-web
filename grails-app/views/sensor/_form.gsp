<%@ page import="arduito.Sensor" %>



			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'tipo', 'error')} required">
				<label for="tipo" class="control-label"><g:message code="sensor.tipo.label" default="Tipo" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="tipo" required="" value="${sensorInstance?.tipo}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'tipo', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: sensorInstance, field: 'descripcion', 'error')} required">
				<label for="descripcion" class="control-label"><g:message code="sensor.descripcion.label" default="Descripcion" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="descripcion" required="" value="${sensorInstance?.descripcion}"/>
					<span class="help-inline">${hasErrors(bean: sensorInstance, field: 'descripcion', 'error')}</span>
				</div>
			</div>

