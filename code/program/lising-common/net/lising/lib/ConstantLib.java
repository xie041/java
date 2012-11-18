package net.lising.lib;

/**
 * ConstantLib.java 静态常量
 *
 * @version ConstantLib 1.0
 * @author 冷燕她哥
 * @date 2012-4-11 上午10:41:13
 */
public class ConstantLib {
	
	/**
	 * request.getSession.setAttribute("ctx");
	 */
	public static final String USERCONTEXT = "ctx";
	
	/**
	 * Long型 数字0
	 */
	public static final Long ZERO = 0L;
	/**
	 * int型 数字1
	 */
	public static final int ONE = 1;
	/**
	 * int型 数字2
	 */
	public static final int TWO = 2;
	/**
	 * 空字符串
	 * <br><br>值：""
	 */
	public static final String NULL= "";
	/**
	 * int型 男性
	 * <br><br>值：0
	 */
	public static final int INT_SEX_MAN = 0;
	/**
	 * int型 女性
	 * <br><br>值：1
	 */
	public static final int INT_SEX_WOMAN = 1;
	/**
	 * String型 男性
	 * <br><br>值："男"
	 */
	public static final String STR_SEX_MAN = "男";
	/**
	 * String型 女性
	 * <br><br>值："女"
	 */
	public static final String STR_SEX_WOMAN = "女";
	/**
	 * int型 操作成功
	 * <br><br>值：1
	 */
	public static final int SUCCEED = 1;
	/**
	 * int型 操作失败
	 * <br><br>值：0
	 */
	public static final int FAIL = 0;
	/**
	 * String[]型 默认添加的部门名称
	 * <br><br>值：{"管理部","财务","运维部"}
	 */
	public static final String[] DEFAULT_APPEND_NAME = {"管理部","财务","运维部"};
	/**
	 * String型 管理部
	 * <br><br>值："管理部"
	 */
	public static final String GENERAL_MANAGER = "管理部";
	/**
	 * String型 管理员
	 * <br><br>值："管理员"
	 */
	public static final String GENERAL_MANAGER_POSITION = "管理员";
	/**
	 * String型 财务
	 * <br><br>值："财务"
	 */
	public static final String FINANCE = "财务";
	/**
	 * String型 运维部
	 * <br><br>值："运维部"
	 */
	public static final String ADMIN = "运维部";
	/**
	 * Long型 默认父id
	 * <br><br>值：-9
	 */
	public static final Long DEFAULT_PARENT_ID = -9L;
	/**
	 * Long 型 默认错误返回值
	 * <br><br>值：-13
	 */
	public static final Long ERROR_RETURN = -13L;
}
