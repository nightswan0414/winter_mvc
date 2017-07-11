package mango.main;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class ModelAndView {
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	private String viewName = null;
	private Map<String,Object> oMap = null;
	
	public ModelAndView(){
		
	}

	public ModelAndView( String viewName ){
		this.viewName = viewName;
	}
	
	public ModelAndView addObject(String key, Object val) {
		if( key != null && val != null ){
			if( oMap == null ){
				oMap = new Hashtable<String,Object>();			
			}
			oMap.put(key, val);
		}
		return this;
	}
	
	public boolean hasObject( String key ){
		if( oMap == null ){
			return false;
		}else{
			return oMap.containsKey( key );
		}
	}

	public void plant( HttpServletRequest request ){
		if( oMap == null ){
			return;
		}
		
		Set<String> ks = oMap.keySet();
		for( String k : ks ){
			Object v = oMap.get(k);
			request.setAttribute( k, v ); 
		}
	}
	
	public void print(){
		System.out.println( oMap );
	}
}




