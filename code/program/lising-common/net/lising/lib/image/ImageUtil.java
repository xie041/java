package net.lising.lib.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.lising.lib.FileUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图像压缩工具类
 * 
 * @author Jay.Lee
 * 
 * @createDate 2010-12-20 下午05:03:00
 */
public class ImageUtil {

	private static int WIDTH;
	private static int HEIGHT;

	public static String LARGE = "large";
	public static String MIDDLE = "middle";
	public static String SMALL = "small";
	public static String TT_MIDDLE = "tt_middle";
	public static String TT_SMALL = "tt_small";
	
	/**
	 * 小头像的宽   int 72
	 */
	public static final int SMALL_HEAD_IMAGE_WIDTH = 72;
	/**
	 * 小头像的高   int 72
	 */
	public static final int SMALL_HEAD_IMAGE_HEIGHT = 72;
	
	/**
	 * 名片头像的宽 int 120
	 */
	public static final int CARD_HEAD_IMAGE_WIDTH = 120;
	
	/**
	 * 名片头像的高 int 116
	 */
	public static final int CARD_HEAD_IMAGE_HEIGHT = 116;

	/**
	 * 空间头像压缩大小
	 */
	private static Map<String, String> SPACE_HEAD = new HashMap<String, String>();

	/**
	 * 资讯关联图压缩大小
	 */
	private static Map<String, String> NEWSIMG = new HashMap<String, String>();

	/**
	 * 空间相册图片压缩大小
	 */
	private static Map<String, String> SPACE_ALBUM = new HashMap<String, String>();
	
	/**
	 * 公司营业执照压缩大小
	 */
	private static Map<String, String> COMPANY_LICENSE = new HashMap<String, String>();

	static {
		SPACE_HEAD.put(SMALL, "72,72");

		NEWSIMG.put(MIDDLE, "130,130");
		NEWSIMG.put(SMALL, "70,85");

		NEWSIMG.put(TT_MIDDLE, "145,145");
		NEWSIMG.put(TT_SMALL, "50,50");

		SPACE_ALBUM.put(LARGE, "489,366");
		SPACE_ALBUM.put(MIDDLE, "110,105");
		SPACE_ALBUM.put(SMALL, "52,52");
		
		COMPANY_LICENSE.put(MIDDLE, "222,160");
	}
	
	/**
	 * 压缩公司营业执照
	 * @param imgPath 图片路径
	 */
	public static void pressCompanyLicense(String imgPath,String targetPath) {
		WIDTH = Integer.parseInt(COMPANY_LICENSE.get(MIDDLE).split(",")[0]);
		HEIGHT = Integer.parseInt(COMPANY_LICENSE.get(MIDDLE).split(",")[1]);
		reduceImg(imgPath, targetPath, WIDTH, HEIGHT);
	}
	
	/**
	 * 根据图片路径/高度/宽度压缩图片
	 * @param imgPath
	 * @param width
	 * @param height
	 */
	public static void pressImage(String imgPath,int width,int height) {
		reduceImg(imgPath, imgPath, width, height);
	}
	
	/**
	 * 自定义长宽压缩图片
	 * @param imgPath 原图片路径
	 * @param targetPath 自定义图片路径
	 * @param width 压缩后图片的宽
	 * @param height 压缩后图片的高
	 */
	public static void selfDefinedCompressImage(String imgPath,String targetPath,int width,int height) {
		reduceImg(imgPath, targetPath, width, height);
	}
	
	/**
	 * 压缩新闻资讯图片
	 * 
	 * @param imgpath
	 *            图片路径
	 * @param delSource
	 *            压缩后是否删除原图
	 */
	public static void pressNewsImg(String imgpath, boolean delSource) {
		// 网页小图
		WIDTH = Integer.parseInt(NEWSIMG.get(SMALL).split(",")[0]);
		HEIGHT = Integer.parseInt(NEWSIMG.get(SMALL).split(",")[1]);
		String targetImg = initPath(imgpath, SMALL, 0);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);

