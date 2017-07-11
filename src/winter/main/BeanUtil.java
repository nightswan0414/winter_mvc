package winter.main;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

public class BeanUtil 
{
	public static void cTypeTransferProps( HttpServletRequest request, 
		Object pvo )
	{
		Class<?> cls = pvo.getClass();
		Method[] ml = cls.getMethods();
		for( int i = 0 ; i < ml.length ; i++ )
		{
			String name = ml[i].getName();
			Class<?>[] ptypes = ml[i].getParameterTypes();

			if( name.startsWith("set") && ptypes.length == 1 )
			{
				String pn = UtilForName.cTypePropName( name );
				try{
					if( ptypes[0] == Integer.class ){
						ml[i].invoke(pvo, Util.pInt( request.getParameter(pn)));
					}
					else if( ptypes[0] == String.class ){
						ml[i].invoke(pvo ,Util.us2utf8(request.getParameter(pn)));
					}
					else if( ptypes[0] == Double.class ){
						ml[i].invoke( pvo,Util.pDbl(request.getParameter(pn)));
					}
				}
				catch( Exception e ){ continue; }
			}
		}
	}
	
	public static void jTypeTransferProps( HttpServletRequest request, Object pvo )
	{
		Class<?> cls = pvo.getClass();
		Method[] ml = cls.getMethods();
		for( int i = 0 ; i < ml.length ; i++ )
		{
			String name = ml[i].getName();
			Class<?>[] ptypes = ml[i].getParameterTypes();

			if( name.startsWith("set") && ptypes.length == 1 )
			{
				String pn = UtilForName.jTypePropName(name);
				try{
					if( ptypes[0] == Integer.class ){
						ml[i].invoke(pvo, Util.pInt(request.getParameter(pn)));
					}
					else if( ptypes[0] == String.class ){
						ml[i].invoke(pvo, Util.us2utf8(request.getParameter(pn)));
					}
					else if( ptypes[0] == Double.class ){
						ml[i].invoke(pvo, Util.pDbl(request.getParameter(pn)));
					}
				}
				catch( Exception e ){ continue; }
			}
		}
	}
}
