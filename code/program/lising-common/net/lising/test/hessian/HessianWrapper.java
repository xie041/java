package net.lising.test.hessian;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * <pre>
 * usage:
 * Tservice t1 = new Factory<Tservice>().getService(Tservice.class,"http://url");
 * </pre>
 * @author xie041
 * @param <T>
 */
public class HessianWrapper<T> {
	
	@SuppressWarnings("unchecked")
	public T getService(Class<T> clazz,String remoteUrl){
		
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);// 解决同名方法冲突问题
		T service;
		try {
			service = (T) factory.create(clazz,remoteUrl);
			System.out.println(clazz.getName());
		} catch (Exception e) {
			service = null;
			e.printStackTrace();
		}
		return service;
	}
	
}