		// 网页中图
		WIDTH = Integer.parseInt(NEWSIMG.get(MIDDLE).split(",")[0]);
		HEIGHT = Integer.parseInt(NEWSIMG.get(MIDDLE).split(",")[1]);
		targetImg = initPath(imgpath, MIDDLE, 0);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);

		// TT大图
		WIDTH = Integer.parseInt(NEWSIMG.get(TT_MIDDLE).split(",")[0]);
		HEIGHT = Integer.parseInt(NEWSIMG.get(TT_MIDDLE).split(",")[1]);
		targetImg = initPath(imgpath, TT_MIDDLE, 0);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);

		// TT小图
		WIDTH = Integer.parseInt(NEWSIMG.get(TT_SMALL).split(",")[0]);
		HEIGHT = Integer.parseInt(NEWSIMG.get(TT_SMALL).split(",")[1]);
		targetImg = initPath(imgpath, TT_SMALL, 0);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);
		if (delSource)
			FileUtil.delFile(imgpath);
	}

	/**
	 * 压缩新闻内容中图片
	 * 
	 * @param imgpath
	 */
	public static void pressNewsImg(String imgpath) {
		reduceImg(imgpath, imgpath, 0, 0);
	}

	/**
	 * 公司展示：关于我们的图片
	 * 
	 * @param imgpath
	 *            图片路径
	 */
	public static void pressCompanyAboutImg(String imgpath) {
		WIDTH = Integer.parseInt(NEWSIMG.get(SMALL).split(",")[0]);
		HEIGHT = Integer.parseInt(NEWSIMG.get(SMALL).split(",")[1]);
		String targetImg = initPath(imgpath, "s", 0);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);
	}

	/**
	 * 压缩空间头像
	 * 
	 * @param imgpath
	 *            图片路径
	 */
	public static void pressSpaceHeadImg(String imgpath) {
		WIDTH = Integer.parseInt(SPACE_HEAD.get(SMALL).split(",")[0]);
		HEIGHT = Integer.parseInt(SPACE_HEAD.get(SMALL).split(",")[1]);
		String targetImg = initPath(imgpath, "", 0);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);
	}

	/**
	 * 压缩空间相册图片
	 * 
	 * @param imgpath
	 *            图片路径
	 */
	public static void pressSpaceAlbumPhoto(String imgpath) {
		String targetImg = initPath(imgpath, LARGE, 0);
		reduceImg(imgpath, targetImg, 0, 0);

		WIDTH = Integer.parseInt(SPACE_ALBUM.get(MIDDLE).split(",")[0]);
		HEIGHT = Integer.parseInt(SPACE_ALBUM.get(MIDDLE).split(",")[1]);
		targetImg = initPath(imgpath, MIDDLE, 1);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);

		WIDTH = Integer.parseInt(SPACE_ALBUM.get(SMALL).split(",")[0]);
		HEIGHT = Integer.parseInt(SPACE_ALBUM.get(SMALL).split(",")[1]);
		targetImg = initPath(imgpath, SMALL, 2);
		reduceImg(imgpath, targetImg, WIDTH, HEIGHT);
	}

	/**
	 * 设置文件名称
	 * 
	 * @param path
	 *            源文件路径
	 * @param addname
	 *            要加入的文件名称字符串
	 * @param type
	 *            1相册中图;2相册小图;0其它
	 * @return
	 */
	private static String initPath(String path, String addname, int type) {
		String imgPath = path.replace("/", "\\");
		if (System.getProperty("os.name").toLowerCase().contains("linux"))
			imgPath = path.replace("\\", "/");
		// 不带扩展名的文件名称
		String nameWithoutFix = imgPath.substring(imgPath
				.lastIndexOf(File.separator) + 1, imgPath.lastIndexOf("."));
		// 文件扩展名(类型)
		String suffix = imgPath.substring(imgPath.lastIndexOf("."), imgPath
				.length());
		// 文件所在文件夹路径
		String directory = imgPath.substring(0, imgPath
				.lastIndexOf(File.separator) + 1);
		StringBuffer sb = new StringBuffer();
		sb.append(directory);
		if (null != addname && !"".equals(addname) && !"null".equals(addname))
			sb.append(addname);
		sb.append(nameWithoutFix).append(suffix);
		imgPath = sb.toString();
		if (type == 1)
			imgPath = imgPath.replace(LARGE, MIDDLE);
		else if (type == 2)
			imgPath = imgPath.replace(LARGE, SMALL);
		return imgPath;
	}

	/**
	 * 压缩图片<br/>
	 * 摘自JAVAEYE
	 * 
	 * @param srcImg
	 *            源图片路径
	 * @param targetImg
	 *            要输出的图片路径
	 * @param width
	 *            输出图片宽度
	 * @param height
	 *            输出图片高度
	 */
	private static void reduceImg(String srcImg, String targetImg, int width,
			int height) {
		try {
			srcImg = srcImg.replace("\\", "/");
			targetImg = targetImg.replace("\\", "/");
			File srcfile = new File(srcImg);
			if (!srcfile.exists()){
				return;
			}
			Image src = javax.imageio.ImageIO.read(srcfile);
			width = width == 0 ? src.getWidth(null) : width;
			height = height == 0 ? src.getHeight(null) : height;
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
			String directory = targetImg.substring(0, targetImg.lastIndexOf("/") + 1);
			FileUtil.createFolder(directory);
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param imgPath
	 *            图片路径
	 * @param addname
	 *            要加入的文件名称
	 * @param imgWidth
	 *            目标宽
	 * @param imgHeight
	 *            目标高
	 * @param per
	 *            压缩百分比
	 * @param type
	 *            1相册中图;2相册小图;0其它
	 */
	@SuppressWarnings("unused")
	private static boolean compressImg(String imgPath, String addname,
			int imgWidth, int imgHeight, float per, int type) {
		boolean flag = false;
		Image src;
		try {
			BufferedImage isrc = javax.imageio.ImageIO.read(new File(imgPath)); // 构造Image对象
			imgPath = imgPath.replace("/", "\\");
			if (System.getProperty("os.name").toLowerCase().contains("linux"))
				imgPath = imgPath.replace("\\", "/");
			String nameWithoutFix = imgPath.substring(imgPath
					.lastIndexOf(File.separator) + 1, imgPath.lastIndexOf("."));
			String suffix = imgPath.substring(imgPath.lastIndexOf("."), imgPath
					.length());
			String directory = imgPath.substring(0, imgPath
					.lastIndexOf(File.separator) + 1);
			StringBuffer sb = new StringBuffer();
			sb.append(directory);
			if (addname!=null&&!addname.equals(""))
				sb.append(addname);
			sb.append(nameWithoutFix).append(suffix);
			imgPath = sb.toString();
			int old_w = isrc.getWidth(null); // 得到源图宽
			int old_h = isrc.getHeight(null);// 得到源图长
			int new_w = 0;
			int new_h = 0;
			double w2 = (old_w * 1.00) / (imgWidth * 1.00);
			double h2 = (old_h * 1.00) / (imgHeight * 1.00);
			// 图片跟据长宽留白，成一个正方形图。
			BufferedImage oldpic;
			if (old_w > old_h) {
				oldpic = new BufferedImage(old_w, old_w,
						BufferedImage.TYPE_INT_RGB);
			} else {
				if (old_w < old_h) {
					oldpic = new BufferedImage(old_h, old_h,
							BufferedImage.TYPE_INT_RGB);
				} else {
					oldpic = new BufferedImage(old_w, old_h,
							BufferedImage.TYPE_INT_RGB);
				}
			}
			Graphics2D g = oldpic.createGraphics();
			g.setColor(Color.white);
			if (old_w > old_h) {
				g.fillRect(0, 0, old_w, old_w);
				g.drawImage(isrc, 0, (old_w - old_h) / 2, old_w, old_h,
						Color.white, null);
			} else {
				if (old_w < old_h) {
					g.fillRect(0, 0, old_h, old_h);
					g.drawImage(isrc, (old_h - old_w) / 2, 0, old_w, old_h,
							Color.white, null);
				} else {
					g.drawImage(isrc.getScaledInstance(old_w, old_h,
							Image.SCALE_SMOOTH), 0, 0, null);
				}
			}
			g.dispose();
			src = oldpic;
			// 图片调整为方形结束
			if (old_w > imgWidth)
				new_w = (int) Math.round(old_w / w2);
			else
				new_w = old_w;
			if (old_h > imgHeight)
				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
			else
				new_h = old_h;
			BufferedImage tag = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,
					0, null);
			if (type == 1)
				imgPath = imgPath.replace(LARGE, MIDDLE);
			else if (type == 2)
				imgPath = imgPath.replace(LARGE, SMALL);
			FileOutputStream newimage = new FileOutputStream(imgPath); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/* 压缩质量 */
			jep.setQuality(per, true);
			encoder.encode(tag, jep);
			newimage.close();
			flag = true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return flag;
	}
	
	public static void main(String[] args) {
//		String src = "/www/hfs/demo/1.jpg";
//		pressCompanyLicense(src,"/www/hfs/demo/2.jpg");
		String src = "/www/hfs/cmp/license/1/10.jpg";
		pressCompanyLicense(src,"/www/hfs/cmp/license/1/000.jpg");
	}
}