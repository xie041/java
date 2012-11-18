package net.lising.core.model;

import static net.lising.lib.LogUtil.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.lising.lib.DateUtil;

/**
 * 使用方法：
 * @author xie041
 *
 */
@SuppressWarnings("unchecked")
public abstract class UploadAction extends AbstractAction {

	private static final long serialVersionUID = 3107789789759080584L;
	// 上传的文件
	private File upload;
	// 上传的文件类型   获取文件扩展名
	private String uploadContentType;
	// 上传的文件名
	private String uploadFileName;
	/**
	 * 文件保存的路径的绝对路径
	 */
	private String savePath;
	// 原文件名称
	private String realFileName;
	
	/**
	 * 上传文件
	 * @param rename  
	 * <pre>
	 * 是否重新命名文件
	 * true 重新命名文件    
	 * false保留上传文件名[自己处理上传文件名重复问题，比如根据时间生成文件夹]
	 * </pre>
	 */
	protected void upload(boolean rename){
		this.setRealFileName(this.uploadFileName);
		if(rename){
			String fileName = DateUtil.generateFileName("",this.uploadFileName);
			this.setUploadFileName(fileName);
		}
		executeUpload();
	}
	
	/**
	 * 上传，前提是要检测文件名是否已经存在，否则会覆盖旧文件
	 * @param new_name
	 */
	protected void upload(String new_name){
		this.setRealFileName(this.uploadFileName);
		this.setUploadFileName(new_name);
		executeUpload();
	}
	
	private void executeUpload(){
		
		//即使调用不检测重复，这里也应该处理
		File f = new File(this.getFilepath()+this.getUploadFileName());
		if(f.exists()){
			String fileName = DateUtil.generateFileName("",this.uploadFileName);
			this.setUploadFileName(fileName);
		}
		
		//文件输出流
		FileOutputStream fos = null;
		FileInputStream fis = null;
		String fpath = this.getFilepath() + "\\";
		if (System.getProperty("os.name").toLowerCase().contains("linux")){
			fpath = fpath.replace("\\", "/");
		}
		try {
			fos = new FileOutputStream(fpath + this.getUploadFileName());
			// 以上传文件建立一個文件上传流
			fis = new FileInputStream(this.getUpload());
			// 将上传文件的内容写入服务器
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		}catch (IOException e) {
			log.error("上传文件 ->upload() 失败："+e.getMessage());
		} finally{
			try {
				if(fis!=null){
					fis.close();
				}
				if(fos!=null){
					fos.close();
				}
			} catch (IOException e) {
				log.error("关闭流异常 ->upload() 失败："+e.getMessage());
			}
		}
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return this.uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFilepath() {
		return this.savePath;
	}

	public void setSavePath(String value) {
		this.savePath = value;
	}
}
