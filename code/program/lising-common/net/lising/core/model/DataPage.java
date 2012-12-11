package net.lising.core.model;

import java.io.Serializable;
import java.util.List;

public class DataPage<T> implements Serializable{
	
	private static final long serialVersionUID = 1524850944311166138L;

	public static final int PAGE_SIZE = 20;
	
	public static final int FIRST_PAGE = 1;
	/**
	 * 数据list
	 */
	private List<T> datas;
	/**
	 * 总条数
	 */
	private int totalRows;
	/**
	 * 分页大小
	 */
	private int pageSize = PAGE_SIZE;
	/**
	 * 第一页
	 */
	@SuppressWarnings("unused")
	private int firstIndex;
	/**
	 * 当前页
	 */
	private int currPage = FIRST_PAGE;
	/**
	 * 分页的action
	 */
	private String url;
	/**
	 * 查询参数序列化编码   name=zhangsan&pass=12345&sex=1
	 */
	private String sercode;
	public int getTotalPages() {
		return (this.totalRows) / this.pageSize
				+ (this.totalRows % this.pageSize != 0 ? 1 : 0);
	}
	public int getTopPageNo() {
		return FIRST_PAGE;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPreviousPageNo() {
		if (this.currPage <= 1)
			return FIRST_PAGE;
		return this.currPage - 1;
	}

	public int getNextPageNo() {
		if (this.currPage >= this.getBottomPageNo())
			return this.getBottomPageNo();
		return this.currPage + 1;
	}

	public int getBottomPageNo() {
		return this.getTotalPages();
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getFirstIndex() {
		return (currPage - 1) * pageSize;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public String getSercode() {
		return sercode;
	}

	public void setSercode(String sercode) {
		this.sercode = sercode;
	}

}
