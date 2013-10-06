<html>
<head>
	<title>Bienvenido a Arduito</title>
	<meta name="layout" content="not-logged-layout" />
</head>
<body>
	<div style="min-height: 400px;width:500px" >
		<form action="${postUrl}" method="post" accept-charset="UTF-8"  class="form-horizontal">
			<div class="form-group">
		    	<label for="inputEmail1" class="control-label">Usuario</label>
		    	<div class="controls">
		    	<input style="margin-bottom: 15px;" type="text" placeholder="Username" id="username" name="j_username">  	
		    	</div>
		  	</div>
			<div class="form-group">
		    	<label for="inputEmail1" class="control-label">Password</label>
		    	<div class="controls">
		    	<input style="margin-bottom: 15px;" type="password" placeholder="Password" id="password" name="j_password">  	
		    	</div>
		  	</div>
		  	 <div class="control-group">
    			<div class="controls">
					<input class="btn btn-primary" type="submit" value="Entrar">
				</div>
			</div>
		</form>
		<g:if test="${flash.message}">
			<div class="alert" style="display: inline-block" align="center">${flash.message}</div>
		</g:if>
	</div>
</body>

</html>
