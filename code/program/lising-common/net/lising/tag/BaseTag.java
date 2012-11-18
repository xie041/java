package net.lising.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class BaseTag extends TagSupport{

	private static final long serialVersionUID = 8684577829643181400L;

	protected String getPath() {
		String path = null;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String tempPath = request.getContextPath();
		path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + tempPath + "/";
		return path;
	}
	
	protected abstract void initTag();
}