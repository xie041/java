package net.lising.lib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <pre>
 * 项目名称：lising-tourie  
 * 类名称：TestUtil  
 * 类描述：  测试工具类
 * 创建人：xieyong   Email:xie041@126.com  QQ:190221242 
 * 创建时间：2011-9-9 下午03:45:51  
 * 修改人：xie041  
 * 修改时间：2011-9-9 下午03:45:51  
 * 修改备注：  
 * @version  
 * </pre> 
 */ 
public class TestUtil{
	public static Object findService(String serviceName){
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean(serviceName);
	}
}
