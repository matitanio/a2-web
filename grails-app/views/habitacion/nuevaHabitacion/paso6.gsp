<%@ page import="arduito.Edificio" %>
<%@ page import="arduito.Cuenta" %>
<%@ page import="arduito.DispositivoMovil" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="habitacion" />
	<title>Nueva Habitacion</title>
	<style>
		#agregar{
			position:relative;
			left: 179px;
		}
	</style>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.multiselect.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'multiselect.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css/smoothness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css">
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
	<g:javascript src="jquery-ui-1.10.3.custom.js" />
	<g:javascript src="jquery.multiselect.js" />
</head>

<body>
<g:form  class="form-horizontal">
<div class="row main-flow">
	<div class="span4">
		<fieldset class="form">
			<h4>Sensores</h4>
			<g:each in="${sensores}" var="unSensor" status="i">
			<div class="row">
				<div class="span2">
					${unSensor.nombre} <select id="multiple-sensor${i}" class="notificadores" name="dispositivos-${unSensor.uuid}" multiple="multiple">
					<g:each in="${dispositivos}" var="unDispositivo">
						<option value="${unDispositivo.id}" ${unSensor.notificables.contains(unDispositivo.id as String)?"selected='selected'":''}>${unDispositivo.owner}</option>
					</g:each>
					</select>
				</div>
			</div>	
			</g:each>
			
		</fieldset>
	</div>
	<div class="span4">
			<h4>Camaras</h4>	
			<g:each in="${camaras}" var="unaCamara" status="i">
			<div class="row">
				<div class="span2">
					Camara ${i} - ip :${unaCamara.ip} <select id="multiple-camara${i}" class="notificadores" name="dispositivos-${unaCamara.uuid}" multiple="multiple">
					<g:each in="${dispositivos}" var="unDispositivo">
						<option value="${unDispositivo.id}" ${unaCamara.notificables.contains(unDispositivo.id as String)?"selected='selected'":''}>${unDispositivo.owner}</option>
					</g:each>
					</select>
				</div>
			</div>	
			</g:each>
	
	</div>
	
	<div class="span4">
		<g:if test="${paso1Command.rfid}">
			<h4>Rfid</h4>
			<div class="row">
				<div class="span2">
					<select id="multiple-rfid${i}" class="notificadores" name="dispositivos-rfid" multiple="multiple">
					<g:each in="${dispositivos}" var="unDispositivo">
						<option value="${unDispositivo.id}" ${rfid.notificables.contains(unDispositivo.id as String)?"selected='selected'":''}>${unDispositivo.owner}</option>
					</g:each>
					</select>
				</div>
			</div>	
		</g:if>
	
	</div>
</div>
<div class="form-actions">
	
   		<g:render template="nuevaHabitacion/formActions"/>
   	
</div>
</g:form>

<script>
function sePuedeSeguir(){

	var sePuedeSeguir = false;
	var multiplicacionTamanio = 1;
   	$('.notificadores').each(function(index){
   		multiplicacionTamanio = multiplicacionTamanio * $(this).multiselect("getChecked").size();
   		sePuedeSeguir = multiplicacionTamanio != 0;
	      });
	      if(sePuedeSeguir){
	    	  $('#btn-siguiente').removeAttr('disabled')
		  }else{
			  $('#btn-siguiente').attr('disabled','disabled')
		}
	
}

$(document).ready(function(){

		<g:if test="${resumen}">
			$('#btn-siguiente').removeAttr('disabled')
		</g:if>
	
	   $(".sensor").click(function(){ // Click to only happen on announce links
	     $("#sensor-uuid").val($(this).data('id'));
	   });

	   $(".notificadores").multiselect({
		   noneSelectedText:'Seleccionar dispositivos',	
		   checkAllText: 'Todos',
		   uncheckAllText:'Ninguno',
		   selectedText:'Seleccionados',
		   click: function(event, ui){
			 
			   sePuedeSeguir();   
		    },
		});
	});

	
</script>
</body>
</html>
