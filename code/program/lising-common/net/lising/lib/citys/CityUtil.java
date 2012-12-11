package net.lising.lib.citys;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.lising.lib.XMLParser;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 读写城市XML文件工具类
 */
public class CityUtil {
	private static Logger log = Logger.getLogger(CityUtil.class);
	
	private static String CITY_FILE_PATH = "xml/citys.xml";
	private static String PROVINCE_FILE_PATH = "xml/provinces.xml";

	public static List<Map<String, Object>> proList;
	public static List<Map<String, Object>> cityList;

	// 省市信息缓存
	static {
		try {
			//从编译路径寻找xml文件
			String path = CityUtil.class.getClassLoader().getResource("").getPath();
			cityList = parseCityXML(path);
			proList = parseProvinceXML(path);
			proList = fillProvince();
		} catch (DocumentException e) {
			//从jar包中寻找xml文件
			log.error("解析XML出错"+e.getMessage());
		}
	}

	/**
	 * 监听器调用，初始化省市信息
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<Map<String, Object>> initList() {
		return proList;
	}

	/**
	 * 获取省份信息
	 * 
	 * @param proId
	 * @return
	 */
	public static ProvinceXml getPro(String proId) {
		ProvinceXml pro = null;
		for (Map<String, Object> p : proList) {
			if (p.get("id").toString().equals(proId)) {
				pro = new ProvinceXml();
				pro.setId(proId);
				pro.setName(p.get("name") + "");
				break;
			}
		}
		return pro;
	}

	/**
	 * 获取城市信息
	 * 
	 * @param cityId
	 * @return
	 */
	public static CityXml getCity(long cityId) {
		CityXml city = null;
		for (Map<String, Object> c : cityList) {
			if (c.get("cityId").toString().equals(cityId + "")) {
				city = new CityXml();
				city.setCityId(cityId + "");
				city.setCityName(c.get("cityName") + "");
				city.setFather(c.get("father") + "");
				break;
			}
		}
		return city;
	}

