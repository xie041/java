package net.lising.lib;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import net.lising.urls.ProjectUtil;

import org.apache.log4j.Logger;
import org.junit.Test;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * 压缩css，js
 * @author xie041
 */
public class CompressJsCss {
	
	private static Logger log = Logger.getLogger(CompressJsCss.class);
	
	/**
	 * <pre>
	 * 测试
	 * 要处理的目录
	 * File dir = new File("D:\\workspace\\u8cerp\\WebRoot\\js");
	 * </pre>
	 * @throws Exception
	 */
	@Test
	public void testMain() throws Exception{
		File dir = new File(ProjectUtil.getFileUploadAddressInDisk());
		compressFile(dir);
	}
	
	/**
	 * 压缩css，js
	 * @param file
	 * @throws Exception
	 */
	public static void compressFile(File file) throws Exception{
		
		log.debug("Ready to compress js and css in "+file.toString());

		/*单文件直接压缩*/
		if(file.isFile()){
			zipSingle(file);
			return;
		}
		
		/*目录则递归*/
		File[] files = file.listFiles();
		
		/*空目录*/
		if(files==null||files.length==0) return;
		
		/*目录中包含文件*/
		for(File f:files){
			
			/*单文件直接压缩*/
			if(file.isFile()){
				zipSingle(file);
				continue;
			}
			
			/*目录则递归*/
			compressFile(f);
		}
		
		log.debug("Compress js and css finished");
	}
	
	/**
	 * 压缩
	 * @param file
	 * @throws Exception
	 */
	private static void zipSingle(File file) throws Exception{
		
		String fileName = file.getName();
		
		/*非js或者css文件*/
		if(!(css(fileName)||js(fileName))) return;
		
		log.debug(fileName);
		
		Reader in = new FileReader(file);
		String filePath = file.getAbsolutePath();
		File temp = new File(filePath+".temp");
		Writer out = new FileWriter(temp);
		
		if(js(fileName)){
			JavaScriptCompressor jscompressor=new JavaScriptCompressor(in,new org.mozilla.javascript.ErrorReporter() {
					
				@Override
				public void warning(String message, String sourceName,  int line, String lineSource, int lineOffset) {
					if (line < 0) {
						log.error("\n[WARNING] " + message);
	                } else {
	                	log.error("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
	                }
				}
				
				@Override
				public org.mozilla.javascript.EvaluatorException runtimeError(String message, String sourceName, 
						int line, String lineSource, int lineOffset) {
					error(message, sourceName, line, lineSource, lineOffset);
	                return new org.mozilla.javascript.EvaluatorException(message);
				}
				
				@Override
				public void error(String message, String sourceName,  int line, String lineSource, int lineOffset) {
					if (line < 0) {
						log.error("\n[ERROR] " + message);
	                } else {
	                	log.error("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
	                }
				}
			});
	//		private static int linebreakpos = -1;
	//		private static boolean munge = true;
	//		private static boolean verbose = false;
	//		private static boolean preserveAllSemiColons = false;
	//		private static boolean disableOptimizations = false;
			jscompressor.compress(out, -1, true, false, false, false);
		}else if(css(fileName)){
			 CssCompressor csscompressor = new CssCompressor(in);
			 csscompressor.compress(out, -1);
		}
		
		out.close();
		in.close();
		file.delete();
		temp.renameTo(file);
		temp.delete();
	}
	
	/**
	 * 判断css
	 * @param fileName
	 * @return
	 */
	private static boolean css(String fileName){
		if(fileName.toLowerCase().endsWith(".css")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断js
	 * @param fileName
	 * @return
	 */
	private static boolean js(String fileName){
		if(fileName.toLowerCase().endsWith(".js")){
			return true;
		}else{
			return false;
		}
	}
}