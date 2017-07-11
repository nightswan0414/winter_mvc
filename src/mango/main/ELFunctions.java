package mango.main;

public class ELFunctions {


}

/*
	@SuppressWarnings("rawtypes")
	public final static boolean notExistInt( Integer i, List ls ){
		for( Object t : ls ){
			if( t.equals( i ) ){
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public final static boolean existInt( Integer i, List ls ){
		for( Object t : ls ){
			if( t.equals( i ) ){
				return true;
			}
		}
		return false;
	}
	
	public final static String defaultDesign( String l, String type )
	{
		if( Util.isNvd( l ) )
		{
			if( type.equals("html") ){
				return "<span id=\"abcd\">HelloWorld</span>";
			}
			else if( type.equals("css") ){
				return "#abcd {font-size:20px;}";
			}
			else if( type.equals("script") ){
				return "alert(\"HelloWorld\");";
			}
		}
		return l;
	}
	
	public final static String changeSpecialChar( String html ){
		StringBuffer sb = new StringBuffer();
		
		char[] cs = html.toCharArray();
		for( int i = 0 ; i < cs.length ; i++ )
		{
			if( cs[i] == '"' ){
				sb.append("&quot;");
			}else if( cs[i] == '<' ){
				sb.append("&lt;");
			}else if( cs[i] == '>' ){
				sb.append("&gt;");
			}else if( cs[i] == '\'' ){
				sb.append("&apos;");
			}else if( cs[i] == ' ' ){
				sb.append("&nbsp;");
			}else{
				sb.append( cs[i] );
			}
		}
		return sb.toString();		
	}
	
	
	 *	1 이면 true
	 * 	0 이면 false
	 * 	
	 * 	-1 이면 함수명을 잘못 적은것
	 * 	-2 이면 함수 실행시에 에러가 발생한것
	 * 	-3 함수의 리턴타입이 boolean 이 아닌 것
	 
	public final static int config( String l ){
		try{
			Method mtd = Config.class.getMethod( l );
			if( mtd != null ){
				Object obj = mtd.invoke( null );
				if( obj instanceof Boolean ){
					if( ((Boolean)obj).booleanValue() ){
						return 1;
					}else{
						return 0;
					}
				}else{
					return -3;
				}
			}else{
				return -1;
			}
		}
		catch( Exception e ){
			return -2;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public final static int talkNoCount( List ls, int j )
	{
		int count = 0;
		try{
			Method mtd = null;
			for( int i = 0 ; i < ls.size(); i++ ){
				if( mtd == null ){
					mtd = ls.get(i).getClass().getMethod("getTalkNo");
				}
				int t = (Integer)mtd.invoke( ls.get(i) );
				if( t == j ){
					count++;
				}
			}
		}
		catch( Exception e ){}
		return count;
	}
	
	public final static boolean isNull( Object obj )
	{
		return (obj == null);
	}

	public final static boolean isNotNull( Object obj )
	{
		return (obj != null);
	}
	
	public final static boolean isTrue( Boolean b )
	{
		return ( b != null && b.booleanValue() );
	}

	public final static boolean isFalse( Boolean b )
	{
		return ( b == null || !(b.booleanValue()) );
	}
	
	public final static boolean emptyList( List l )
	{
		return ( l == null || l.size() == 0 );
	}
	
	public final static boolean validList( List l )
	{
		return ( l != null && l.size() > 0 );
	}
	
	public final static boolean su( String cuid )
	{
		return ( cuid != null && cuid.equals("apple") );
	}

	public final static boolean notSu( String cuid )
	{
		return !su( cuid );
	}
	
	public final static String utf8( String l ){
		if( l == null || l.equals("") ){
			return l;
		}else{
			try{
				String l2 = java.net.URLEncoder.encode(l,"utf-8");
				return l2;
			}
			catch( Exception e ){
				return l;
			}
		}
	}

	public final static String u8( String l ){
		if( l == null || l.equals("") ){
			return "";
		}else{
			try{
				return java.net.URLEncoder.encode(l,"utf-8");
			}catch( Exception e ){
				return "";
			}
		}
	}

	public final static String u8( Integer l ){
		if( l == null ){
			return "";
		}else{
			return l.toString();
		}
	}
	
	public final static String shortDot( String l ){
		if( l == null || l.equals("") || l.length() < 9 ){
			return l;
		}else{
			return l.substring(0,6) + "...";
		}
	}
	
	public final static String nvl( String l, String r ){
		if( l == null || l.equals("") ){
			return r;
		}else{
			return l;
		}
	}
	
	public static String indentation( Integer val, String type ){
		if( "nbsp".equals(type) )
		{
			if( val != null ){
				StringBuffer sb = new StringBuffer();
				
				int val2 = val.intValue();
				for( int i = 0 ; i < val2 ; i++ ){
					sb.append("&nbsp;").append("&nbsp;").
						append("&nbsp;").append("&nbsp;");
				} 
				return sb.toString();
			}else{
				return "";
			}
		}
		return String.valueOf( val );		
	}
	
	public static String doneCodeText( String code ){
		return "I don't know";
	}
	
	public static String doneCodeAlert( String code ){
		return "<script>alert(\"I dont't know\");</script>";
	}
*/