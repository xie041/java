package base.lising;

import java.io.File;

import net.lising.lib.CompressJsCss;
import net.lising.urls.ProjectUtil;

import org.apache.log4j.Logger;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class AppSetup implements Setup {
    
    private Logger log = Logger.getLogger(AppSetup.class);
    
    @Override
    public void init(NutConfig config) {
    	
    	//启动服务就压缩一次静态文件，比较好的策略是提供一个方法，随时压缩
    	//windows   D:/files/
    	//linux     /www/hfs
    	try {
			CompressJsCss.compressFile(new File(ProjectUtil.getFileUploadAddressInDisk()));
		} catch (Exception e) {
			log.error("压缩静态文件失败！",e);
		}
    }

    @Override
    public void destroy(NutConfig config) { }
}