	/**
	 * 根据省ID获取城市
	 * 
	 * @param proId
	 * @return
	 */
	public static List<Map<String, Object>> getCity(String proId) {
		List<Map<String, Object>> citylist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : cityList) {
			if (m.get("father").equals(proId))
				citylist.add(m);
		}
		return citylist;
	}

	private static List<Map<String, Object>> fillProvince() {
		List<Map<String, Object>> plist = proList;
		List<Map<String, Object>> citylist = null;
		for (Map<String, Object> p : plist) {
			citylist = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> c : cityList) {
				if (c.get("father").equals(p.get("id"))) {
					citylist.add(c);
				}
			}
			p.put("cityList", citylist);
		}
		return plist;
	}

	/**
	 * 从XML文件中读取所有城市-----城市切换
	 * 
	 * @param rootPath
	 *            工程根路径
	 * @throws DocumentException
	 */
	private static List<Map<String, Object>> parseCityXML(String rootPath) throws DocumentException {
		Iterator<?> iterator = XMLParser.parseXMLFile(rootPath + CITY_FILE_PATH);
		List<Map<String, Object>> listCitys = new ArrayList<Map<String, Object>>();
		for (Iterator<?> it = iterator; it.hasNext();) {
			Element el = (Element) it.next();
			Map<String, Object> city = new HashMap<String, Object>();
			city.put("cityName", el.elementText("cityName"));
			city.put("cityId", el.elementText("cityId"));
			city.put("father", el.elementText("father"));
			city.put("hot", el.elementText("hot"));
			listCitys.add(city);
		}
		return listCitys;
	}

	/**
	 * 从XML文件中读取所有省份-----城市切换
	 * 
	 * @param rootPath
	 *            工程根路径
	 * @throws DocumentException
	 */
	private static List<Map<String, Object>> parseProvinceXML(String rootPath)
			throws DocumentException {
		Iterator<?> iterator = XMLParser.parseXMLFile(rootPath + PROVINCE_FILE_PATH);
		List<Map<String, Object>> proList = new ArrayList<Map<String, Object>>();
		Map<String, Object> p = null;
		for (Iterator<?> it = iterator; it.hasNext();) {
			Element el = (Element) it.next();
			p = new HashMap<String, Object>();
			p.put("id", el.elementText("id"));
			p.put("name", el.elementText("name"));
			p.put("centerCityId", el.elementText("centerCityId"));
			p.put("centerCityName", el.elementText("centerCityName"));
			p.put("orderby", el.elementText("orderby"));
			p.put("pinyin", el.elementText("pinyin"));
			proList.add(p);
		}
		proList = sortList(proList);
		return proList;
	}

	/**
	 * 排序
	 * 
	 * @param list
	 * @return
	 */
	private static List<Map<String, Object>> sortList(
			List<Map<String, Object>> list) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if (Integer.parseInt(o1.get("orderby") + "") > Integer.parseInt(o2.get("orderby") + ""))
					return 1;
				else
					return -1;
			}
		});
		return list;
	}

	/**
	 * 生成城市XML方法
	 * 
	 * @param filePath
	 *            文件路径
	 * @param list
	 *            城市列表
	 */
	@SuppressWarnings("unused")
	private static void CreateCityDocument(String filePath, List<CityXml> list) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("citys");

		for (int i = 0; i < list.size(); i++) {
			CityXml city = list.get(i);
			Element e = root.addElement("city");
			e.addElement("cityId").addText(city.getCityId() + "");
			e.addElement("cityName").addText(city.getCityName());

			String pin = "errorpinyin";
			try {
				// pin = ToPinYin.toPinYin(city.getCityName());// 将城市转换为拼音
			} catch (Exception e2) {
				System.out.println("转换拼音出错：" + city.getCityName());
			}
			String firstWord = pin.substring(0, 1);
			String leftWords = pin.substring(1, pin.length());
			firstWord = firstWord.toUpperCase();
			String pinyin = firstWord + leftWords;// 将第一个拼音转换为大写
			e.addElement("farther").addText(city.getFather());
			e.addElement("pinyin").addText(pinyin);
			e.addElement("hot").addText("0");
		}

		OutputFormat format = null;
		XMLWriter xmlwriter = null;
		// 进行格式化
		format = OutputFormat.createPrettyPrint();
		// 设定编码
		format.setEncoding("UTF-8");
		try {
			xmlwriter = new XMLWriter(new FileOutputStream(filePath), format);
			xmlwriter.write(document);
			xmlwriter.flush();
		} catch (Exception e) {
			System.out.println("生成城市XML文件出错：" + e.getMessage());
		} finally {
			if (xmlwriter != null) {
				try {
					xmlwriter.close();
				} catch (IOException e) {
					System.out.println("关闭流出错" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 省份生成XML方法
	 * 
	 * @param listProvince
	 */
	@SuppressWarnings("unused")
	private static void createProvinceDocument(List<ProvinceXml> listProvince) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("Provinces");

		for (int i = 0; i < listProvince.size(); i++) {
			ProvinceXml pro = listProvince.get(i);
			Element province = root.addElement("province");
			province.addElement("id").addText(pro.getId());
			province.addElement("name").addText(pro.getName());
		}

		OutputFormat format = null;
		XMLWriter xmlwriter = null;
		// 进行格式化
		format = OutputFormat.createPrettyPrint();
		// 设定编码
		format.setEncoding("UTF-8");
		try {
			xmlwriter = new XMLWriter(new FileOutputStream("d:\\provinces.xml"), format);
			xmlwriter.write(document);
			xmlwriter.flush();
		} catch (Exception e) {
			System.out.println("生成省份XML文件出错：" + e.getMessage());
		} finally {
			if (xmlwriter != null) {
				try {
					xmlwriter.close();
				} catch (IOException e) {
					System.out.println("关闭流出错" + e.getMessage());
				}
			}
		}
	}
}