package net.lising.sms;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsClient {

	//webservice服务器定义
	private String serviceURL = "http://124.172.250.160/WebService.asmx";
	private String sn = "tangtu";// 序列号
	private String pwd = "888888";// 密码

	/**
	 * 发送即时短信
	 * @param mobile 手机号，多个手机号以英文“,”分割
	 * @param content 发送内容(只有一条内容)
	 * @return 立即发送短信的结果信息，0表示发送成功
	 */
	public  String mt(String mobile, String content) {
		String soapAction = "http://tempuri.org/mt";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mt xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "</mt>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<mtResult>(.*)</mtResult>";
		
		return connectService(soapAction, xml, resultStr);
	}

	/**
	 * 接收短信
	 * @return 接收短信的结果信息
	 * 无回复短信的情况：0
	 * 有回复短信的情况（多条短信间以换行分割）:90,13439599223,好好学习,2012-3-13 10:51:13
	 *	                        90,13439599223,天天向上,2012-3-13 10:51:42
	 */
	public String recsms() {
		String soapAction = "http://tempuri.org/recsms";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<recsms xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "</recsms>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<recsmsResult>(.*)</recsmsResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 发送定时短信
	 * @param mobile 手机号，多个手机号以英文“,”分割
	 * @param content 发送内容(只有一条内容)
	 * @param stime 发送时间,格式如2010-11-11 11:11:00
	 * @return 发送定时短信的结果信息，0表示发送成功
	 */
	public String sendSMS (String mobile, String content, String stime) {
		String soapAction = "http://tempuri.org/SendSMS";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<SendSMS xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<stime>" + stime + "</stime>";
		xml += "</SendSMS>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<SendSMSResult>(.*)</SendSMSResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 发送定时个性短信：定时发送不同内容给不同手机号(内容条数和手机个数相同，第一个手机号收到第一条内容，第n个手机号收到第n条内容)
	 * @param mobile 手机号，多个手机号以英文“,”分割
	 * @param content 发送内容，多个内容以英文“*”分割
	 * @param stime 发送时间,格式如2010-11-11 11:11:00
	 * @return 发送定时个性短信的结果信息，0表示发送成功
	 */
	public String dsgxmt(String mobile, String content, String stime) {
		String soapAction = "http://tempuri.org/dsgxmt";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<dsgxmt xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<stime>" + stime + "</stime>";
		xml += "</dsgxmt>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<dsgxmtResult>(.*)</dsgxmtResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 发送个性短信:发送不同内容给不同手机号(内容条数和手机个数相同，第一个手机号收到第一条内容，第n个手机号收到第n条内容)
	 * @param mobile 手机号，多个手机号以英文“,”分割
	 * @param content 发送内容，多个内容以英文“*”分割
	 * @return 发送个性短信的结果信息，0表示发送成功
	 */
	public String gxmt(String mobile, String content) {
		String soapAction = "http://tempuri.org/gxmt";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<gxmt xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "</gxmt>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<gxmtResult>(.*)</gxmtResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 登录
	 * @return 登录的结果信息，0表示登录成功
	 */
	public String login() {
		String soapAction = "http://tempuri.org/Login";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<Login xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "</Login>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<LoginResult>(.*)</LoginResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 注册
	 * @return 注册的结果信息，0表示注册成功
	 */
	public String register() {
		String soapAction = "http://tempuri.org/Register ";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<Register  xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "</Register >";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<RegisterResult>(.*)</RegisterResult>";
		
		return connectService(soapAction, xml, resultStr);
	}

	/**
	 * 修改密码
	 * @param nPwd 新密码
	 * @return 修改密码的结果信息，0表示修改成功
	 */
	public String udpPwd(String nPwd) {
		String soapAction = "http://tempuri.org/UDPPwd";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<UDPPwd xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "<NPwd>" + nPwd + "</NPwd>";
		xml += "</UDPPwd>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<UDPPwdResult>(.*)</UDPPwdResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 短信余额
	 * @return 查询短信余额的结果信息：短信条数
	 */
	public String balance() {
		String soapAction = "http://tempuri.org/balance";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<balance xmlns=\"http://tempuri.org/\">";
		xml += "<Sn>" + sn + "</Sn>";
		xml += "<Pwd>" + pwd + "</Pwd>";
		xml += "</balance>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";
		String resultStr = "<balanceResult>(.*)</balanceResult>";
		
		return connectService(soapAction, xml, resultStr);
	}
	
	/**
	 * 连接webservice服务端
	 * @param soapAction
	 * @param xml SOAP请求xml
	 * @param resultStr 结果字符串，如：<balanceResult>(.*)</balanceResult>
	 * @return 请求结果字符串（经过处理的字符串：截取resultStr标签内的字符；如<balanceResult>98</balanceResult>，结果为98）
	 *         从服务端读取到的数据格式为：<?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body><recsmsResponse xmlns="http://tempuri.org/"><recsmsResult>0</recsmsResult></recsmsResponse></soap:Body></soap:Envelope>
	 */
	public String connectService(String soapAction, String xml, String resultStr) {
		try {
			URL url = new URL(serviceURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			OutputStream out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			InputStreamReader isr = new InputStreamReader(httpconn
					.getInputStream(), "utf-8");
			BufferedReader in = new BufferedReader(isr);

			String result = "";
			String inputLine = "";
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile(resultStr);
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			in.close();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
}
