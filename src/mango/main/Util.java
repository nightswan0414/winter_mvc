package mango.main;

import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Util 
{
	public static int randomInt( int max )
	{
		long seed = (long)(Math.random() * Long.MAX_VALUE);
		Random r = new Random( seed );
		return r.nextInt( max );
	}	
	
	public static <T extends Object> List<T> nvl( List<T> ls ){
		if( ls == null ){
			return new ArrayList<T>();
		}else{
			return ls;
		}
	}
	
	public static StringBuffer COMMA( StringBuffer sb ){
		if( sb == null ){
			return new StringBuffer();
		}else{
			return sb.append(",");
		}
	}
	
	public static <T extends Object> T nvl( T l, T r ){
		if( l != null ){
			return l;
		}else{
			return r;
		}
	}
	
	public static <T extends Annotation> boolean hasAnnotation( Class<?> cls, Class<T> anno )
	{
		if( cls == null || anno == null  ){
			return false;
		}
		
		try{
			T a = cls.getAnnotation( anno );
			return ( a != null );
		}
		catch( Exception e ){
			return false;
		}
	} 
	
	public static <T extends Annotation> T getAnnotation( Class<?> cls, Class<T> anno )
	{
		if( cls == null || anno == null  ){
			return null;
		}
		
		try{
			T a = cls.getAnnotation( anno );
			return a;
		}
		catch( Exception e ){
			return null;
		}
	} 	
	
	
	public static String getCookieParameter( HttpServletRequest request, 
		String key  )
	{
		Cookie[] cs = request.getCookies();
		if( cs != null ){
			for( int i = 0 ; i < cs.length; i++ ){
				if( key.equals( cs[i].getName() ) ){
					String l = cs[i].getValue();
					if( l == null || l.equals("") || l.equals("null") )	{
						return null;
					}else{
						return l;
					}
				}
			}
		} 
		return null;
	}	

	public static Integer pInt( Object l )
	{
		if( l == null ){
			return null;
		}else if( l instanceof String ){
			return pInt( (String)l );
		}else if( l instanceof Integer ){
			return (Integer)l;
		}else{
			return null;
		}
	}

	public static Integer pInt( Object l, Integer defaultValue )
	{
		if( l == null ){
			return defaultValue;
		}else if( l instanceof String ){
			return pInt( (String)l , defaultValue );
		}else if( l instanceof Integer ){
			return (Integer)l;
		}else{
			return defaultValue;
		}
	}
	
	public static Integer pInt( String l )
	{
		if( l == null || l.equals("") ){
			return null;
		}
		else{
			try{
				return Integer.parseInt( l );
			}
			catch( Exception e ){
				return null;
			}
		}
	}

	public static Integer pInt( String l, Integer defaultValue )
	{
		if( l == null || l.equals("") ){
			return defaultValue;
		}
		else{
			try{
				return Integer.parseInt( l );
			}
			catch( Exception e ){
				return defaultValue;
			}
		}
	}
	
	public static Integer pInt( HttpServletRequest request, String name )
	{
		String l = request.getParameter( name );
		
		if( l == null || l.equals("") ){
			return null;
		}
		else{
			try{
				return new Integer( l );
			}
			catch( Exception e ){
				return null;
			}
		}
	}
	
	
	public static Double pDbl( String l ){
		if( l == null || l.equals("") ){
			return null;
		}
		else{
			try{
				return Double.parseDouble( l );
			}
			catch( Exception e ){
				return null;
			}
		}
	}	

	public static Double pDbl( String l, Double defaultValue ){
		if( l == null || l.equals("") ){
			return defaultValue;
		}
		else{
			try{
				return Double.parseDouble( l );
			}
			catch( Exception e ){
				return defaultValue;
			}
		}
	}	

	
	public static String us2utf8( String l ){
		if( l == null || l.equals("") ){
			return null;
		}else{
			try{
				byte[] bs = l.getBytes("8859_1");
				return new String( bs, "utf-8");
			}
			catch( Exception e ){ return null; }
		}
	}

	public static String us2utf8( String l, String defaultValue ){
		if( l == null || l.equals("") ){
			return defaultValue;
		}else{
			try{
				byte[] bs = l.getBytes("8859_1");
				return new String( bs, "utf-8");
			}
			catch( Exception e ){ return defaultValue; }
		}
	}

	public static String euckr2utf8( String l )
	{
		return euckr2utf8( l, null );
	}
	
	public static String euckr2utf8( String l, String defaultValue ){
		if( l == null || l.equals("") ){
			return defaultValue;
		}else{
			try{
				byte[] bs = l.getBytes("euc-kr");
				return new String( bs, "utf-8");
			}
			catch( Exception e ){ return defaultValue; }
		}
	}
	
	
	public static String encodeUTF8( String l ){
		if( l == null || l.equals("") ){
			return l;
		}else{
			try{
				return URLEncoder.encode( l, "utf-8" );	
			}
			catch( Exception e ){
				return null;
			}
		}
	}
	
	public static String decodeUTF8( String l ){
		if( l == null || l.equals("") ){
			return l;
		}else{
			try{
				return URLDecoder.decode( l, "utf-8" );	
			}
			catch( Exception e ){
				return null;
			}
		}
	}

	public static String urlAppend(String url, String l ) {
		if( url == null ){
			return null;
		}
		else if( l == null ){
			return url;
		}
		else{
			if( url.endsWith(".do") || url.endsWith(".jsp") ){
				return url + "?" + l;
			}else{
				return url + "&" + l;
			}
		}
	}

	public static String mysqlDate( String t ){
		String now = date("-");
		if( t.startsWith( now ) ){
			return ( t.substring(11,19) );
		}else{
			return ( t.substring(0,10) );
		}
	}
	
	public static String date( String delim ){
		Calendar calendar = Calendar.getInstance();
		int yy = calendar.get(Calendar.YEAR);
		int mm = calendar.get(Calendar.MONTH) + 1;
		int dd = calendar.get(Calendar.DAY_OF_MONTH);

		StringBuffer l = new StringBuffer();
		l.append( yy ).append( delim );
		if( mm < 10 ){
			l.append("0");
		}
		l.append(mm).append( delim );
		if( dd < 10 ){
			l.append("0");
		}
		l.append(dd);
		
		return l.toString();
	}
	
	public static Throwable finalCause(Throwable e) {
		Throwable e2 = null;
		while( true ){
			e2 = e.getCause();
			if( e2 != null ){
				e = e2;
			}else{
				break;
			}
		}
		return e;
	}	
	
	
	public static String varchar( String l )
	{
		StringBuffer sb = new StringBuffer();
		
		char[] cs = l.toCharArray();
		for( int i = 0 ; i < cs.length ; i++ ){
			int j = cs[i];
			if( j == 34 || j == 39 || j == 92 ){
				sb.append( (char)(92) );	//	 slash
			}
			sb.append(cs[i]);
		}
		return (sb.toString());		
	}
	
	public static void e( Throwable e ){
		if( e != null ){
			e.printStackTrace();
		}
	}

	public static boolean isVd( String l ) {
		if( l == null || l.equals("") ){
			return false;
		}else{
			for( int i = 0 ; i < l.length() ; i++ ){
				char ch = l.charAt(i);
				if( ch != ' ' || ch != '\r' || ch != '\n' ){
					return true;
				}
			}
			return false;
		}
	}

	public static boolean isVd(Integer arg) {
		return ( arg != null );
	}

	
	public static boolean isNvd(String arg) {
		return !isVd( arg );
	}

	public static boolean isNvd(Integer arg) {
		return !isVd( arg );
	}

	public static String toHexString( int val, int len )
	{
		StringBuffer sb = new StringBuffer();
		String l = Integer.toHexString( val );
		sb.append( l );
		while( sb.length() < len ){
			sb.insert(0,'0');			
		}
		return sb.toString();
	}

	public static String toBinString(int val, int len) {
		StringBuffer sb = new StringBuffer();
		String l = Integer.toBinaryString( val );
		sb.append( l );
		while( sb.length() < len ){
			sb.insert(0,'0');			
		}
		return sb.toString();
	}

	public static boolean isNotExistsIn(String val, String[] ls) 
	{
		for( int i = 0 ; i < ls.length ; i++ ){
			if( ls[i].equals( val ) ){
				return false;
			}
		}
		return true;
	}

	public static boolean hasMatchAtCode(int codes, int code) 
	{
		return ( (codes & code) != 0 );
	}
	
	public static int addCode(int codes, int code) {
		return ( codes | code );
	}
}
