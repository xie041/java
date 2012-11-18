package net.lising.rmt.bean;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * <pre>
 * 项目名称：lising-tourie  
 * 类名称：City  
 * 类描述：  中国城市，不包含县级市
 * 创建人：xieyong   Email:xie041@126.com  QQ:190221242 
 * 创建时间：2011-9-15 下午01:47:27  
 * 修改人：xie041  
 * 修改时间：2011-9-15 下午01:47:27  
 * 修改备注：  
 * @version  
 * </pre> 
 */ 
@Table("t_base_citys_all")
public class City implements Serializable{
	
	private static final long serialVersionUID = -3171200141736025050L;

	@Id
	@Column
	private int id;
	
	/**
	 * 城市名称
	 */
	@Column
	private String cityName;
	
	/**
	 * 拼音
	 */
	@Column
	private String pinyin;
	
	/**
	 * 省份ID
	 */
	@Column
	private int father;

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
	 * 获取城市名称  
	 * @return cityName 城市名称  
	 */
	public String getCityName() {
		return cityName;
	}

	/**  
	 * 设置城市名称  
	 * @param cityName 城市名称  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**  
	 * 获取拼音  
	 * @return pinyin 拼音  
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**  
	 * 设置拼音  
	 * @param pinyin 拼音  
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**  
	 * 获取省份ID  
	 * @return father 省份ID  
	 */
	public int getFather() {
		return father;
	}

	/**  
	 * 设置省份ID  
	 * @param father 省份ID  
	 */
	public void setFather(int father) {
		this.father = father;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", cityName=" + cityName + ", pinyin="
				+ pinyin + ", father=" + father + "]";
	}
}
