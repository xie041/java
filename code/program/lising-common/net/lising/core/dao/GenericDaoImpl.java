package net.lising.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.Table;

import net.lising.core.model.DataPage;
import net.lising.core.model.Where;
import net.lising.core.util.DaoHelper;

import org.springframework.stereotype.Repository;

/**
 * 数据底层操作接口实现
 */

@SuppressWarnings("unchecked")
@Repository
public class GenericDaoImpl extends DaoHelper implements GenericDao {

	@Override
	public List<Object[]> listOfPage(String sql, Map<String, Object> params,
			int pageNo, int pageSize, String orderKey) {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		Query query = em.createQuery(sql);
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public int rows(String sql, Map<String, Object> params) {
		String[] ps = this.getParams(params);
		Object[] vs = this.getValues(params);
		Object o = getHibernateTemplate().findByNamedParam(
				sql, ps, vs).get(0);
		return Integer.parseInt(o + "");
	}

	
	private  <T,PK extends Serializable>String className(Class<T> clazz) {
		return clazz.getSimpleName();
	}
	
	@Override
	public <T,PK extends Serializable> void delete(Class<T> clazz,PK pk) throws RuntimeException {
		T t = em.find(clazz, pk);
		if (null != t)
			em.remove(t);
	}

	@Override
	public int execute(String sql, Map<String, Object> params) throws RuntimeException {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
	    Query query =  em.createNativeQuery(sql);
	    for(int i=0;i<ps.length;i++){
	    	 query.setParameter(ps[i], vs[i]);
	    }
		return query.executeUpdate();
	}

	@Override
	public <T,PK extends Serializable>T findById(Class<T> clazz, PK pk) {
		return em.find(clazz, pk);
	}
	@Override
	public <T,PK extends Serializable>DataPage<T> listOfPage(Class<T> clazz, Where where,
			String properties, Map<String, Object> params, int pageNo,
			int pageSize, String orderKey) {
		
		DataPage<T> pageData = new DataPage<T>();
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer("select "+properties + " from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		queryStr.append(" ").append(null != orderKey?orderKey:"");
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<Object[]> list = query.getResultList();
		List<T> result = this.packageEntity(properties, list, clazz);
		pageData.setDatas(result);
		pageData.setCurrPage(pageNo);
		int count = rows(clazz,where,params);
		pageData.setTotalRows(count);
		pageData.setPageSize(pageSize);
		return pageData;
	}

	@Override
	public <T,PK extends Serializable>int rows(Class<T> clazz, Where where, Map<String, Object> params) {
		String[] ps = this.getParams(params);
		Object[] vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer("select count(id) from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		Object o = getHibernateTemplate().findByNamedParam(
				queryStr.toString(), ps, vs).get(0);
		return Integer.parseInt(o + "");
	}
	
	

	@Override
	public <T,PK extends Serializable>void save(T t) throws RuntimeException {
		em.persist(t);
	}
	
	@Override
	public <T,PK extends Serializable> void update(T t) throws RuntimeException {
		if(em.contains(t)){
			em.persist(t);
		}else{
			em.merge(t);
		}
	}
	
	@Override
	public <T,PK extends Serializable> String getTableName(Class<T> clazz) {
		Table a = (Table) clazz.getAnnotation(Table.class);
		if (null != a)
			return a.name().toLowerCase();
		return null;
	}

	@Override
	public <T,PK extends Serializable> List<T> list(Class<T> clazz) {
		String className = className(clazz);
		StringBuffer queryStr = new StringBuffer(" from "+ className);
		Query q = em.createQuery(queryStr.toString());
		List<T> list = q.getResultList();
		return list;
	}

	@Override
	public <T,PK extends Serializable> T findByWhere(Class<T> clazz, Where where,
			Map<String, Object> params){
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer(" from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		List<T> list = query.getResultList();
		if(list.size()!=1){
			return null;
		}else{
			return list.get(0);
		}
		
		
	}

	@Override
	public <T,PK extends Serializable> List<T> listByWhere(Class<T> clazz, Where where, String properties,
			Map<String, Object> params, String orderKey) {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer("select "+properties + " from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		queryStr.append(" ").append(null != orderKey?orderKey:"");
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		List<Object[]> list = query.getResultList();
		List<T> result = this.packageEntity(properties, list, clazz);
		return result;
	}

	@Override
	public <T,PK extends Serializable>List<T> listByWhere(Class<T> clazz, Where where,
			Map<String, Object> params, String orderKey) {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer(" from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		queryStr.append(" ").append(null != orderKey?orderKey:"");
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		List<T> list = query.getResultList();
		return list;
	}

	@Override
	public <T,PK extends Serializable>List<Object[]> selectResult(String select ,Class<T> clazz, Where where,
			Map<String, Object> params) {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
	    // eg: select name,password form table
		StringBuffer queryStr = new StringBuffer(select+ " from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public <T,PK extends Serializable>T findByWhereTop(Class<T> clazz, Where where,
			Map<String, Object> params, String orderBy, int top) {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer(" from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		queryStr.append(" ").append(null != orderBy?orderBy:"");
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		query.setFirstResult(top);
		query.setMaxResults(top+1);
		List<T> list = query.getResultList();
		if(list.size()==1)
		return list.get(0);
		else
			return null;
	}

	@Override
	public <T,PK extends Serializable>List<T> listByWhereTop(Class<T> clazz, Where where,
			Map<String, Object> params, String orderBy, int begin, int end) {
		String []ps = this.getParams(params);
	    Object []vs = this.getValues(params);
		StringBuffer queryStr = new StringBuffer(" from "+ className(clazz));
		if(null != where && !"".equalsIgnoreCase(where.getValue())){
			queryStr.append(" where ").append(where.getValue());
		}
		queryStr.append(" ").append(null != orderBy?orderBy:"");
		Query query = em.createQuery(queryStr.toString());
		for(int i=0;i<ps.length;i++){
			query.setParameter(ps[i], vs[i]);
		}
		query.setFirstResult(begin);
		query.setMaxResults(end);
		List<T> list = query.getResultList();
		return list;
	}

	@Override
	public <T extends Serializable>List<T> listByOperator(Class<T> clazz,String key,String operator,String value){
		StringBuffer q = new StringBuffer(" from "+ className(clazz));
		q.append(" where " + key + " " + operator + " " + value);
		Query query = em.createQuery(q.toString());
		return query.getResultList();
	}
}
