package winter.main.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*	ModelAttribute 는 Spring 과 같은 심플한 형태
 * 	ModelAttribute2 는 @Plant @CuidInto 와 같이 쓸 수 있다.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelAttribute {

}
