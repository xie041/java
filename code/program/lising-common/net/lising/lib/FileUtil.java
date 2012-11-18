package net.lising.lib;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * 文件操作工具类
 * 
 * @author Jay.Lee
 * 
 * @createDate 2010-12-20 下午05:03:18
 */
public final class FileUtil {

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            文件绝对路径
	 */
	public static void delFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileExist(String path) {
		File file = new File(path);
		return file.exists() ? true : false;
	}

	/**
	 * 检查文件夹是否存在,不存在则创建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static void createFolder(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
	}

	/**
	 * 根据文件创建时间检查文件是否有效
	 * 
	 * @param path
	 *            文件路径
	 * @param limit
	 *            有效期(毫秒)
	 * @return
	 */
	public static boolean fileIsValid(String path, long limit) {
		long fileModifyTime = getLastModifiedTime(path);
		long now = new Date().getTime();
		return (now - fileModifyTime) > limit ? true : false;
	}

	/**
	 * 获取文件最后修改时间
	 * 
	 * @param path
	 * @return 文件修改时间毫秒数
	 */
	private static long getLastModifiedTime(String path) {
		File file = new File(path);
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		long time = file.lastModified();
		return time;
	}

	/**
	 * 检查文件是否当天创建的
	 * 
	 * @param f
	 * @return
	 */
	public static boolean checkFileIsToday(File f) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		int year = ca.get(Calendar.YEAR), month = ca.get(Calendar.MONTH), day = ca
				.get(Calendar.DAY_OF_MONTH);
		ca.set(year, month, day, 0, 0, 1);
		long today_0 = ca.getTimeInMillis();
		ca.set(year, month, day, 23, 59, 59);
		long today_1 = ca.getTimeInMillis();
		long file_time = f.lastModified();
		return file_time > today_0 && file_time < today_1;
	}

	/**
	 * 删除文件（如果是文件夹则删除该目录下的所有字文件夹和文件,递归）
	 * 
	 * @param path
	 */
	public static void delDirectory(String path) {
		if (!Common.valid(path))
			return;
		File file = new File(path);
		if (file.isDirectory()) {
			File[] allFile = file.listFiles();
			if (allFile.length == 0) {
				file.deleteOnExit();
				return;
			}
			for (File f : allFile) {
				if (f.isDirectory()) {
					delDirectory(f.getAbsolutePath());
					continue;
				}
				f.delete();
			}
			// 当前文件夹中的文件全部删除完之后删除此文件夹
			file.delete();
		} else if (file.isFile()) {
			file.delete();
		}
	}

}
