
<%@ page import="arduito.DispositivoMovil" %>
<%@ page import="arduito.Estado" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'dispositivoMovil.label', default: 'DispositivoMovil')}" />
	<title>Dispositivo Movil</title>
</head>

<body>
	
<section id="list-dispositivoMovil" class="first">
<g:if test="${dispositivoMovilInstanceList.size() == 0}">
	<div class="alert alert-info"> no hay Dispositivos </div>
</g:if>
<g:else>
	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th><g:message code="dispositivoMovil.owner.label" default="Owner" /></th>
				<th><g:message code="dispositivoMovil.owner.label" default="Estado" /></th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${dispositivoMovilInstanceList}" status="i" var="dispositivoMovilInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="edit" id="${dispositivoMovilInstance.id}">${fieldValue(bean: dispositivoMovilInstance, field: "owner")}</g:link></td>
				<td>${fieldValue(bean: dispositivoMovilInstance, field: "estado")}
					<g:if test="${dispositivoMovilInstance?.estado == Estado.SIN_VERIFICAR}">
						<a data-toggle="modal" href="#verificar-modal" class="btn btn-primary btn-small verificador" data-id="${dispositivoMovilInstance.id}">Verificar</a>
					</g:if>
					<g:if test="${dispositivoMovilInstance?.estado == Estado.VERIFICANDO}">
						<a data-toggle="modal" href="#verificar-modal" class="btn btn-primary btn-small verificador"  data-id="${dispositivoMovilInstance.id}">Re-Verificar</a>
					</g:if>
					<g:if test="${dispositivoMovilInstance?.estado == Estado.VERIFICADO}">
						<a data-toggle="modal" href="#verificar-modal" class="btn btn-danger btn-small verificador"  data-id="${dispositivoMovilInstance.id}">Re-Verificar</a>
					</g:if>		
				</td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${dispositivoMovilInstanceTotal}" />
	</div>
</g:else>
<div id="verificar-modal" class="modal hide fade in" style="display: none; ">  
<div class="modal-header">  
<a class="close" data-dismiss="modal">×</a>  
<h3>Verificar dispositivo</h3>  
</div>  
<div class="modal-body">  
<h4>Se enviara un mail al usuario con un codigo de registro</h4>  
<p>El usuario debera ingresar este codigo y su nombre de usuario para completar el proceso de verificacion</p>        
<p>Si re-verifica un dispostivo que ya fue verificado el usuario deberá volver a introducir el pin y su nombre de usuario en la aplicacion movil</p>        
</div>  
<div class="modal-footer">
<g:form action="verificar">
	<g:hiddenField name="dispositivo-id"/>
	<g:submitButton name="verificar" value="Verificar" class="btn btn-success"/>
	<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
</g:form>
  
</div>  
</div>
</section>
<script>
$(document).ready(function(){
	   $(".verificador").click(function(){ // Click to only happen on announce links
	     $("#dispositivo-id").val($(this).data('id'));
	   });
	});
</script>
</body>

</html>
