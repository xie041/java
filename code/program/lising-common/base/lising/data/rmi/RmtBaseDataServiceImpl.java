package base.lising.data.rmi;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.lising.rmt.bean.ChinaCitys;
import net.lising.rmt.spi.RmtBaseDataService;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import com.caucho.hessian.server.HessianServlet;

@IocBean(name="service")
public class RmtBaseDataServiceImpl extends HessianServlet implements RmtBaseDataService {
	
	private static final long serialVersionUID = 321111856965778546L;
	
	@Inject
	private Dao dao;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = Mvcs.getIoc().get(Dao.class);
	}

	@Override
	public List<ChinaCitys> listCitysByProvince(int province) {
		return dao.query(ChinaCitys.class, Cnd.where("father","=",province));
	}
	
	@Override
	public List<ChinaCitys> listCitysByNameMatching(String name) {
		return dao.query(ChinaCitys.class, Cnd.where("cityName","like","%"+name+"%"));
	}

	@Override
	public ChinaCitys findCityByCityId(int cid) {
		return dao.fetch(ChinaCitys.class, Cnd.where("id", "=", cid));
	}

//	@Override
//	public List<TempProvince> demolist() {
//		List<TempProvince> list = dao.query(TempProvince.class, null);
//		List<City> clist = null;
//		for(TempProvince t : list){
//			clist = dao.query(City.class, Cnd.where("father", "=", t.getId()));
//			t.setList(clist);
//		}
//		return list;
//	}

}