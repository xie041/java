package algorithm;

public class Digest {
	
	public static final int KEY = 1;
	
	public static void main(String[] args) {
		
		String str = "123456";
        StringBuffer str2 = new StringBuffer();  //存储加密后的字符串
        StringBuffer str3 = new StringBuffer();  //存储解密后的字符串
        //加密过程
        for(int i=0;i<str.length();i++)
        {
            char c = (char)(str.charAt(i) ^ KEY);
            str2.append(c);
        }
        //解密过程
        for(int i=0;i<str2.length();i++)
        {
            char c = (char)(str2.charAt(i) ^ KEY);
            str3.append(c);
        }
        System.out.println("原始 的字符串为:" + str);
        System.out.println("加密后 的字符串为:" + str2);
        System.out.println("解密后 的字符串为:" + str3);
	}

}

