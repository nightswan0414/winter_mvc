package mango.scan_target;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import mango.main.annotations.Controller;
import mango.main.annotations.ModelAttribute;
import mango.main.annotations.RequestMapping;
import mango.main.annotations.RequestParam;
import mango.main.annotations.ResponseBody;

@Controller
public class ForTestCtrl 
{
	@RequestMapping(value="/ping.do")
	public String ping() throws Exception
	{
		System.out.println("CtrlTest::ping");
		return "ping";
	}
	
	@RequestMapping(value="/ping2.do")
	@ResponseBody
	public String ping2() throws Exception
	{
		System.out.println("CtrlTest::ping2");
		return "æ»≥Á«œººø‰??";
	}

	@RequestMapping(value="/ping3.do")
	@ResponseBody
	public String ping3( @RequestParam("l") String l,
		HttpServletRequest request ) throws Exception
	{
		System.out.println("CtrlTest::ping3");
		System.out.println( request.getAttribute("l") );
		return l;
	}
	
	@RequestMapping(value="/ping4.do")
	@ResponseBody
	public String ping4( @RequestParam("l") String l,
		HttpServletRequest request ) throws Exception
	{
		System.out.println("CtrlTest::ping4");
		System.out.println( request.getAttribute("l") );
		return l;
	}

	@Autowired
	public ForTestDAO dao = null;
	
	@RequestMapping(value="/ping5.do")
	@ResponseBody
	public String ping5() throws Exception
	{
		return dao.toString();
	}
}