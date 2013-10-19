<g:submitButton class="btn btn-primary" name="siguiente" value="Siguiente" id="btn-siguiente" />
<g:if test="${resumen}">
	<g:submitButton class="btn btn-primary" name="resumen" value="Resumen" />
</g:if>
<g:if test="${edit}">
   <g:submitButton class="btn btn-primary" name="guardar" value="Guardar" />
</g:if>
<g:submitButton class="btn btn-primary" name="atras" value="Atras" />
<g:link class="btn btn-danger" role="button" name="cancelar" action="list">Cancelar</g:link>