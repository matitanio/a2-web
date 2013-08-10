
<%@ page import="arduito.DispositivoMovil" %>
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
	<div class="sin-datos"> no hay Dispositivos </div>
</g:if>
<g:else>
	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th><g:message code="dispositivoMovil.owner.label" default="Owner" /></th>
			
				<g:sortableColumn property="numero" title="${message(code: 'dispositivoMovil.numero.label', default: 'Numero')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${dispositivoMovilInstanceList}" status="i" var="dispositivoMovilInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${dispositivoMovilInstance.id}">${fieldValue(bean: dispositivoMovilInstance, field: "owner")}</g:link></td>
			
				<td>${fieldValue(bean: dispositivoMovilInstance, field: "numero")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${dispositivoMovilInstanceTotal}" />
	</div>
</g:else>
</section>

</body>

</html>
