package net.lising.rmt.spi;

import java.util.List;

import net.lising.rmt.bean.ChinaCitys;

public interface RmtBaseDataService {
	
	/**
	 * 查询省份下面的城市
	 * @param province
	 * @return
	 */
	public List<ChinaCitys> listCitysByProvince(int province);
	
	/**
	 * 用拼音或者汉字模糊匹配城市
	 * @param name
	 * @return
	 */
	public List<ChinaCitys> listCitysByNameMatching(String name);
	
	/**
	 * 根据城市ID返回实体
	 * @param cid
	 * @return
	 */
	public ChinaCitys findCityByCityId(int cid);
	
//	List<TempProvince> demolist();

}