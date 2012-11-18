package net.lising.lib.image;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

 /*******************************************************************************
 * 该JavaBean可以直接在其他Java应用程序中调用，实现屏幕的"拍照" This JavaBean is used to snapshot the
 * GUI in a Java application! You can embeded it in to your java application
 * source code, and us it to snapshot the right GUI of the application
 * @author xieyong   Email:xie041@126.com
 * @2011-6-8 上午11:35:40
 *******************************************************************************/
class SnapShot{
	
    private String fileName; // 文件的前缀

    private String defaultName = "LisingCamera";

    static int serialNum = 0;
    
    static String savePath = "";

    private String imageFormat; // 图像文件的格式

    private String defaultImageFormat = "png";

    //获取屏幕坐标
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    /***********************************************************************
      * 默认的文件前缀为GuiCamera，文件格式为PNG格式 The default construct will use the
      * default Image file surname "GuiCamera", and default image format
      * "png"
      **********************************************************************/
    public SnapShot(){
	     fileName = defaultName;
	     imageFormat = defaultImageFormat;
    }

    /***********************************************************************
      * @param s the surname of the snapshot file
      * @param format the format of the image file, it can be "jpg" or "png"
      * 本构造支持JPG和PNG文件的存储
      **********************************************************************/
    public SnapShot(String s, String format){
	     fileName = s;
	     imageFormat = format;
    }
    
    /***********************************************************************
     * @param path 保存路径，为空，则默认当前项目
     * @param s the surname of the snapshot file
     * @param format the format of the image file, it can be "jpg" or "png"
     * 本构造支持JPG和PNG文件的存储
     **********************************************************************/
    public SnapShot(String path,String s, String format){
    	savePath = path;
    	fileName = s;
    	imageFormat = format;
    }
    
    /***********************************************************************
     * 按照屏幕坐标截图
     * @param path
     * @param s
     * @param format
     * @param x
     * @param y
     **********************************************************************/
    public SnapShot(String path,String s, String format,int x ,int y){
    	savePath = path;
    	fileName = s;
    	imageFormat = format;
    	d.setSize(x, y);
    }

    /***********************************************************************
      * 对屏幕进行拍照 snapShot the Gui once
      **********************************************************************/
    public void snapShot(){
	    try{
	        // 拷贝屏幕到一个BufferedImage对象screenshot
	         BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0,(int) d.getWidth(), (int) d.getHeight()));
	         serialNum++;
	        // 根据文件前缀变量和文件格式变量，自动生成文件名
	         String name = fileName + String.valueOf(serialNum) + "." + imageFormat;
	         File f = new File(savePath + name);
	        
	         ImageIO.write(screenshot, imageFormat, f);// 将screenshot对象写入图像文件
	         System.out.print("Snapshot Finished! 存储路径:"+(savePath.equals("")?"当前项目下":savePath));
	     } catch (Exception ex){
	         System.out.println(ex);
	     }
     }

    public static void main(String[] args){
    	SnapShot cam = new SnapShot("/www/hfs/", "snapshot", "png", 600, 400);
    	cam.snapShot();
     }
}


