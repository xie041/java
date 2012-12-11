package net.lising.core.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class AbstractSerBean implements Serializable{
	
	private static final long serialVersionUID = -8587297862487902820L;
	
	public static final String split_1 = "=";
	public static final String split_2 = "&";
	
	public AbstractSerBean(String ser){
	}
	
	public AbstractSerBean(){
	}
	public String openSer(){
		StringBuffer sb = new StringBuffer("");
		List<Method> methods = findGetters();
		for(Method method:methods){
			String field = method.getName().substring(3);
			sb.append(field.substring(0, 1).toLowerCase()+field.substring(1));
			try {
				String value = method.invoke(this)+"";
				sb.append(split_1+("".equals(value)?"":value)+split_2);
			} catch (Exception e) {
			} 
		}
		return sb.substring(0, sb.length()-split_2.length());
	}
	
	protected List<Method> findGetters(){
		List<Method> getters = new ArrayList<Method>();
		Method[] sms = this.getClass().getMethods();
		
		for(Method sm:sms){
			if(sm.getName().startsWith("get") && !"getClass".equals(sm.getName())){
				getters.add(sm);
			}
		}
		return getters;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
