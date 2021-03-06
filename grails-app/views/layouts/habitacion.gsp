<!DOCTYPE html>
<%-- <html lang="${org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).toString().replace('_', '-')}"> --%>
<html lang="${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}">

<head>
	<title>Arduito</title>
	
    <meta charset="utf-8">
    <meta name="viewport"		content="width=device-width, initial-scale=1.0">
    <meta name="description"	content="">
    <meta name="author"			content="">
    
	<link rel="shortcut icon"		href="${resource(dir: 'images', file: 'logo_ardui2.png')}" type="image/x-icon" />
	
	<link rel="apple-touch-icon"	href="assets/ico/apple-touch-icon.png">
    <link rel="apple-touch-icon"	href="assets/ico/apple-touch-icon-72x72.png"	sizes="72x72">
    <link rel="apple-touch-icon"	href="assets/ico/apple-touch-icon-114x114.png"	sizes="114x114">
	
	<%-- Manual switch for the skin can be found in /view/_menu/_config.gsp --%>
	<r:require modules="jquery"/>
	<r:require modules="${session.skin ? session.skin            : 'bootstrap'}"/>
	<r:require modules="${session.skin ? session.skin + '_utils' : 'bootstrap_utils'}"/>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'arduito.css')}" type="text/css">

	<r:layoutResources />
	<g:layoutHead />

	<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<%-- For Javascript see end of body --%>
<body>
	<g:render template="/_menu/navbar"/>														

	<!-- Enable to overwrite Header by individual page -->
	<g:if test="${ pageProperty(name:'page.header') }">
   		<g:pageProperty name="page.header" />
	</g:if>
	<g:else>
		<g:render template="/layouts/header"/>														
	</g:else>
	<g:render template="/layouts/menu"/>
			
	<div class="container" style="min-height: 400px;">
		<div class="row">
	<div class="span12 pagination-centered">
	
		<ul class="breadcrumb">
		<g:form>
		  <li class="active"><g:link event="jump" params="[to:'paso1']">Inicio</g:link></li>
		  <li><g:link event="jump" params="[to:'paso2']">Sensores</g:link></li>
		  <li><g:link event="jump" params="[to:'paso3']">Camaras</g:link></li>
		  <li><g:link event="jump" params="[to:'paso4']">Plano</g:link></li>
		  <li><g:link event="jump" params="[to:'paso5']">Ubicacion</g:link></li>
		  <li><g:link  event="jump" params="[to:'paso6']">Notificaciones</g:link></li>
		  <li><g:link  event="jump" params="[to:'paso7']">Resumen</g:link></li>
		</g:form>
		</ul>
		</div>						
		</div>						
		<g:layoutBody/>
	</div>
	<r:layoutResources />
</body>

</html>