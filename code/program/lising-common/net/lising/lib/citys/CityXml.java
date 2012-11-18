package net.lising.lib.citys;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <pre>
 * <?xml version="1.0" encoding="UTF-8"?>
 * <citys>
 * 	<city>
 * 		<cityId>1</cityId>
 * 		<cityName>北京</cityName>
 * 		<father>1</father>
 * 		<pinyin>Beijing</pinyin>
 * 		<hot>1</hot>
 * 	</city>
 * 	<city>
 * 		<cityId>2</cityId>
 * 		<cityName>天津</cityName>
 * 		<father>1</father>
 * 		<pinyin>Tianjin</pinyin>
 * 		<hot>1</hot>
 * 	</city>
 * 	<city>
 * 		<cityId>3</cityId>
 * 		<cityName>上海</cityName>
 * 		<father>1</father>
 * 		<pinyin>Shanghai</pinyin>
 * 		<hot>1</hot>
 * 	</city>
 * </pre>
 * 城市XML实体
 */
public class CityXml {

	/**
	 * 城市ID
	 */
	private String cityId;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 省份ID
	 */
	private String father;

	/**
	 * 城市名称拼音
	 */
	private String pinyin;

	/**
	 * 城市名称拼音首字母
	 */
	private Character firstChar;

	/**
	 * 是否是热门城市
	 */
	private Integer hot;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Character getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(Character firstChar) {
		this.firstChar = firstChar;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}