package net.lising.lib.encrypt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

/**
 * <pre>
 * 项目名称：nutz-demo2  
 * 类名称：SuperEncript  
 * 类描述：  超级机密工具类
 * 创建人：xieyong   Email:xie041@126.com  QQ:190221242 
 * 创建时间：2011-9-20 下午06:00:06  
 * 修改人：xie041  
 * 修改时间：2011-9-20 下午06:00:06  
 * 修改备注：  
 * @version  
 * </pre> 
 */ 
public class SuperEncript {
	
	/**
	 * 加密密钥
	 */
	public static final String SECRET_KEY = "www.tourie.com";
	
	/**
	 * 加密
	 * encoded in BASE64
	 * @param o
	 * @return
	 */
	public static String encryptEveryThing(Object o){
		BasicTextEncryptor te = new BasicTextEncryptor();
		te.setPassword(SECRET_KEY);
		return te.encrypt(String.valueOf(o));
	}
	
	/**
	 * 解密
	 * @param ""
	 * @return
	 */
	public static String decryptEveryThing(String s){
		s = replaceBackSpace(s);
		BasicTextEncryptor te = new BasicTextEncryptor();
		te.setPassword(SECRET_KEY);
		try {
			return te.decrypt(s);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 替换request传值引起的，+被转化为空格
	 * @param sourceStr
	 * @return
	 */
	private static String replaceBackSpace(String sourceStr){
		String replaceStr = " " ;
		if(sourceStr.indexOf(replaceStr)<0){//找到有空格的位置
			return sourceStr;
		}
		int currentIdex = 0;
		String preString = "";//空格左边的部分
		String nextString = "";//空格右边的部分
		currentIdex = sourceStr.indexOf(replaceStr);
		preString = sourceStr.substring(0,currentIdex) + "+";//截取前面的部分，添加一个加号,因为request传值，会让+转化成空格
		currentIdex += 1;//往后移动一位
		nextString = sourceStr.substring(currentIdex, sourceStr.length());
		return preString + replaceBackSpace(nextString);
	}
	
	/**
	 * demo.do?id=123
	 * demo.do?id=123&pwd=123
	 * 获取加密参数
	 * @param request
	 * @return
	 */
	public static Map<String,String> getEncriptParams(HttpServletRequest request){
		return execute(request.getQueryString());
	}
	/**
	 * demo.do?id=123
	 * demo.do?id=123&pwd=123
	 * 获取加密参数
	 * @param request
	 * @return
	 */
	public static Map<String,String> getEncriptParams(String queryString){
		return execute(queryString);
	}
	
	/**
	 * 执行分解的方法
	 * @param query
	 * @return
	 */
	private static Map<String, String> execute(String query){
		String temp = query;
		//空串不处理
		if(query == null || query.equals(""))return new HashMap<String, String>();
		//尝试解密参数
		query = decryptEveryThing(query);
		//解密失败返回，尝试用为加密参数获取方法,不做为加密与加密参数混合传递的处理
		if(query.equals("")){
			query = temp;
		}
//		if(query.indexOf("?")>-1){
//			query = query.substring(query.indexOf("?")+1, query.length());
//		}
//		System.out.println(query);
		Map<String, String> map = new HashMap<String, String>();
		//单参数
		//可能存在demo.do?id=123类型传参数
		if(query.indexOf("&")<0){
			if(query.indexOf("=")>0){
				//demo.do?id=123
				//说明有参数
				String[] each = query.split("=");
				String key = each[0];
				String value = each[1];
//				System.out.println("key = " + key + "   value=" + value  );
				map.put(key, value);
				return map;
			}
		}
		//很多参数的时候
		//demo.do?id=123&pwd=123&c=bkkkk&d=mmmmmm
		String[] params = query.split("&");
		for(String one_param : params){
			String key = null ;
			String value = null;
			try {
				//必须符合a=b结构
				if(one_param.indexOf("=")<0) continue;
				String[] each = one_param.split("=");
				//=左右必须有参数
				if(each.length != 2)continue;
				key = each[0];
				value = each[1];
				map.put(key, value);
			} catch (Exception e) {
				//echo
			}
		}
		return map;
	}
	
	@Test
	public void b(){
		StandardStringDigester digester = new StandardStringDigester();
		digester.setAlgorithm("SHA-1");   // optionally set the algorithm
		digester.setIterations(50000);  // increase security by performing 50000 hashing iterations
		String digest = digester.digest("aa");
		System.out.println(digest);
		System.out.println(encryptEveryThing("aa"));
	}
	
	@Test
	public void a(){
//		int a = -954168069;
//		System.out.println(encryptEveryThing(a));
//		System.out.println(decryptEveryThing("jZziwbXjAKeFQTXxpI638RRBE5P1gIc "));
//		System.out.println(decryptEveryThing("gSVhTEJoQoWp6ey nFQ8qmPlHqKSgljO"));
//		System.out.println(decryptEveryThing("/ Se4Ny1NpnL6W29U8lMJUF1PMCWkZ9Q"));
		Map<String,String> map = getEncriptParams(encryptEveryThing("a.do?a.id=12&fuck=you?&&&&f=ok&id=123&===&===&&&&&&&&b=c"));
		System.out.println(map.get("a.id") + "     =====================   ");
		System.out.println(map.get("b"));
		System.out.println(map.get("fuck"));
		System.out.println(map.get("f"));
	}
}