package winter.main;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;

import winter.annotations.ModelAttribute;
import winter.annotations.RequestParam;

public class MethodAndTarget
{
	private Method method = null;
	private Object target = null;
	
	public MethodAndTarget(Method m, Object t) 
	{
		method = m;
		target = t;
	}
	
	@Override
	public String toString() {
		return "MethodAndTarget [method=" + method + "]";
	}

	public Method getMethod(){
		return method;
	}	

	public Object doIt(HttpServletRequest request,
		HttpServletResponse response, ApplicationContext applicationContext)
		throws Throwable
	{
		Class<?>[] paramTypes = method.getParameterTypes();
		Object[] args = new Object[paramTypes.length];
		
		for( int i = 0 ; i < paramTypes.length ; i++ )
		{
			if( paramTypes[i] == HttpServletRequest.class ){
				args[i] = request;
			}
			else if( paramTypes[i] == HttpServletResponse.class ){
				args[i] = response;
			}
			else if( paramTypes[i] == HttpSession.class ){
				args[i] = request.getSession();
			}
			else if( paramTypes[i] == ApplicationContext.class ){
				args[i] = applicationContext;
			}
			else if( paramTypes[i] == SqlSessionTemplate.class ){
				SqlSessionTemplate sqlSession = applicationContext.getBean(
					"sqlSessionTemplate", SqlSessionTemplate.class);
				args[i] = sqlSession;
			}
			else
			{
				ModelAttribute modelAttribute = (ModelAttribute)getAnnotationInArgumentAt(
					i,ModelAttribute.class);
				RequestParam requestParam = (RequestParam)getAnnotationInArgumentAt(
					i,RequestParam.class);
					
				if( modelAttribute != null && requestParam != null ){
					throw new RuntimeException("ModelAttribute 와 RequestParam 두개는 같이 못씀");
				}
				

				if( requestParam != null )
				{
					String name = requestParam.value();
					Object temp = null;
					
					if( paramTypes[i] == String.class ){
						temp = Util.us2utf8(request.getParameter(name));
					}
					else if( paramTypes[i] == Integer.class ){
						temp = Util.pInt(request.getParameter(name));
					}
					else if( paramTypes[i] == Double.class ){
						temp = Util.pDbl(request.getParameter(name));
					}
					
					if( temp != null ){
						args[i] = temp;
					}
					continue;
				}
				
				if( modelAttribute != null )
				{
					Class<?> cls = getMethod().getParameterTypes()[i];
					Object pvo = cls.newInstance();
					
					/*	pvo 에 userName 이라는 property 가 있으면
					 * 	request.getParameter("userName") 을 한 결과를 가지고
					 * 	pvo 안에 setUserName 을 호출해서 자동으로 주입하는 함수가
					 * 	BeanUtil.populate 함수의 역할이다.
					 * 
					 * */
					BeanUtil.jTypeTransferProps(request, pvo);
					args[i] = pvo;

					continue;
				}
				
			}
		}
			
		Object obj = null;
		try {
			obj = method.invoke(target, args);
		}
		catch(Throwable e) {
			throw Util.finalCause( e );
		}
		return obj;
		
	}
	
	private Annotation getAnnotationInArgumentAt( int i, Class<?> cls )	{
		Annotation[] l = getMethod().getParameterAnnotations()[i];
		Annotation xxx = null;
		for( int j = 0 ; j < l.length ; j++ ){
			if( cls.isInstance( l[j] ) ){
				xxx = l[j];
				break;
			}
		}
		return xxx;
	}	
	
	
	public <T extends Annotation> T getAnnotation( Class<T> cls ){
		return method.getAnnotation( cls );
	}

	public <T extends Annotation> boolean hasAnnotation( Class<T> cls ){
		return ( method.getAnnotation( cls ) != null ) ;
	}
}
