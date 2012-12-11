package net.lising.rmt.bean;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author xie041
 * 机场三字码
 * [\"AOG\",\"鞍山\",\"anshan\",\"AS\"]
 */
@Table("t_airport_code")
public class AirportThreeCharacterCode implements Serializable{
	
	private static final long serialVersionUID = 6452182218463344552L;

	public AirportThreeCharacterCode() {
		super();
	}
	
	public AirportThreeCharacterCode(String code, String name, String pinyin,
			String shortPinyin) {
		super();
		this.code = code;
		this.name = name;
		this.pinyin = pinyin;
		this.shortPinyin = shortPinyin;
	}

	public AirportThreeCharacterCode(String code, String name, String pinyin,
			String shortPinyin, int cityId, int provinceId) {
		super();
		this.code = code;
		this.name = name;
		this.pinyin = pinyin;
		this.shortPinyin = shortPinyin;
		this.cityId = cityId;
		this.provinceId = provinceId;
	}

	public AirportThreeCharacterCode(String code, String name, int cityId,
			int provinceId) {
		super();
		this.code = code;
		this.name = name;
		this.cityId = cityId;
		this.provinceId = provinceId;
	}

	public AirportThreeCharacterCode(int id, String code, String name,
			int cityId, int provinceId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.cityId = cityId;
		this.provinceId = provinceId;
	}

	public AirportThreeCharacterCode(int id, String code, String name,
			String pinyin, String shortPinyin, int cityId, int provinceId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.pinyin = pinyin;
		this.shortPinyin = shortPinyin;
		this.cityId = cityId;
		this.provinceId = provinceId;
	}


	@Id
	@Column
	private int id;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column
	private String pinyin;
	
	@Column
	private String shortPinyin;
	
	@Column
	private int cityId;
	
	@Column
	private int provinceId;

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

	/**  
	 * 获取pinyin  
	 * @return pinyin pinyin  
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**  
	 * 设置pinyin  
	 * @param pinyin pinyin  
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**  
	 * 获取shortPinyin  
	 * @return shortPinyin shortPinyin  
	 */
	public String getShortPinyin() {
		return shortPinyin;
	}

	/**  
	 * 设置shortPinyin  
	 * @param shortPinyin shortPinyin  
	 */
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
	}

	/**  
	 * 获取cityId  
	 * @return cityId cityId  
	 */
	public int getCityId() {
		return cityId;
	}

	/**  
	 * 设置cityId  
	 * @param cityId cityId  
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	/**  
	 * 获取provinceId  
	 * @return provinceId provinceId  
	 */
	public int getProvinceId() {
		return provinceId;
	}

	/**  
	 * 设置provinceId  
	 * @param provinceId provinceId  
	 */
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
}
