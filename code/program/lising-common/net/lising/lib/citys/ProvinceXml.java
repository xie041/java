package net.lising.lib.citys;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <pre>
 * <Provinces>
 * 	<province>
 * 		<id>1</id>
 * 		<name>直辖市</name>
 * 		<centerCityId>1</centerCityId>
 * 		<centerCityName>北京</centerCityName>
 * 		<orderby>0</orderby>
 * 		<pinyin>ZhiXiaShi</pinyin>
 * 	</province>
 * 	<province>
 * 		<id>2</id>
 * 		<name>安徽省</name>
 * 		<centerCityId>5</centerCityId>
 * 		<centerCityName>合肥</centerCityName>
 * 		<orderby>13</orderby>
 * 		<pinyin>AnHui</pinyin>
 * 	</province>
 * </Provinces>
 * </pre>
 * 省份实体类
 */
public class ProvinceXml {

	private String id;

	private String name;

	private String centerCityId;

	private String centerCityName;

	private String pinyin;

	/**
	 * 排序
	 */
	private Integer orderby;

	private List<Map<String, String>> cityList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCenterCityId() {
		return centerCityId;
	}

	public void setCenterCityId(String centerCityId) {
		this.centerCityId = centerCityId;
	}

	public String getCenterCityName() {
		return centerCityName;
	}

	public void setCenterCityName(String centerCityName) {
		this.centerCityName = centerCityName;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public List<Map<String, String>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, String>> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
