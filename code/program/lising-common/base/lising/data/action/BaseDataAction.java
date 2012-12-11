package base.lising.data.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lising.lib.citys.CityUtil;
import net.lising.rmt.bean.ChinaCitys;
import net.lising.rmt.spi.RmtBaseDataService;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@IocBean
@Ok("json")
public class BaseDataAction {
	
	@Inject
	private RmtBaseDataService service;
	
	Map<String, String> map ;
	
	@At("/ajax/city")
	@Ok("void")
	public void demo(HttpServletRequest request,HttpServletResponse response,@Param("name") String name,@Param("callback") String callback){
		List<ChinaCitys> list = service.listCitysByNameMatching(name);
		JSONArray array = new JSONArray();
		for(ChinaCitys c : list){
			JSONObject obj = new JSONObject();
			obj.put("id", c.getId());
			obj.put("name", c.getCityName());
			array.add(obj);
		}
		jsonnpResponse(response, callback,array.toString());
	}
	
	public static void jsonnpResponse(HttpServletResponse response,
			String callback, String msg) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(callback + "(" + msg + ")");
		} catch (IOException e) {

		}
	}
	
	@At("/demo")
	@Ok("void")
	public void demo(HttpServletRequest request,HttpServletResponse response){
//		map = SuperEncript.getEncriptParams(request);
//		System.out.println(map.get("id"));
		System.out.println(CityUtil.cityList);
		System.out.println(CityUtil.getCity(1).getCityName());
	}
	
//	@At("/citys/html")
//	@Ok("jsp:/common/citys.jsp")
//	public List<TempProvince> html(){
//		return service.demolist();
//	}
}