package net.lising.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DaoHelper {

	@PersistenceContext
	protected EntityManager em;

	protected HibernateTemplate getHibernateTemplate() {
		HibernateTemplate hibernateTemplate = null;
		Session session = (Session) em.getDelegate();
		hibernateTemplate = new HibernateTemplate(session.getSessionFactory());
		return hibernateTemplate;
	}

	protected String[] getParams(Map<String, Object> queryParams) {
		String[] params = new String[0];
		if (null != queryParams) {
			params = new String[queryParams.size()];
			int index = 0;
			Set<String> ps = queryParams.keySet();
			for (String p : ps){
				params[index++] = p;
			}
		}
		return params;
	}
	
	protected Object[] getValues(Map<String, Object> queryParams) {
		String[] params = getParams(queryParams);
		Object[] values = new Object[params.length];
		int index = 0;
		for (String p : params){
			values[index++] = queryParams.get(p);
		}
		return values;
	}
	
	private  Method findSetter(Method[] ms,String f){
		Method method = null;
			for (Method m : ms) {
				if (("set"+f).equals(m.getName())){
					method = m;
					break;
				}
			}
			return method;
	}
	
	private  Method findGetter(Method[] ms,String f){
		Method method = null;
			for (Method m : ms) {
				if (("get"+f).equals(m.getName())||("is"+f).equals(m.getName())){
					method = m;
					break;
				}
			}
			return method;
	}
	protected  <T>List<T> packageEntity(String columns,List<Object[]> values,Class<T> clazz)
															{
		List<T> result = new ArrayList<T>();
		String[] mm = columns.replaceAll(" ", "").split(",");
		Method[] ms = clazz.getMethods();
		try{
		for(Object[] value:values){
			T t = (T)clazz.newInstance();
			Object[] objs = value;
			for(int i=0;i<mm.length;i++){
				char firstChar = mm[i].charAt(0);
				String lastStr = (firstChar+"").toUpperCase() + mm[i].substring(1);
				Method gm = findGetter(ms,lastStr);
				Method sm = findSetter(ms,lastStr);
				DataTypeEnumUtil[] dt = DataTypeEnumUtil.values();
				Object o = null;
				for(DataTypeEnumUtil dte:dt){
					if(dte.findClass()==gm.getReturnType()){
						o = dte.convertDataType(objs[i]);
						break;
					}
				}
				sm.invoke(t,o);
			}
			result.add(t);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
}
