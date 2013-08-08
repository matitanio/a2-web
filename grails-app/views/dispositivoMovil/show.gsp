
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

<section id="show-dispositivoMovil" class="first">
	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="dispositivoMovil.owner.label" default="Owner" /></td>
				
				<td valign="top" class="value"><g:link controller="usuario" action="show" id="${dispositivoMovilInstance?.owner?.id}">${dispositivoMovilInstance?.owner?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="dispositivoMovil.numero.label" default="Numero" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: dispositivoMovilInstance, field: "numero")}</td>
				
			</tr>
			
			<tr class="prop">
				<td valign="top" class="name">Estado</td>
				
				<td valign="top" class="value">
				<span class="text-danger">${dispositivoMovilInstance?.estado.toString().replaceAll('_',' ')}  
				<g:if test="${dispositivoMovilInstance?.estado == Estado.SIN_VERIFICAR}">
					<a data-toggle="modal" href="#verificar-modal" class="btn btn-primary btn-small">Verificar</a>
				</g:if>
				<g:if test="${dispositivoMovilInstance?.estado == Estado.VERIFICANDO}">
					<a data-toggle="modal" href="#verificar-modal" class="btn btn-primary btn-small">Re-Verificar</a>
				</g:if>
				</span> </td>
			</tr>
		
		</tbody>
	</table>
	
<div id="verificar-modal" class="modal hide fade in" style="display: none; ">  
<div class="modal-header">  
<a class="close" data-dismiss="modal">×</a>  
<h3>Verificar dispositivo</h3>  
</div>  
<div class="modal-body">  
<h4>Se enviara un mail al usuario con un codigo</h4>  
<p>El usuario debera ingresar ese codigo en la aplicacion movil mas bla bla y algun dibujito</p>                
</div>  
<div class="modal-footer">  
<g:link action="verificar" params='[id:dispositivoMovilInstance.id]' class="btn btn-success">Verificar</g:link>  
<a href="#" class="btn" data-dismiss="modal">Cancelar</a>  
</div>  
</div>  
</section>
</body>
</html>
