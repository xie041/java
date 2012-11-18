package net.lising.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <pre>
 * 字符长度截取类
 * 用例：
 * 		1：添加URL
 * 			<%@ taglib uri="/WEB-INF/tlds/lising.tld" prefix="ls"%>
 * 		2：使用方法
 * 			<ls:subString subString="${o.name}" subLen="20" />
 * 使用时传入两个参数：数据源和要截取的长度
 * @author cheng.rong
 * </pre>
 */
public class SubString extends TagSupport {

	private static final long serialVersionUID = -1199317115741186365L;

	/**
	 * 源字符串
	 */
	private String source;
	/**
	 * 需要截取的长度
	 */
	private int showLength = 20;
	
	/**
	 * 超过长度的，用什么字符显示，默认使用"..."
	 */
	private String replaceStr = "...";

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer showStr = new StringBuffer("");
		try {
			showStr.append("<ins title='" + source + "'>");
			if (source.length() > showLength)
				showStr.append(source.substring(0, showLength) + replaceStr);
			else
				showStr.append(source);
			showStr.append("</ins>");
			out.print(showStr.toString());
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return super.doStartTag();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**  
	 * 获取需要截取的长度  
	 * @return showLength 需要截取的长度  
	 */
	public int getShowLength() {
		return showLength;
	}

	/**  
	 * 设置需要截取的长度  
	 * @param showLength 需要截取的长度  
	 */
	public void setShowLength(int showLength) {
		this.showLength = showLength;
	}

	/**  
	 * 获取用什么字符显示，默认使用  
	 * @return replaceStr 用什么字符显示，默认使用  
	 */
	public String getReplaceStr() {
		return replaceStr;
	}

	/**  
	 * 设置用什么字符显示，默认使用  
	 * @param replaceStr 用什么字符显示，默认使用  
	 */
	public void setReplaceStr(String replaceStr) {
		this.replaceStr = replaceStr;
	}
}
