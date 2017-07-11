package winter.main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import winter.annotations.Controller;
import winter.annotations.RequestMapping;
import winter.annotations.ResponseBody;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet
{
	@Override
	public void service( final HttpServletRequest request, 
		final HttpServletResponse response ) throws ServletException, IOException 
	{
		String ctxPath 		= request.getContextPath() ;
		String uri2 		= request.getRequestURI();
		String actionURI 	= uri2.substring( ctxPath.length() );
		
		final MethodAndTarget methodAndTarget = methodTargetMap.get( actionURI );
		if( methodAndTarget != null )
		{
			try
			{
				Object ro = methodAndTarget.doIt(request, response, applicationContext);

				//	doIt 을 호출하는 부분 : 감싸주는 코드는 여기서 작성해야 한다.				
				if( ro == null ){
					
				}
				else if( ro instanceof String )
				{
					String responseText = ro.toString();
					ResponseBody responseBody = methodAndTarget.getAnnotation(ResponseBody.class);
					
					if( responseBody != null )
					{
						String mimeType = responseBody.mimeType();
						String encType = responseBody.encType();
						
						byte[] bs = ( encType.equals("utf-8") ) ? 
							responseText.getBytes("utf-8") : responseText.getBytes();

						response.setContentLength(bs.length);
						response.setContentType( mimeType + ";charset=" + encType );
						
						OutputStream out = response.getOutputStream();
						out.write( bs );
						out.flush(); out.close();
					}
					else if( responseText.startsWith("redirect:") )
					{
						responseText = responseText.substring(9);
						if( responseText.startsWith("http://") ){
							response.sendRedirect( responseText );
						}else{
							String l2 = ctxPath + responseText;
							response.sendRedirect( l2 );
						}
					}
					else
					{
						ViewResolver viewResolver = applicationContext.
							getBean("viewResolver", ViewResolver.class );
						
						String p = viewResolver.getPrefix() + responseText + viewResolver.getSuffix();
						RequestDispatcher rd = request.getRequestDispatcher( p );
						rd.forward( request, response );
					}
				}
				else if( ro instanceof ModelAndView )
				{
					ViewResolver viewResolver = applicationContext.
						getBean("viewResolver", ViewResolver.class );					
					ModelAndView mnv = (ModelAndView)ro;
					
					String viewName = mnv.getViewName();
					if( viewName.startsWith("response:") )
					{
						viewName = viewName.substring( 9 );
						response.setContentType("text/plain;charset=utf-8");
						
						PrintWriter out = response.getWriter();
						out.print( viewName );
						out.flush(); out.close();
					}
					else if( viewName.startsWith("redirect:") )
					{
						viewName = viewName.substring(9);
						if( viewName.startsWith("http://") ){
							response.sendRedirect( viewName );
						}else{
							String l2 = ctxPath + viewName;
							response.sendRedirect( l2 );
						}
					}
					else
					{
						String l2 = ( viewResolver != null ) ?
							viewResolver.getPrefix() + viewName + viewResolver.getSuffix() :
							"WEB-INF/jsp/" + viewName + ".jsp";

						mnv.plant( request );
						RequestDispatcher rd = request.getRequestDispatcher( l2 );
						rd.forward( request, response );
					}
				}
			}
			catch( Throwable e ){
				e = Util.finalCause(e);
				//	error on controller
				
				if( errorConsoleView ){
					e.printStackTrace();
				}
				else{
					ViewResolver viewResolver = applicationContext.getBean(
						"viewResolver" , ViewResolver.class );
					request.setAttribute("err", e );
					request.setAttribute("ste", e.getStackTrace() );
					String l2 = viewResolver.getPrefix() + "system_error_view" + 
						viewResolver.getSuffix();
					RequestDispatcher rd = request.getRequestDispatcher( l2 );
					rd.forward( request, response );		
				}
			}
		}
		else if( methodAndTarget == null )
		{
			//	no mapping informations
			String l = actionURI + "에 해당하는 컨트롤러 함수가 없습니다.";
			onSystemMessageView(l, request, response,  applicationContext);
		}
	}
	
	private static final boolean errorConsoleView = true;
	
	private ServletContext application = null;
	private ApplicationContext applicationContext = null;
	
	private Map<String,MethodAndTarget> methodTargetMap = null;	
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		{	//	멤버변수들의 초기화 영역

			application = config.getServletContext();
			String path = config.getInitParameter("contextConfigLocation");
			String springConfig = application.getRealPath( path );
			applicationContext = new FileSystemXmlApplicationContext( springConfig );
		}

		methodTargetMap = new Hashtable<String,MethodAndTarget>();
		
		String[] names = applicationContext.getBeanDefinitionNames();
		for( String name : names ) 
		{
			Object ctrl = applicationContext.getBean(name);
			Class<?> cls = ctrl.getClass();
			Controller annot = cls.getAnnotation(Controller.class);
					
			if( annot == null ) {
				continue;
			}
			else 
			{
				Method[] mtds = cls.getMethods();
				for( Method mtd : mtds )
				{
					RequestMapping rm = mtd.getAnnotation( RequestMapping.class );
					if( rm == null ){
						continue;
					}
						
					String value = rm.value();
					if( value == null ){
						throw new RuntimeException("@RequestMapping 의 설정에 문제 있어요 ");
					}
					else if( value != null )
					{
						MethodAndTarget temp = methodTargetMap.get( value );
						if( temp != null )
						{
							String l = "중복된 mapping 정보네요 확인요망요 : " +
								cls.getName() + "::" + mtd.getName();
							throw new RuntimeException( l );
						}
						else{
							MethodAndTarget mnt = new MethodAndTarget( mtd, ctrl );
							methodTargetMap.put( value, mnt );
						}						
					}
				}
			}
		}
	}
	
	public static void onSystemMessageView(String l, HttpServletRequest request,
		HttpServletResponse response, ApplicationContext applicationContext) 
		throws ServletException, IOException 
	{
		ViewResolver viewResolver = applicationContext.getBean(
			"viewResolver" , ViewResolver.class );
			
		request.setAttribute("message", l );
		String l2 = viewResolver.getPrefix() + "system_message_view" + 
			viewResolver.getSuffix();
		RequestDispatcher rd = request.getRequestDispatcher( l2 );
		rd.forward( request, response );		
	}	
}


