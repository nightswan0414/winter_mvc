package winter.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {
	public String mimeType() default "text/plain";
	public String encType() default "euc-kr";
}
