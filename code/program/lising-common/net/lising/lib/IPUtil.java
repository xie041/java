package net.lising.lib;

import org.junit.Test;

/**
 * 
 * 项目名称：lising-tourie  
 * 类名称：IPUtil  
 * 类描述： IP地址工具
 * 创建人：xieyong   Email:xie041@126.com  QQ:190221242 
 * 创建时间：2011-7-6 上午11:29:43  
 * 修改人：xie041  
 * 修改时间：2011-7-6 上午11:29:43  
 * 修改备注：  
 * @version   
 */ 
public class IPUtil {

	//将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
    public static long ipToLong(String strIP){
         try {
			long[] ip=new long[4];
			 //先找到IP地址字符串中.的位置
			 int position1=strIP.indexOf(".");
			 int position2=strIP.indexOf(".",position1+1);
			 int position3=strIP.indexOf(".",position2+1);
			 //将每个.之间的字符串转换成整型
			 ip[0]=Long.parseLong(strIP.substring(0,position1));
			 ip[1]=Long.parseLong(strIP.substring(position1+1,position2));
			 ip[2]=Long.parseLong(strIP.substring(position2+1,position3));
			 ip[3]=Long.parseLong(strIP.substring(position3+1));
			 return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3];
		} catch (NumberFormatException e) {
			return 0;
		} 
    }

    //将10进制整数形式转换成127.0.0.1形式的IP地址
    public static String longToIP(long longIP){
         try {
			StringBuffer sb=new StringBuffer("");
			 //直接右移24位
			 sb.append(String.valueOf(longIP>>>24));
			 sb.append(".");          
			 //将高8位置0，然后右移16位
			 sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16)); 
			 sb.append(".");
			 sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));
			 sb.append(".");
			 sb.append(String.valueOf(longIP&0x000000FF));
			 return sb.toString();
		} catch (Exception e) {
			return "";
		} 
    }
    
    /**
     * @param args
     */
    @Test
    public void a(){
        String ipStr = "74.125.66.83";
        long ipLong = ipToLong(ipStr);
        System.out.println("74.125.66.83 >> " + ipLong);
        System.out.println(ipLong + " >> "  + longToIP(ipLong));
        //IP地址转化成二进制形式输出
        System.out.println("192.168.0.1 二进制>> "   + Long.toBinaryString(ipLong));
    }
}

