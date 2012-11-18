package org.jasig.cas.client;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.lising.lib.CookieUtil;
import net.lising.manage.org.bean.context.UserContext;
import net.lising.memcached.MemOperator;
import net.lising.urls.ProjectUtil;

import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

public class SessionFilter implements Filter{
	
	Logger log = Logger.getLogger(SessionFilter.class.getName());
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		log.debug("come in SessionFilter.doFilter()");
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		HttpSession session = request.getSession();
		
		Assertion assertion = session != null ? (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;
		log.debug("获取cas登陆后的assertion:   assertion=" + assertion);
		if(assertion == null) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		String userName = assertion.getPrincipal().getName();
		log.debug("获取到登陆的用户名："+userName);
		if(userName == null || userName.equals("")){
			log.fatal("从sso服务端返回的用户名为空，这里会出现异常");
			arg2.doFilter(arg0, arg1);
			return;
		}
		log.debug("获取到登陆用户名 ---> " + userName);
		log.debug("判断当前用户session是否为空，如果为空，则从缓存服务器去获取");
		UserContext ctx = (UserContext)request.getSession().getAttribute(ProjectUtil.CTX);
		
		if(ctx != null){
			log.debug("用户session不为空，不需要再次从Memcached中获取");
			log.debug("Enter Next Filter");
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		log.debug("程序能执行到这里，说明用户session为空，sso返回的用户("+userName+")不为空，即将从Memcached服务器中获取");
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) MemOperator.getLoginUser(userName);
			if(map == null){
				log.fatal("从Memcached获取用户信息失败，这是严重的错误！客户端的用户将没有session");
				arg2.doFilter(arg0, arg1);
				//throw new NullPointerException("从缓存获取用户失败");
				return;
			}
			ctx = (UserContext)map.get(userName);
			session.setAttribute(ProjectUtil.CTX, ctx);
			//所在城市
			session.setAttribute(ProjectUtil.CITYID, ctx.getCmp().getId());
			log.debug("***Memcached client fetch success***");
			log.debug("已经将用户存入session,使用 request.getSession().getAttribute(\"ctx\")获取");
			
			log.info("存入cookie，"+ProjectUtil.LOGINFLAG +"=true,弹出消息后，请设置为false" );
			CookieUtil.setCookie(request,response, ProjectUtil.LOGINFLAG, true+"");
		} catch (Exception e) {
			log.error("Memcached 在客户端获取失败,session存储失败"+e.getMessage());
		}
		arg2.doFilter(arg0, arg1);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
