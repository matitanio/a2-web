<%@ page import="arduito.Habitacion"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'cuenta.label', default: 'Cuenta')}" />
<title>Habitacion</title>
<style>
.modal {
	/* new custom width */
	width: 800px;
	/* must be half of the width, minus scrollbar on the left (30px) */
	margin-right: 250px;
	left: 40%;
}
</style>
</head>

<body>

	<section id="list-cuenta" class="first">
		<g:if test="${habitacionInstanceList.size() == 0}">
			<div class="alert alert-info">no hay Habitaciones</div>
		</g:if>
		<g:else>
			<table class="table table-bordered">
				<thead>
					<tr>

						<th>id</th>

						<g:sortableColumn property="ipHabitacion" title="ip" />
						<th>Instalacion</th>
						<th>Estado Actual</th>
						<th>Editar</th>
						<th>Actualizar Accesos</th>
						<th>Activar</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${habitacionInstanceList}" status="i"
						var="habitacionInstance">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

							<td>
								${habitacionInstance.id}
							</td>
							<td>
								${habitacionInstance.ipHabitacion}
							</td>
							<td><a data-toggle="modal"
								href="#hoja${habitacionInstance.id}-modal"
								class="btn btn-primary btn-small verificador">Ver Hoja
									Instalacion</a>

								<div id="hoja${habitacionInstance.id}-modal"
									class="modal hide fade in" style="display: none;">
									<div class="modal-header">
										<a class="close" data-dismiss="modal">×</a>
										<H4>Hoja Instalacion</H4>
									</div>
									<div class="modal-body">

										<table class="table table-bordered">
											<thead>
												<tr align="center">
													<th align="center">Parametros configuracion</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>Ip</td>
													<td>
														${habitacionInstance.ipHabitacion}
													</td>
												</tr>
												<tr>
													<td>Edificio</td>
													<td>
														${habitacionInstance.edificio}
													</td>
												</tr>
												<tr>
													<td>Id Edificio</td>
													<td>
														${habitacionInstance.edificio.id}
													</td>
												</tr>
												<tr>
													<td>Id Habitacion</td>
													<td>
														${habitacionInstance.id}
													</td>
												</tr>
												<tr>
													<td>Piso</td>
													<td>
														${habitacionInstance.piso}
													</td>
												</tr>
												<tr>
													<td>Numero</td>
													<td>
														${habitacionInstance.numero}
													</td>
												</tr>
											</tbody>
										</table>
										<table class="table table-bordered">
											<thead>
												<tr align="center">
													<th align="center">Sensores</th>
												</tr>
												<tr>
													<th>Tipo</th>
													<th>Id Notificacion</th>
													<th>Url notificacion</th>
												</tr>
											</thead>
											<tbody>
												<g:each in="${habitacionInstance.sensores}" var="unSensor">
													<tr>
														<td>
															${unSensor.sensor.tipo}
														</td>
														<td>
															${unSensor.id}
														</td>
														<td>
															a2-web/notificacion/${habitacionInstance.edificio.id}/${habitacionInstance.id}/${unSensor.id}/
														</td>
													</tr>
												</g:each>
											</tbody>
										</table>

										<table class="table table-bordered">
											<thead>
												<tr align="center">
													<th align="center">Camaras</th>
												</tr>
											</thead>
											<tbody>
												<g:each in="${habitacionInstance.camaras}" var="unaCamara">
													<tr>
														<td>ip</td>
														<td>
															${unaCamara.ip}
														</td>
													</tr>
												</g:each>
											</tbody>
										</table>
									</div>
									<div class="modal-footer">
										<a href="#" class="btn" data-dismiss="modal">Cerrar</a>
									</div>
								</div></td>
							<td><a data-toggle="modal"
								href="#estado${habitacionInstance.id}-modal"
								class="btn btn-primary btn-small verificador">Ver</a>

								<div id="estado${habitacionInstance.id}-modal"
									class="modal hide fade in" style="top: 40%;">
									<div class="modal-header">
										<a class="close" data-dismiss="modal">×</a>
										<H4>Estado Habitacion</H4>
									</div>
									<div class="modal-body "
										style="display: block; width: 700px; height: 700px;">
										<div id="droppable"
											style='position:relative;width: 600px; height: 600px;  border: 1px solid black;  background-image: url("/a2-web/${habitacionInstance.urlPlano}");background-size: 100% 100%;'>
											<g:each in="${habitacionInstance.sensores}" var="unSensor">
												<div
													style='position:relative; top:${unSensor.coordenadaY}px; left:${unSensor.coordenadaX}px; width: 10px; height: 10px;'
													class="sensor">
													<strong>.</strong>
													${unSensor.sensor.tipo}
													<span
														style="position: absolute; display: none; width: 100px; height: auto; background: white">Valor
														Actual=${unSensor.valorActual?:0}
													</span>
												</div>
											</g:each>

										</div>
									</div>
									<div class="modal-footer">
										<a href="#" class="btn" data-dismiss="modal">Cerrar</a>
									</div>
								</div></td>
							<td><g:link role="button" class="btn btn-primary btn-small" action="edit"  id="${habitacionInstance.id}">Editar</g:link></td>
							<td><g:link role="button" class="btn btn-primary btn-small" action="actulizarAccesos"  id="${habitacionInstance.id}">Actualizar Accesos</g:link></td>
							<td>
								<g:link role="button" class="btn btn-primary btn-small" action="cambiarEstado" params="[id:habitacionInstance.id,estado:habitacionInstance.activa]">${habitacionInstance.activa?'Desactivar':'Activar'}</g:link>
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
	<script>
		$(function() {
			$('.sensor').hover(function() {
				$(this).children("span:last").show()
			}, function() {
				$(this).children("span:last").hide();
			});
		});
	</script>
</body>

</html>
