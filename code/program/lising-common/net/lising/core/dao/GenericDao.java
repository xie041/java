package net.lising.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.lising.core.model.DataPage;
import net.lising.core.model.Where;

/**
 * 数据底层操作接口
 * @author jin.lishi(jsworld@qq.com)
 * @param <T> 泛型实体
 * @param <PK> 泛型主键
 */
public interface GenericDao {

	/**
	 * 根据实体获取表名
	 * @param clazz
	 * @return
	 */
	<T, PK extends Serializable> String getTableName(Class<T> clazz);
	
	/**
	 * 根据实体主键ID来查询实�?
	 * @param pk 主键
	 * @param clazz 实体类型
	 * @return 泛型实体
	 */
	<T, PK extends Serializable> T findById(Class<T> clazz,PK pk);
	
	/**
	 * @param clazz
	 * @param where
	 * @param properties
	 * @param params
	 * @param orderBy
	 * @return
	 */
	<T, PK extends Serializable> List<T> listByWhere(Class<T> clazz, Where where, String properties,
			Map<String, Object> params,String orderBy);
	
	<T, PK extends Serializable> List<T> listByWhere(Class<T> clazz, Where where,
			Map<String, Object> params,String orderBy);
	
	<T, PK extends Serializable> List<T> listByWhereTop(Class<T> clazz, Where where,
			Map<String, Object> params,String orderBy,int begin,int end);
	
	<T, PK extends Serializable> T findByWhereTop(Class<T> clazz, Where where,
			Map<String, Object> params,String orderBy,int top);
	
	
	<T, PK extends Serializable> T findByWhere(Class<T> clazz, Where where,
			Map<String, Object> params);
	
	
	<T, PK extends Serializable> List<T> list(Class<T> clazz);
	
	/**
	 * 按照字段查询
	 * @param select
	 * @param clazz
	 * @param where
	 * @param params
	 * @return
	 */
	<T, PK extends Serializable> List<Object[]> selectResult(String select ,Class<T> clazz, Where where,
			Map<String, Object> params);
	
	
	/**
	 * 分页查询数据
	 * <pre>
	 * @Override
	 *	public DataPage<Student> listOfPage() {
	 *		Where where = new Where();
	 *		where.append("name =:name");
	 *		where.append("title like:title");
	 *		Map<String, Object> params = new HashMap<String, Object>();
	 *		params.put("name", "jinlishi");
	 *		params.put("title", "%good%");
	 *		String orderKey = " order by id  ";
	 *		DataPage<Student> data = dao.listOfPage(Student.class, where, "id,name,age", params,2, 10, orderKey);
	 *		return data;
	 *	}
	 *
	 * </pre>
	 * @param clazz 实体类型
	 * @param where 查询条件
	 * @param properties �?��获取的属性字�?
	 * @param params 条件�?
	 * @param pageNo 当前�?
	 * @param pageSize 页显示行�?
	 * @param orderKey 排序条件
	 * @return
	 */
	<T, PK extends Serializable> DataPage<T> listOfPage(Class<T> clazz, Where where, String properties,
			Map<String, Object> params,int pageNo,int pageSize,String orderKey);
	
	
	/**
	 * 更新数据
	 * <p>如果实体内存在ID值且大于0,则会修改数据库已经存在对应的数据
	 * <p>如果实体内不存在ID�?则会添加�?��新数�?
	 * @param t 待处理泛型实�?
	 * @throws Exception
	 */
	<T, PK extends Serializable> void save(T t) throws Exception;
	
	<T, PK extends Serializable> void update(T t) throws Exception;
	
	/**
	 * 根据实体主键ID来删除对应数�?
	 * @param clazz
	 * @param pk
	 * @throws Exception
	 */
	<T, PK extends Serializable> void delete(Class<T> clazz,PK pk) throws Exception;
	
	/**
	 * 查询符合条件的数据行�?
	 * @param clazz 实体类型
	 * @param where 查询条件
	 * @param params 条件�?
	 * @return 行数
	 */
	<T, PK extends Serializable> int rows(Class<T> clazz,Where where,Map<String,Object> params);
	
	/**
	 * <pre>
	 * delete from [table-name] where name=:name
	 * Map<String, Object> params = new HashMap<String, Object>();
	 * params.put("name", "jinlishi");
	 * </pre>
	 * (不建议使�?�?�?改都可以执行
	 * @param sql 自定义传统sql语句
	 * @param params 条件对应的�?
	 * @return �?��响的行数
	 * @throws Exception
	 */
	int execute(String sql,Map<String,Object> params) throws Exception;
	
	/**
	 *  根据原始的sql语句来查询
	 * @param sql
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param orderKey
	 * @return
	 */
	List<Object[]> listOfPage(String sql,Map<String, Object> params,int pageNo,int pageSize,String orderKey);
	
	int rows(String sql,Map<String,Object> params);
	
	/**
	 * <pre>
	 * <h1>USAGE:</h1>
	 * listByOperator(Class,"age","in","(12,25,38)");
	 * listByOperator(Class,"age","between","30 and 50");
	 * listByOperator(Class,"name","like","%a");
	 * listByOperator(Class,"name","like","%a%");
	 * listByOperator(Class,"name","like","a%");
	 * 
	 * <h2>why?:</h2>
	 * <h3>BETWEEN：</h3>
	 * 比如，我们使用操作符BETWEEN来查询金额在300到1000之间的订单，代码如下所示：
     * Query query = em.createQuery(
     * "select o from Order as o where o.amount between 300 and 1000");
     * List result = query.getResultList();
     * 
     * <h3>IN:</h3>
     * 我们使用操作符IN来查询所有年龄为18，25 的Person。如以下语句所示：
     * Query query = em.createQuery("select p from Person as p where p.age in(18,25)");
     * List result = query.getResultList();
     * 
     * <h3>LIKE ：</h3>
     * 和使用SQL语句查询类似，使用操作符LIKE同样也可以实现模糊查询。如以下语句就可以实现查找以字符串"li"开头的Person：
     * Query query = em.createQuery("select p from Person as p where p.name like 'li%'");
     * List result = query.getResultList(); 
	 * </pre>
	 * @param <T>
	 * @param clazz
	 * @param fieldName
	 * @param operator
	 * @return
	 */
	<T extends Serializable> List<T> listByOperator(Class<T> clazz,String key,String operator,String value);
}