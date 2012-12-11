package net.lising.core.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 条件
 * @author jin.lishi(jsworld@qq.com)
 *
 */
public class Where implements Serializable {

	private static final long serialVersionUID = -1810413214936764473L;
	private StringBuffer sb;
	private Map<String,Object> params;

	public Where() {
		sb = new StringBuffer(" 1 = 1 ");
	}

	public Where append(String s) {
		sb.append(s);
		return this;
	}

	public String getValue() {
		return sb.toString();
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
