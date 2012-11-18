/**
 * 
 */
package net.lising.frame.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lising.frame.utils.Logger;
import net.lising.manage.org.bean.context.UserContext;
import net.lising.memcached.MemOperator;


/**
 * @author 吕佳诚
 * 
 */
@SuppressWarnings("serial")
public class LogoutController extends HttpServlet {
	private Logger logger;
	
	/**
	 * 构造方法
	 */
	public LogoutController() {
		super();    
	}
	
	public void init() throws ServletException {
		this.logger = new Logger();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isImLogout = request.getParameter("from");
		
		if( isImLogout  != null ){
			request.getSession().invalidate();
			logger.info("项目[" + request.getParameter("project")+ "]session清除成功！");
		}else{
			//获取登陆用户名，清除Memcached缓存中的登陆用户
			UserContext ctx = (UserContext) request.getSession().getAttribute("ctx");
			if (ctx != null) {
				try {
					MemOperator.removeLoginUser(ctx.getUser().getUserName());
				} catch (Exception e) {
					logger.debug("清除Memcached缓存失败", e);
				}
			}
			request.getSession().invalidate();
			logger.info("项目[" + request.getParameter("project")+ "]session清除成功！");
		}
	}
	
}
