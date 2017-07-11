package mango.main;

public class UtilForName 
{
	//	setNameValue �� nameValue �� �ٲپ� �ִ� �Լ��̴�.
	public static String jTypePropName( String fn )
	{
		if( fn.startsWith("set") && fn.length() > 3 )
		{
			char l = (char)( fn.charAt(3) + 32 );
			
			StringBuffer propName = new StringBuffer();
			propName.append( l );
			propName.append( fn.substring(4) );
			String pn = propName.toString();
			
			return pn;
		}
		else
		{
			String el = "setter �Լ����� �������� �ʽ��ϴ�";
			throw new RuntimeException( el );			
		}
	}

	//	setNameValue �� name_value �� �ٲپ� �ִ� �Լ��̴�. ( �̿ϼ� )
	public static String cTypePropName( String fn )
	{
		if( fn.startsWith("set") && fn.length() > 3 )
		{
			StringBuffer sb = new StringBuffer();
			char ch = (char)( fn.charAt(3) + 32 );
			sb.append( ch );
			
			for( int i = 4 ; i < fn.length(); i++ ){
				ch = fn.charAt(i);
				if( ch >= 'A' && ch <= 'Z' ){
					sb.append("_");
					sb.append( (char)(ch + 32) );
				}
				else{
					sb.append( ch );
				}
			}
			return ( sb.toString() );
		}
		else{
			String el = "setter �Լ����� �������� �ʽ��ϴ�";
			throw new RuntimeException( el );
		}

	}
	
	
	/*	make "userId" TO "setUserId" 
	 */
	public static String setterName( String name ){
		if( name == null || name.equals("") ){
			return name;
		}
		if( name.startsWith("set") ){
			return name;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("set");
		sb.append( name );
		int r = sb.charAt(3);
		sb.setCharAt(3, (char)(r - 0x20) );
		return sb.toString();
	}
	

	/*	make "user_id" TO setUserId
	 */
	public static String setterName2( String name )
	{
		byte[] cs = name.getBytes();
		
		StringBuffer sb = new StringBuffer("set");
		sb.append( (char)( cs[0] - 32 ) );

		boolean up = false;
		for( int i = 1 ; i < cs.length ; i++ ){
			if( cs[i] == '_' ){
				up = true;
				continue;
			}
			
			if( up ){
				sb.append( (char)(cs[0] - 32) );
				up = false;
			}else{
				sb.append( (char)cs[i] );
			}
		}
		
		return ( sb.toString() );		
	}
}
