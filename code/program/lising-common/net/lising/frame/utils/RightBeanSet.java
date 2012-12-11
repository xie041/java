/**
 * 
 */
package net.lising.frame.utils;

import java.util.ArrayList;
import java.util.List;

import net.lising.frame.vo.ParameteVO;
import net.lising.manage.org.bean.RightBean;

/** 
 * @author : 吕佳诚
 * @Description : TODO 
 * @CreateDate : 2012-4-15 下午04:33:32 
 * @lastModified : 2012-4-15 下午04:33:32 
 * @version : 1.0  
 */
public class RightBeanSet {
	/**权限bean列表*/
	private List<RightBean> listRight ;
	private  RightBean rBean = null;
	private  ParameteVO parameteVO = null;
	private Logger logger = null ;
	
	/**
	 * 加工权限列表
	 * @param listRightBean 权限列表bean
	 * @param parameteVO 参数vo
	 * @param logger 日志类
	 */
	public RightBeanSet(List<RightBean> listRightBean,ParameteVO parameteVO,Logger logger) {
		this.listRight = listRightBean ;
		this.rBean = new RightBean();
		this.parameteVO = parameteVO;
		this.logger = logger;
	}
	
	
	/**
	 * 获取RightBean
	 * @return
	 */
	public  RightBean getRightBean() {
		logger.info("\n\t===[权限设定开始]===");
		
		//获取一级菜单
		setLevelOne(listRight,parameteVO);	
		
		//获取二级菜单。如果一级菜单为空，则不执行，直接返回。
		List<RightBean> levelOneList = rBean.getLevelOneList();
		if( levelOneList.size() > 0 )
		{	
			setLevelTwo(listRight,parameteVO);
		}else {
			logger.info("\t\t\t 权限列表为空！");
			return rBean;
		}
		
		
		logger.info("\t\t\t【是否调入body区域组件】:"+parameteVO.isFirst());
		//判断是否调入组件。
		if (parameteVO.isFirst())
		{
			setLevelThree(listRight,parameteVO);
		}
		
		logger.info("\t===[权限设定结束]===\n");
		return rBean;
	}
	
