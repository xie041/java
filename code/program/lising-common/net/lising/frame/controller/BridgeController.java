/**
 * 
 */
package net.lising.frame.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lising.urls.ProjectUtil;

import org.apache.log4j.Logger;


/** 
 * @author : 吕佳诚
 * @Description : TODO 
 * @CreateDate : 2012-4-14 下午04:44:04 
 * @lastModified : 2012-4-14 下午04:44:04 
 * @version : 1.0  
 */
public class BridgeController extends HttpServlet {
	
	private static final long serialVersionUID = -8889271556018745969L;
	
	private Logger log = Logger.getLogger(BridgeController.class);

	public void init() throws ServletException {
		super.init();
	}

	public void destroy() {
		super.destroy();
	}

	/* 
	 * 桥接lising-im服务器，因为跨域问题不能解决
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response){
        String curLine = null;  
        StringBuffer buf = new StringBuffer();
        try {
//        	logger.info("\t\t====获得心跳地址："+ProjectUtil.getImHeartbeatCheckerAddress());
			URL server=new URL(ProjectUtil.getImHeartbeatCheckerAddress() + "?" + request.getQueryString());  
			HttpURLConnection connection = (HttpURLConnection) server.openConnection();
			//设置超时时间,防止程序异常出现
			connection.setConnectTimeout(50*1000);
			connection.setReadTimeout(50*1000);
			connection.connect();
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
			while ((curLine = reader.readLine()) != null) { 
			    buf.append(curLine);
			}  
			is.close();  
			response.setContentType("text/xml");  
			response.setCharacterEncoding("utf-8");  
//			ServletOutputStream   out = response.getOutputStream();
//			out.print(buf.toString());
			response.getWriter().write(buf.toString());
		} catch (IOException e) {
			log.error("im server error: bridgecontoller error ",e);
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
