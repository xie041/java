package net.lising.rmt.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Province.java
 *
 * @version Province
 * @author 冷燕她哥
 * @date 2012-5-11 下午03:29:27
 */
@Table("t_base_provinces")
public class Province {
	
	@Id
	@Column
	private int id;
	
	/**
	 * 省份名称
	 */
	@Column
	private String provinceName;

	/**  
	 * 获取id  
	 * @return id id  
	 */
	public int getId() {
		return id;
	}

	/**  
	 * 设置id  
	 * @param id id  
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**  
	 * 获取省份名称  
	 * @return provinceName 省份名称  
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**  
	 * 设置省份名称  
	 * @param provinceName 省份名称  
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Override
	public String toString() {
		return "Province [id=" + id + ", provinceName=" + provinceName + "]";
	}
	
}