	/**
	 * 设定一级导航菜单项
	 * @param listRight 权限对象列表
	 * @param parameteVO 参数对象
	 */
	private void setLevelOne(List<RightBean> listRight,ParameteVO parameteVO) {
		logger.info("\n \t---【一级】设定开始---\n");
		
		List<RightBean> levelOneList = new ArrayList<RightBean>();

		//判断该Id是否在权限列表中
//		boolean isRight = false;
		//一级导航
		for (int i = 0,len = listRight.size(); i < len; i++) {
			//判断父ID是否为"0"
			if(FrameConstant.LEVEL_ONE_NAVIGATOR.equalsIgnoreCase( String.valueOf(listRight.get(i).getParentId())) ){
				RightBean rb = new RightBean();
				rb.setId( listRight.get(i).getId() );
				rb.setMenuName( listRight.get(i).getMenuName()  ) ;
				rb.setMenuPosition(	listRight.get(i).getMenuPosition() );//
				rb.setJsFunction( listRight.get(i).getJsFunction());//
				rb.setParentId( listRight.get(i).getParentId() );//
				rb.setUrlAddress(listRight.get(i).getUrlAddress());
				rb.setProjectKey( listRight.get(i).getProjectKey() );
				rb.setOrderBy( listRight.get(i).getOrderBy() );
				
				levelOneList.add(rb);
				
				//地址栏传来的Id为空,将当前工程名同权限列表中的工程名相同的Id作为一级菜单ID
				if( parameteVO.getSerialId() == null ) {
//					logger.info("[ parameteVO.getProjectName().indexOf( listRight.get(i).getProjectKey() )>-1]?");
//					System.out.println( parameteVO.getProjectName().indexOf( listRight.get(i).getProjectKey() )>-1 );
					if ( parameteVO.getProjectName().indexOf( listRight.get(i).getProjectKey() )>-1)
					{
						parameteVO.setSerialId( String.valueOf( listRight.get(i).getId() )  );
////						isRight = true;
					}
				}
//				else {//地址栏传来的Id有值,判断该ID同权限列表中的Id是否相同
//					if( Integer.valueOf(parameteVO.getSerialId()).intValue() ==  listRight.get(i).getId().intValue() ) {
//						isRight = true;
//					}
//				}
				logger.info(
						"\t\tID["+rb.getId()+
						"]父ID["+rb.getParentId()+
						"]名称["+rb.getMenuName()+						
						"]URL["+rb.getUrlAddress()+
						"]工程名["+rb.getProjectKey()+
						"]排序编号[" +rb.getOrderBy()+"]"
						);
			}
		}
		
		
		if(levelOneList.size() > 0)
		{
			levelOneList = sortRightBeanListByOrderByColumn(levelOneList);
			//如果id不在权限列表中，则将第一个id作为该ID。
//			if( !isRight ) {
//				parameteVO.setSerialId(levelOneList.get(0).getId().toString());
//			}
			if( parameteVO.getSerialId() == null ) {
				parameteVO.setSerialId(levelOneList.get(0).getId().toString());
			}
		}
		
		logger.info("\n\t 获取到的parameteVO.getSerialId()为:"+parameteVO.getSerialId());
//		if(levelOneList != null ) {
//			parameteVO.setSerialId(   String.valueOf( levelOneList.get(0).getId() )  );
//		}
		rBean.setLevelOneList(levelOneList);
		logger.info("\n \t---【一级】设定结束---\n");
	}
	
	
	
	
	/**
	 * 设定二级导航菜单
	 * @param listRight 权限对象列表
	 * @param parameteVO 参数对象
	 */
	private void setLevelTwo(List<RightBean> listRight,ParameteVO parameteVO) {
		logger.info("\n \t---【二级】设定开始---\n");
		logger.info("\t\t 获取到的sid(parameteVO.getSerialId)为:"+parameteVO.getSerialId());
		
		List<RightBean> levelTwoList = new ArrayList<RightBean>();
		
		//二级菜单
		/**
		 * 设定说明：
		 * 如果[一级菜单]同 {权限列表}中的[父ID]相同，
		 * 而且,{权限列表} 中的 [菜单位置(0:一级,1:二级,2:左边组件,3:右边组件))] 等于2级
		 * 
		 */
		for (int i = 0,len = listRight.size(); i < len; i++) {
			if( parameteVO.getSerialId().equals( String.valueOf( listRight.get(i).getParentId() ) ) 
					&& Integer.valueOf(listRight.get(i).getMenuPosition()) ==  FrameConstant.LEVEL_TWO_NUMBER ) //原来为：< LEFT_SIZE_NUMBER，更改返回平台的bug时修改
			{
				RightBean rb = new RightBean();
				rb.setId( listRight.get(i).getId() );
				rb.setMenuName( listRight.get(i).getMenuName()  ) ;
				rb.setMenuPosition(	listRight.get(i).getMenuPosition() );//
				rb.setJsFunction( listRight.get(i).getJsFunction());//
				rb.setParentId( listRight.get(i).getParentId() );//
				rb.setUrlAddress( listRight.get(i).getUrlAddress() );//
				rb.setOrderBy( listRight.get(i).getOrderBy() );
				rb.setProjectKey( listRight.get(i).getProjectKey() ) ;
				
				levelTwoList.add(rb);
				
				logger.info(
						"\t\tID["+rb.getId()+
						"]父ID["+rb.getParentId()+
						"]名称["+rb.getMenuName()+						
						"]URL:["+rb.getUrlAddress()+
						"]工程名:["+rb.getProjectKey()+
						"]排序编号：[" +rb.getOrderBy()+"]"
						);
			}
		}
		
		if( levelTwoList.size() > 0 ) {
			levelTwoList = sortRightBeanListByOrderByColumn(levelTwoList);
			if(parameteVO.getSerialId2() == null ) {
				parameteVO.setSerialId2(   String.valueOf( levelTwoList.get(0).getId() )  );
			}
		}
		rBean.setLevelTwoList(levelTwoList);
		logger.info("\t\t 获取到的sid2(parameteVO.getSerialId2)为:"+parameteVO.getSerialId2());
		logger.info("\n \t---【二级】设定结束---\n");
	}
	
