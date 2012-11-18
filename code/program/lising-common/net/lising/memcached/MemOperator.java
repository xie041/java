package net.lising.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import net.lising.urls.ProjectUtil;

import org.apache.log4j.Logger;
import org.spymemcached.AddrUtil;
import org.spymemcached.MemcachedClient;
import org.spymemcached.internal.OperationFuture;

/**
 * Memcached缓存操作类,集中管理
 * @author xie041
 */
public class MemOperator {
	
	private static MemcachedClient client;
	
	private static Logger log = Logger.getLogger(MemOperator.class);
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * public static String ONLINE_USERLIST = "OnLine_UserList";(im在线用户列表key)
	 * 
	 * --------------------------------------------------------------------------------------------
	 * 
	 * MemcachedServiceClient.getSession(ProjectUtil.MEM_MAP_KEY_CHATTING_RECORD);（聊天记录列表）
	 * --------------------------------------------------------------------------------------------
	 */
	public static final String IM_ONLINE_USER_LIST = "IM_ONLINE_USER_LIST";
	public static final String IM_CHATTING_RECORD = "IM_CHATTING_RECORD";
	public static final String CORESEEK_KEYWORDS = "CORESEEK_KEYWORDS";
	public static final String CORESEEK_KEYWORDS_DICTIONARY = "CORESEEK_KEYWORDS_DICTIONARY";
	private static final String MEM_PREFIX = "CTX_";//用户前缀
	private static final String JOB_CATAGORY = "JOB_CATAGORY";//招聘求职分类
	
