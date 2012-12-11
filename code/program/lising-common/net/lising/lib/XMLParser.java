package net.lising.lib;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * XML文件解析工具类
 * 
 * @author Jay.Lee
 * 
 * @createDate 2010-12-20 下午05:05:10
 */
public class XMLParser {

	/**
	 * 解析一个字符串为Document
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static Document strChangeXML(String str) throws IOException {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(str.getBytes()));
		} catch (DocumentException e) {
			//LogConsoleUtil.write(XMLParser.class, "strChangeXML()", e.getMessage());
		}
		return document;
	}

	/**
	 * 读取xml文件 ，返回遍历器
	 * 
	 * @param xmlPath
	 *            xml路径
	 * @return
	 * @throws DocumentException
	 * @author xieyong
	 */
	public static Iterator<?> parseXMLFile(String filePath)throws DocumentException {
		SAXReader reader = new SAXReader();
		File file = new File(filePath);
		Document document = reader.read(file);
		Element element = document.getRootElement();
		return element.elementIterator();
	}
}
