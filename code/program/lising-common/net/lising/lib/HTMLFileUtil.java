package net.lising.lib;

import java.io.File;
import java.io.IOException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLFileUtil {
	
	public static HTMLFileUtil getInstance() {
		return new HTMLFileUtil();
	}

	/**
	 * 请求URL地址获取数据生成HTML文件
	 * 
	 * @param url
	 *            要请求的地址
	 * @param path
	 *            要生成的文件地址
	 * @return 文件是否创建成功
	 */
	public boolean createHtml(String url, String path, String sessionId) {
		String content = getData(url, sessionId);
		if (content !=null ) {
			return createFile(content, path);
		}
		return false;
	}

	/**
	 * 根据内容创建文件
	 * 
	 * @param content
	 *            要写入的文件内容
	 * @param filePath
	 *            文件路径
	 * @return 文件是否创建成功
	 */
	public boolean createFile(String content, String filePath) {
		boolean flag = false;
		FileWriterWithEncoding fw = null;
		try {
			filePath = filePath.replace("\\", "/");
			//linux对文件夹的处理方式不一样，比如/a/b/c，会认为c是一个文件，如果是/a/b/c/，则认为c是目录
			String directory = filePath.substring(0, filePath.lastIndexOf("/")+1);
			FileUtil.createFolder(directory);
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			fw = new FileWriterWithEncoding(file, "UTF-8");
			fw.write(content);
			fw.flush();
			fw.close();
			flag = true;
		} catch (Exception e) {
			//log.error("根据内容创建文件 ->createFile() 失败："+e.getMessage());
			e.printStackTrace();
		} finally {
			if (fw != null)
				fw = null;
		}
		return flag;
	}

	/**
	 * 请求URL地址获取数据
	 * 
	 * @param url
	 *            要请求的地址
	 * @param sessionId
	 *            当前会话ID,维持登录状态
	 * @return
	 */
	public String getData(String url, String sessionId) {
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		Header header = new Header("Cookie", "JSESSIONID=" + sessionId);
		getMethod.setRequestHeader(header);
		try {
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = getMethod.getResponseBody();
				String content = new String(responseBody, "UTF-8");
				return content;
			}
		} catch (Exception e) {
			//
		} finally {
			getMethod.releaseConnection();
		}
		return "";
	}
	
	/**
	 * 获取css文件内容
	 * @param urls
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getCSSData(String[] urls) throws HttpException, IOException {
		StringBuffer bf = new StringBuffer();
		HttpClient client = new HttpClient();
		for(int i=0;i<urls.length;i++){
			GetMethod get = new GetMethod(urls[i]);
			int statusCode = client.executeMethod(get);
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = get.getResponseBody();
				String content = new String(responseBody, "UTF-8");
				bf.append(content);
			}
		}
		return bf.toString();
	}
	
	public String filterInputValue(String html) throws HttpException, IOException {
		Document doc = Jsoup.parse(html);
		System.out.println(doc.html());
		Elements inputs = doc.select("input");
		for(Element input : inputs){
			System.out.println(input);
		}
		return "";
	}
}