	private MemOperator() { }
	static {
		try {
//			client = new MemcachedClient(new BinaryConnectionFactory(),AddrUtil.getAddresses(ProjectUtil.getMemcachedAddress()));
//			client = new MemcachedClient(new InetSocketAddress(ProjectUtil.getMemcachedAddress(), 11211));
			List<InetSocketAddress> list = AddrUtil.getAddresses(ProjectUtil.getMemcachedAddress());
			client = new MemcachedClient(list);
		} catch (IOException e) {
			log.error("创建memcached服务失败",e);
		}
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * 操作登陆用户数据
	 * --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * 从Memcached中获取登陆用户数据
	 * @param userName
	 * @return
	 */
	public static Object getLoginUser(String userName){
		return getMem(MEM_PREFIX+userName);
	}
	
	/**
	 * 用户登陆成功后，根据用户ID，将数据存入到缓存中
	 * @param key
	 * @param dataMap
	 */
	public static boolean setLoginUser(String userName,Map<String,Object> userDataMap){
		return setMem(MEM_PREFIX+userName, userDataMap);
	}
	
	/**
	 * 从memcached中移除登陆的用户信息
	 * @param userName
	 */
	public static boolean removeLoginUser(String userName){
		return rmMem(MEM_PREFIX+userName);
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * 操作全搜索项目，搜索的关键字
	 * --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * 存储全搜索项目中，搜索的时候存储关键字
	 * @param keywordsMap
	 * @return
	 */
	public static boolean setCoreseekKeywords(Map<String,Object> keywordsMap){
		return setMem(CORESEEK_KEYWORDS, keywordsMap);
	}
	
	/**
	 * 获取全搜索项目中，搜索的时候存储关键字
	 * @return
	 */
	public static Object getCoreseekSearchKeywords(){
		return getMem(CORESEEK_KEYWORDS);
	}
	
	/**
	 * 移除全搜索项目中，搜索的时候存储关键字
	 * @return
	 */
	public static boolean removeCoreseekSearchKeywords(){
		return rmMem(CORESEEK_KEYWORDS);
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * 操作coreseek词典
	 * --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * 获取缓存词典，8万条一组
	 * @return
	 */
	public static Object getCoreseekDictionary(String dicKeyItem){
		return getMem(dicKeyItem);
	}
	
	/**
	 * 存储缓存词典，8万条一组，原因是memcached的单次最大数据不能超过1M
	 * @return
	 */
	public static boolean setCoreseekDictionary(String dicKeyItem,Map<String,Object> dicMap){
		return setMem(dicKeyItem, dicMap);
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * IM操作缓存
	 * --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * IM获取在线用户列表
	 * @return
	 */
	public static Object getImOnlineList(){
		return getMem(IM_ONLINE_USER_LIST);
	}
	
	/**
	 * IM存入数据到缓存
	 * @return
	 */
	public static boolean setImOnlineList(Map<String,Object> imOnlineListMap){
		return setMem(IM_ONLINE_USER_LIST, imOnlineListMap);
	}
	
	/**
	 * IM删除用户在线记录缓存
	 * @return
	 */
	public static boolean removeImOnlineList(){
		return rmMem(IM_ONLINE_USER_LIST);
	}
	
	/**
	 * 获取缓存聊天记录
	 * @return
	 */
	public static Object getImChattingRecord(){
		return getMem(IM_CHATTING_RECORD);
	}
	
	/**
	 * 存入缓存聊天记录
	 * @return
	 */
	public static boolean setImChattingRecord(Map<String,Object> chattingRecordMap){
		return setMem(IM_CHATTING_RECORD, chattingRecordMap);
	}
	
	/**
	 * 删除缓存聊天记录
	 * @return
	 */
	public static boolean removeImChattingRecord(){
		return rmMem(IM_CHATTING_RECORD);
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * 招聘求职一级分类和二级分类
	 * --------------------------------------------------------------------------------------------
	 */
	public static boolean setJobCatagory(Map<String,Object> jobMap){
		return setMem(JOB_CATAGORY, jobMap);
	}
	
	/**
	 * 获取全搜索项目中，搜索的时候存储关键字
	 * @return
	 */
	public static Object getJobCatagory(){
		return getMem(JOB_CATAGORY);
	}
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * 测试
	 * --------------------------------------------------------------------------------------------
	 */
	
	/**
	 * 测试获取在线用户列表
	 * @return
	 */
	public static Object getTest(String key){
		return getMem(key);
	}
	
	/**
	 * 测试存入数据到缓存
	 * @return
	 */
	public static boolean setTest(String key,Map<String,Object> testMap){
		return setMem(key, testMap);
	}
	
	/**
	 * 测试删除用户在线记录缓存
	 * @return
	 */
	public static boolean removeTest(String testKey){
		return rmMem(testKey);
	}
	
	/**
	 * 清除缓存
	 */
	public static void flush(){
		try {
			client.flush();
		}catch (Exception e) {
			log.error("清除缓存失败", e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * --------------------------------------------------------------------------------------------
	 * 内部方法
	 * --------------------------------------------------------------------------------------------
	 */

	/**
	 * 通过KEY获取Memcached缓存中的数据
	 * @param key
	 * @return
	 */
	private static Object getMem(String key){
		try {
			log.debug("获取缓存的key："+key);
			return client.get(key);
		}catch (Exception e) {
			log.error("获取缓存中的数据失败：key="+key+"         e:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 存入缓存
	 * @param key
	 * @param dataMap
	 */
	private static boolean setMem(String key,Map<String,Object> dataMap){
		boolean bln = false;
		try {
			OperationFuture<Boolean> b = client.set(key,0,dataMap);
//			System.out.println("setMem:key"+b.getKey());
//			System.out.println("setMem:isDone"+b.isDone());
//			System.out.println("setMem:isSuccess"+b.getStatus().isSuccess());
			bln = b.getStatus().isSuccess();
		}catch (Exception e) {
			log.error("存储到缓存中的数据失败：key="+key+" data:"+dataMap+"         e:"+e.getMessage());
		}
		return bln;
	}
	
	/**
	 * 替换
	 * @param key
	 * @param dataMap
	 */
	@SuppressWarnings("unused")
	private static boolean replaceMem(String key,Map<String,Object> dataMap){
		boolean bln = false;
		try {
			OperationFuture<Boolean> b = client.replace(key,0,dataMap);
			bln = b.getStatus().isSuccess();
		}catch (Exception e) {
			log.error("替换缓存中的数据失败：key="+key+" data:"+dataMap+"         e:"+e.getMessage());
		}
		return bln;
	}
	
	/**
	 * 通过key删除Memcached缓存中的对象
	 * @param key
	 */
	private static boolean rmMem(String key){
		boolean bln = false;
		try {
			OperationFuture<Boolean> b = client.delete(key);
			bln = b.getStatus().isSuccess();
		}catch (Exception e) {
			log.error("清除缓存中的对象失败:key="+key+"         e:"+e.getMessage());
		}
		return bln;
	}

}