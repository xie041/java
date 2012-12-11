package net.lising.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.lising.urls.ProjectUtil;

/**
 * 版权所有
 * @author xie041
 */
public class Copyright extends TagSupport {
	
	private static final long serialVersionUID = 747470764521113986L;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(getHtml());
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	/**
	 * <pre>
	 *<ul id="footer" class="mt10 mb10">
	 *  <li class="qq-links">
	 *	  <a href="javascript:void(0);">TT下载</a>|<a href="javascript:void(0);">关于我们</a>|<a href="javascript:void(0);">广告服务</a>|<a href="javascript:void(0);">合作伙伴</a>|<a href="javascript:void(0);">帮助中心 </a>|<a href="javascript:void(0);">诚征英才</a>|<a href="javascript:void(0);">联系我们</a>|<a href="javascript:void(0);">网站地图</a>|<a href="javascript:void(0);">法律声明</a>|<a href="javascript:void(0);">举报建议</a>
	 *  </li>
	 *  <li><span>Copyright &copy; 2008 - 2011 Tourie. All Rights Reserved</span><span>京ICP备11007300号</span><span>中国旅游行业网  <a href="javascript:void(0);">版权所有</a></span></li>
	 *</ul>
	 *</pre>
	 */
	private static String getHtml(){
		String copyright = "<span>Copyright &copy; 2008 - 2012 Tourie. All Rights Reserved</span><span>京ICP备11007300号</span>";
		String[] item = {"网站地图","广告服务","合作伙伴","诚聘英才","举报建议","法律声明","关于我们","帮助中心"};
		StringBuffer bf = new StringBuffer("<ul id='footer' class='mt10 mb10'>");
		bf.append("<li class='qq-links'>");
		
		for(int i=0;i<item.length;i++){
			bf.append("<a href='javascript:void(0);'>");
			bf.append(item[i]);
			if(i != item.length-1){
				bf.append("</a>|");
			}else{
				bf.append("</a>");
			}
		}
		
		bf.append("</li>");
		bf.append("<li>"+copyright);
		bf.append("<span><a href='"+ProjectUtil.getDefaultProject()+"'>中国旅游行业网</a> 版权所有</span>");
		bf.append("</li>");
		bf.append("</ul>");
		return bf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getHtml());
	}
}