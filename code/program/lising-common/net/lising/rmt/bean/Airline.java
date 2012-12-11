package net.lising.rmt.bean;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author xie041
 * 航空公司
 */
@Table("t_airline")
public class Airline implements Serializable{
	
	private static final long serialVersionUID = -244706124764061268L;

	public Airline() {
		super();
	}

	public Airline(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	@Id
	@Column
	private int id;
	
	@Column
	private String code;
	
	@Column
	private String name;

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
	 * 获取code  
	 * @return code code  
	 */
	public String getCode() {
		return code;
	}

	/**  
	 * 设置code  
	 * @param code code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**  
	 * 获取name  
	 * @return name name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置name  
	 * @param name name  
	 */
	public void setName(String name) {
		this.name = name;
	}
}
