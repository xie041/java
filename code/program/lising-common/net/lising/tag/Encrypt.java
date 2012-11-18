package net.lising.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.lising.lib.encrypt.SuperEncript;

public class Encrypt extends TagSupport {

	private static final long serialVersionUID = -1199317115741186365L;


	private Object encryptObj;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(SuperEncript.encryptEveryThing(encryptObj));
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public Object getEncryptObj() {
		return encryptObj;
	}

	public void setEncryptObj(Object encryptObj) {
		this.encryptObj = encryptObj;
	}
}
