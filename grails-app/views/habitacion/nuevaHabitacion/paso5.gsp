<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
  	<g:javascript src="jquery-ui-1.10.3.custom.js" />
</head>
<style>
  #draggable { width: 50px; height: 50px; background: red;
  				} 
</style>
<body>
  <script>
  $(function() {
    $( ".draggable" ).draggable({
    	containment: '#droppable',
        cursor: 'move',
        snap: '#droppable'
       });

    $('#droppable').droppable({
        drop: function (event, ui) {
        	var pos = ui.draggable.offset(), dPos = $(this).offset();
        	var posicion = (pos.top - dPos.top)+':'+(pos.left - dPos.left)
        	ui.draggable.children('input:last').val(posicion)
        }
    });

    $('.draggable').hover(
      function() {$(this).children("span:last").show()},
      function() { $(this).children("span:last").hide(); }
    );
  });
  </script>
<g:form class="form-horizontal" >
<div class="row">
	<div class="span8">
		<div id="droppable" style='position:relative; width: 600px; height: 600px;  border: 1px solid black; background-image: url("/a2-web/${urlPlanoRelativa}");background-size: 100% 100%;'>
			<g:if test="${ubicacion}">
				<g:each in="${sensores}" var="unSensor">
				<div class="draggable" style='position:relative; top:${unSensor.ubicacion?unSensor.ubicacion.split(':')[0]:-1}px; left:${unSensor.ubicacion?unSensor.ubicacion.split(':')[1]:-1}px; width: 10px; height: 10px;'>
					<strong>.</strong>${unSensor.nombre}
					<span style="position:absolute; display:none; width: 100px; height: auto; background:white">min:${unSensor.min} - max:${unSensor.max}</span>
					<g:hiddenField name="${unSensor.uuid}" value="${ubicacion?unSensor.ubicacion:'-1:-1'}"/>
				</div>
				</g:each>

				<g:each in="${camaras}" var="unaCamara" status="i">
				<div class="draggable" style='width: 40px; height: 40px; top:${unaCamara.ubicacion?unaCamara.ubicacion.split(':')[0]:-1}px; left:${unaCamara.ubicacion?unaCamara.ubicacion.split(':')[1]:-1}px;'>
					<strong>.</strong><img src="http://grails.org/wikiImage/description-693/webcam_icon.jpg" height="30" width="30"/>
					<span style="position:absolute; display:none; width: 100px; height: auto; background:white">ip-${unaCamara.ip}</span>
					<g:hiddenField name="${unaCamara.uuid}" value="${ubicacion?unaCamara.ubicacion:'-1:-1'}"/>
				</div>
				</g:each>
			</g:if>
		</div>
	</div>
	<div class="span4">
	<g:if test="${!ubicacion}">
	<div style="height: 300px;">
		<h4>Sensores</h4>
		<g:each in="${sensores}" var="unSensor">
		<div class="draggable" style="position:relative; top:10px; width: 10px; height: 10px;margin: 15px; padding: 0;">
			<strong>.</strong>${unSensor.nombre}
			<span style="position:absolute; display:none; width: 100px; height: auto; background:white; left: 65px; top:-1px;">min:${unSensor.min} - max:${unSensor.max}</span>
			<g:hiddenField name="${unSensor.uuid}" value="-1:-1"/>
		</div>
		</g:each>
	</div>
	<div style="height: 300px;">
		<h4>Camaras</h4>
		<g:each in="${camaras}" var="unaCamara" status="i">
		<div class="draggable" style="width: 40px; height: 40px;">
			<strong>.</strong><img src="http://grails.org/wikiImage/description-693/webcam_icon.jpg" height="30" width="30"/>
			<span style="position:absolute; display:none; width: 100px; height: auto; background:white; left: 40px; top:-5px;">ip-${unaCamara.ip}</span>
			<g:hiddenField name="${unaCamara.uuid}" value="-1:-1"/>
		</div>
		</g:each>
	</div>
		</g:if>
	</div>
</div>
<div class="form-actions">
   	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>

</body>

</html>
