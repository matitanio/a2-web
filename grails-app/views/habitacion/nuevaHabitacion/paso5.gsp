<%@ page import="arduito.Edificio" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
</head>
<style>
  #draggable { width: 50px; height: 50px; background: red; 
  				}
  .informable{
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
		<div id="droppable" style='width: 600px; height: 600px;  border: 1px solid black; background-image: url("/a2-web/${urlPlanoRelativa}");background-size: 100% 100%;'>
		</div>
	</div>
	<div class="span4">
		<h4>Sensores</h4>
		<g:each in="${sensores}" var="unSensor">
		<div class="draggable" style="width: 10px; height: 10px;">
			<strong>.</strong>${unSensor.nombre}
			<span style="position:absolute; display:none; width: 100px; height: auto; background:white">min:${unSensor.min} - max:${unSensor.max}</span>
			<g:hiddenField name="${unSensor.uuid}" value="-1:-1"/>
		</div>
		</g:each>
		
		<h4>Camaras</h4>
		<g:each in="${camaras}" var="unaCamara" status="i">
		<div class="draggable" style="width: 40px; height: 40px;">
			<strong>.</strong><img src="http://grails.org/wikiImage/description-693/webcam_icon.jpg" height="30" width="30"/>
			<span style="position:absolute; display:none; width: 100px; height: auto; background:white">ip-${unaCamara.ip}</span>
			<g:hiddenField name="${unaCamara.uuid}" value="-1:-1"/>
		</div>
		</g:each>
	</div>
</div>
<div class="form-actions">
   	<g:render template="nuevaHabitacion/formActions"/>
</div>
</g:form>

</body>

</html>
