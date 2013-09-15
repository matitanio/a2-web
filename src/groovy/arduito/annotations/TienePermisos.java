package arduito.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import arduito.rest.api.ComponentesValidables;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TienePermisos {

	ComponentesValidables componente();
		
}
