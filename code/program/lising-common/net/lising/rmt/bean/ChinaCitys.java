package net.lising.rmt.bean;

import java.io.Serializable;

import net.lising.lib.PinyinUtil;

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
public class ChinaCitys implements Serializable{
	
	private static final long serialVersionUID = -1946106841142747240L;

	public ChinaCitys() {
		
	}

	/**
	 * @param cityName 城市名称
	 * @param father 省份ID
	 * @param level 行政级别
	 */
	public ChinaCitys(String cityName, int father, String level) {
		this(cityName,pinyin(cityName, true) ,pinyin(cityName,false),father,level);
	}

	public ChinaCitys(String cityName, String pinyin, String shortPinyin,int father, String level) {
		this.cityName = cityName;
		this.pinyin = pinyin;
//		this.shortPinyin = shortPinyin;
		this.father = father;
		this.level = level;
	}
	
	/**
	 * @param src
	 * @param b true为全拼音  false为首字母拼音
	 * @return
	 */
	private static String pinyin(String src,boolean b){
//		if(src!=null && src.equals("桐乡")){
//			System.out.println("开始监控");
//		}
//		String ret = "";
//		if(b){
//			ret = PinyinUtil.hanziToPinyin(src);
//		}
		return PinyinUtil.hanziToPinyin(src);
//		System.out.println("----------缩写：" + src + " 开始-----------");
//		String[] s = PinyinUtil.getHeadByString(src, false);
//		System.out.println("----------缩写：" + src + " 结束-----------");
//		StringBuffer temp = new StringBuffer("");
//		for(String s1: s){
//			temp.append(s1);
//		}
//		System.out.println( src + " " +temp.toString());
//		return temp.toString();
	}

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
	 * 拼音第一个字母
	 */
	@Column
	private String shortPinyin;
	
	/**
	 * 省份ID
	 */
	@Column
	private int father;
	
	/**
	 * 行政级别：省会、直辖市、副省级、地级市、县级市、
	 */
	@Column
	private String level;

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

	/**  
	 * 获取行政级别：省会、直辖市、副省级、地级市、县级市、  
	 * @return level 行政级别：省会、直辖市、副省级、地级市、县级市、  
	 */
	public String getLevel() {
		return level;
	}

	/**  
	 * 设置行政级别：省会、直辖市、副省级、地级市、县级市、  
	 * @param level 行政级别：省会、直辖市、副省级、地级市、县级市、  
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**  
	 * 获取拼音第一个字母  
	 * @return shortPinyin 拼音第一个字母  
	 */
	public String getShortPinyin() {
		return shortPinyin;
	}

	/**  
	 * 设置拼音第一个字母  
	 * @param shortPinyin 拼音第一个字母  
	 */
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	@Override
	public String toString() {
		return "ChinaCitys [cityName=" + cityName + ", father=" + father
				+ ", id=" + id + ", level=" + level + ", pinyin=" + pinyin
				+ ", shortPinyin=" + shortPinyin + "]";
	}
}