	/**
	 * 设定三级（组件）项
	 * @param listRight 权限对象列表
	 * @param parameteVO 参数对象
	 */
	private void setLevelThree(List<RightBean> listRight,ParameteVO parameteVO) {
		logger.info("\n \t---【三级】设定开始---\n");
		
		List<RightBean> levelThreeList = new ArrayList<RightBean>();

		//三级菜单
		for (int i = 0,len = listRight.size(); i < len; i++) {
			//5-04日更改，将组建挂靠到1级导航
//			if( parameteVO.getSerialId2().equals( String.valueOf( listRight.get(i).getParentId() ) ) )
			if( parameteVO.getSerialId2().equals( String.valueOf( listRight.get(i).getParentId() ) ) 
					&& Integer.valueOf(listRight.get(i).getMenuPosition())>= FrameConstant.LEFT_SIZE_NUMBER
					//&& !parameteVO.getSerialId2().equals( String.valueOf(listRight.get(i).getId()))
				)
			{
				RightBean rb = new RightBean();
				rb.setId( listRight.get(i).getId() );
				rb.setMenuName( listRight.get(i).getMenuName()  ) ;
				rb.setMenuPosition(	listRight.get(i).getMenuPosition() );//
				rb.setJsFunction( listRight.get(i).getJsFunction());//
				rb.setParentId( listRight.get(i).getParentId() );//
				rb.setUrlAddress( listRight.get(i).getUrlAddress() );//
				rb.setOrderBy( listRight.get(i).getOrderBy() );
				
				levelThreeList.add(rb);
				
				logger.info(
						"\t\tID["+rb.getId()+
						"]父ID:"+rb.getParentId()+
						"]名称:["+rb.getMenuName()+		
						"]URL:["+rb.getUrlAddress()+
						"]JS方法:["+rb.getJsFunction()+
						"]排序编号：[" +rb.getOrderBy()+"]"
				);
			}
		}
		
		if( levelThreeList.size()> 0 ) {
			levelThreeList = sortRightBeanListByOrderByColumn(levelThreeList);
		}
		rBean.setLevelThreeList(levelThreeList);
		logger.info("\n \t---【三级】设定结束---\n");
	}
	
	/**
	 * 通过[orderBy]列将列表中的RightBean对象排序
	 * @param list RightBean列表
	 */
	private List<RightBean> sortRightBeanListByOrderByColumn(List<RightBean> list) {
		logger.info("\t\t\t[权限对象列表排序开始]");
		
		if( list == null )
		{
			logger.info("\t\t\t\t[权限对象列表 为空]");
			return null;
		}
		RightBean rb = new RightBean();
		int sort1 = 0;
		int sort2 = 0;
		
		for (int i = 0 , len = list.size(); i < len; i++) {
			 sort1 = list.get(i).getOrderBy();
			for (int j = 0 , len1 = list.size()-1; j < len1; j++) {
				 sort2 = list.get(j).getOrderBy();
				if( sort1 < sort2)
				{
					rb = list.get(i) ;
					list.set(i, list.get(j));
					list.set(j, rb);
				}
			}
		}
		logger.info("\t\t\t[权限对象列表排序结束]排序成功！结果如下：");
		
		for (int i = 0 , len = list.size(); i < len; i++) {
			rb = list.get(i) ;
			logger.info(
						"\t\t\t"+
						rb.getMenuName()+"("+rb.getId()+")" +
						"\t\t排序号：" + rb.getOrderBy()
						);
		}
		
		return list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
