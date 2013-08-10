<g:if test="${session.layout == null || session.layout == 'grid'}">
	<g:set var="menutype" value="nav nav-tabs" />
</g:if>
<g:elseif test="${session.layout == 'fluid'}">
	<g:set var="menutype" value="nav nav-tabs" />
</g:elseif>

<!-- position of menu: LTR (left-to-right, European) or RTL (right-to-left, Oriental) -->
<g:if test="${session.menuposition == 'right' && session.layout == 'grid'}">
	<g:set var="menuposition" value="pull-right" />
</g:if>
<g:elseif test="${session.menuposition == 'right' && session.layout == 'fluid'}">
	<g:set var="menuposition" value="tabbable tabs-right" /> <!-- pull-right -->
</g:elseif>
<g:elseif test="${session.layout == 'fluid'}">
	<g:set var="menuposition" value="tabbable tabs-left" /> <!-- pull-left -->
</g:elseif>
<g:else>
	<g:set var="menuposition" value="" />
</g:else>

<sec:ifLoggedIn>
<div class="${menuposition}">
	<ul class="${menutype}" data-role="listview" data-split-icon="gear" data-filter="true">
	<li class="controller${params.controller == 'home' ? " active" : ""}">
		<g:link controller="home" action="home">
			Home
		</g:link>
	</li>
	<sec:ifAllGranted roles="ROLE_ADMIN">
		<li class="controller${params.controller == 'usuario' ? " active" : ""}">
			<g:link controller="usuario" action="index">
				Usuarios
			</g:link>
		</li>
	</sec:ifAllGranted>
	<sec:ifAllGranted roles="ROLE_ARDUITO">
		<li class="controller${params.controller == 'cuenta' ? " active" : ""}">
			<g:link controller="cuenta" action="index">
				Cuentas
			</g:link>
		</li>
	</sec:ifAllGranted> 
	<sec:ifAllGranted roles="ROLE_ARDUITO">
		<li class="controller${params.controller == 'sensor' ? " active" : ""}">
			<g:link controller="sensor" action="index">
				Sensor
			</g:link>
		</li>
	</sec:ifAllGranted>
	<sec:ifAllGranted roles="ROLE_ADMIN">
		<li class="controller${params.controller == 'edificio' ? " active" : ""}">
			<g:link controller="edificio" action="index">
				Edificios
			</g:link>
		</li>
	</sec:ifAllGranted>
	<sec:ifAllGranted roles="ROLE_ADMIN">
		<li class="controller${params.controller == 'habitacion' ? " active" : ""}">
			<g:link controller="habitacion" action="list">
				Habitaciones
			</g:link>
		</li>
	</sec:ifAllGranted>
	<sec:ifAllGranted roles="ROLE_ADMIN">
		<li class="controller${params.controller == 'dispositivoMovil' ? " active" : ""}">
			<g:link controller="dispositivoMovil" action="index">
				Dispositivos
			</g:link>
		</li>
	</sec:ifAllGranted>
	  	 	
	</ul>
</div>
</sec:ifLoggedIn>