<%@ page import="arduito.Habitacion" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'cuenta.label', default: 'Cuenta')}" />
	<title>Habitacion</title>
</head>

<body>
	
<section id="list-cuenta" class="first">
<g:if test="${habitacionInstanceList.size() == 0}">
	<div class="alert alert-info"> no hay Habitaciones </div>
</g:if>
<g:else>
	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th>id</th>
			
				<g:sortableColumn property="ipHabitacion" title="ip" />
				<th>Instalacion</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${habitacionInstanceList}" status="i" var="habitacionInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td>${habitacionInstance.id}</td>
				<td>${habitacionInstance.ipHabitacion}</td>
				<td><a data-toggle="modal" href="#hoja${habitacionInstance.id}-modal" class="btn btn-primary btn-small verificador">Ver Hoja Instalacion</a>
				
					<div id="hoja${habitacionInstance.id}-modal" class="modal hide fade in" style="display: none; ">  
						<div class="modal-header">  
							<a class="close" data-dismiss="modal">×</a>
							<H4>Hoja Instalacion </H4>  
						</div>  
						<div class="modal-body">  
						        
						        ip: ${habitacionInstance.ipHabitacion}
						        sensores:
						        <g:each in="${habitacionInstance.sensores}" var="unSensor">
						        
						        	tipo: ${unSensor.sensor.tipo}
						        	id para notificaicon: ${unSensor.id}
						        	numero: ${unSensor.numeroSensor}
						        	<br>
						        </g:each>
						</div>  
						<div class="modal-footer">
							<a href="#" class="btn" data-dismiss="modal">Cerrar</a>
						</div>  
					</div>
				</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${habitacionInstanceTotal}" />
	</div>
</g:else>
</section>
</body>

</html>
