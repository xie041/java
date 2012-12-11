package net.lising.lib.num;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字工具类
 * 
 * @author chengrong
 *
 */
public final class NumberUtil {

	// 默认被转化后的对象整数
	private static final int DEFAULT_INT_VALUE = Integer.MIN_VALUE;
	// 默认被转化后的对象长整数
	private static final long DEFAULT_LONG_VALUE = Long.MIN_VALUE;
	// 默认被转化后的对象双精度数
	private static final double DEFAULT_DOUBLE_VALUE = Double.MIN_VALUE;
	// 默认格式化后的小数格式
	private static final String DEFAULT_DOUBLE_FORMAT_PATTERN = "#####.##";
	// 默认格式化后的整数格式
	private static final String DEFAULT_LONG_FORMAT_PATTERN = "##,###";

	private NumberUtil() {
	}

	/**
	 * 将一个对象转化为整数。如果该对象不能成功转化，则返回Integer.MIN_VALUE
	 * 
	 * @param obj 需转化的对象
	 * @return 转化后的整数
	 */
	public static int toInt(Object obj) {

		return toInt(obj, DEFAULT_INT_VALUE);
	}

	/**
	 * 将一个对象转化为长整数
	 * 
	 * @param obj 需转化的对象
	 * @param defaultValue 如果转化不成功，默认的长整数
	 * @return 转化后的长整数
	 */
	public static long toLong(Object obj, long defaultValue) {

		if (obj == null || obj.equals("")) {
			return defaultValue;
		}

		if (obj instanceof Long) {
			obj = ((Long) obj).longValue() + "";
		} else {
			obj = obj.toString();
		}

		try {
			return Long.parseLong((String) obj);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	/**
	 * 将一个对象转化为长整数。如果该对象不能成功转化，则返回Long.MIN_VALUE
	 * 
	 * @param obj 需转化的对象
	 * @return 转化后的长整数
	 */
	public static long toLong(Object obj) {

		return toLong(obj, DEFAULT_LONG_VALUE);
	}

	/**
	 * 将一个对象转化为整数
	 * 
	 * @param obj 需转化的对象
	 * @param defaultValue 如果转化不成功，默认的整数
	 * @return 转化后的整数
	 */
	public static int toInt(Object obj, int defaultValue) {

		if (obj == null || obj.equals("")) {
			return defaultValue;
		}

		if (obj instanceof Integer) {
			obj = ((Integer) obj).intValue() + "";
		} else {
			obj = obj.toString();
		}

		try {
			return Integer.parseInt((String) obj);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	/**
	 * 将一个对象转化为双精度数。如果该对象不能成功转化，则返回Double.MIN_VALUE
	 * 
	 * @param obj 需转化的对象
	 * @return 转化后的双精度数
	 */
	public static double toDouble(Object obj) {

		return toDouble(obj, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * 将一个对象转化为双精度数
	 * 
	 * @param obj 需转化的对象
	 * @param defaultValue 如果转化不成功，默认的双精度数
	 * @return 转化后的双精度数
	 */
	public static double toDouble(Object obj, double defaultValue) {

		if (obj == null || obj.equals("")) {
			return defaultValue;
		}

		if (obj instanceof Double) {
			obj = ((Double) obj).intValue() + "";
		} else {
			obj = obj.toString();
		}

		try {
			return Double.parseDouble((String) obj);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 格式化双精度数。格式化后的样式为：#####.##
	 * 
	 * @param value 需要格式化的双精度数
	 * @return 格式化后的双精度数
	 */
	public static String format(double value) {
		return format(value, DEFAULT_DOUBLE_FORMAT_PATTERN);
	}

	/**
	 * 格式化双精度数
	 * 
	 * @param value 需要格式化的双精度数
	 * @param pattern 格式化样式
	 * @return 格式化后的双精度数
	 */
	public static String format(double value, String pattern) {
		NumberFormat format = new DecimalFormat(pattern);
		return format.format(value);
	}
	
	/**
	 * 根据数值，返回固定长度字符串
	 * @param length
	 * @param value
	 * @return
	 */
	public static String getFixedLength(int length,int value){
		String format = "%0"+length+"d";
		return String.format(format, value + 1);
	}
	
	/**
	 * 格式化长整数。格式化后的样式为：##,###
	 * 
	 * @param value 需要格式化的长整数
	 * @return 格式化后的长整数
	 */
	public static String format(long value) {
		return format(value, DEFAULT_LONG_FORMAT_PATTERN);
	}

	/**
	 * 格式化长整数
	 * 
	 * @param value 需要格式化的长整数
	 * @param pattern 格式化样式
	 * @return 格式化后的长整数
	 */
	public static String format(long value, String pattern) {
		NumberFormat format = new DecimalFormat(pattern);
		return format.format(value);
	}

	/**
	 * 格式化对象
	 * 
	 * @param value 需要格式化的对象
	 * @param pattern 格式化样式
	 * @return 格式化后的对象
	 */
	public static String format(Object value, String pattern) {
		NumberFormat format = new DecimalFormat(pattern);
		return format.format(value);
	}
	
	public static void main(String[] args) {
		System.out.println(RMB.number2UpperCase(13005));
	}

	/**
	 * 判断是否是数字
	 * @param value 要判断的字符串
	 * @return 是数字返回true，反之返回false
	 */
	public static boolean isNum(String value){
		//为空
		if(value == null){
			return false;
		}
		value = value.trim();
		if("".equals(value)){
			return false;
		}
		//小数点
		//不能出现2个及两个以上的小数点
		if(value.indexOf(".") != value.lastIndexOf(".")){
			return false;
		}
		//小数点开头
		if(value.indexOf("-")==0){
			value = value.substring(1);
		}
		if(value.indexOf(".") == 0){
			value = value.substring(1);
		}
		//小数点替换成数字
		if(value.indexOf(".") != -1){
			value.replace('.', '1');
		}
		//小数点结尾
		//小数点大于一个
			if(value.indexOf("-") == 0&&value.indexOf(".") == 1){
				value = value.substring(2);
			}
		//没有小数点的
		for (int i = 0; i < value.length(); i++) {
			int num = 0;
			boolean bool=true;
			try {
				num = Integer.parseInt(value.charAt(i) + "");	
			} catch (Exception e) {	
				return false;	
			}
			for (int j = 0; j < 10; j++) {
				if(num == j){
					bool = false;
					break;
				}
			}
            //如果boolean值为真
			if(bool){
				return false;
			}
		}
		return true;
	}
}
