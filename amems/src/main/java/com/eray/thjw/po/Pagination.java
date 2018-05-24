package com.eray.thjw.po;

import java.io.Serializable;

public class Pagination implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 页数
	 */
	private int page = 1;

	/**
	 * 每页行数
	 */
	private int rows = 20;
	
	private int end;
	
	private int start;

	/**
	 * 排序列字段名
	 */
	private String sort = "auto";

	/**
	 * 排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
	 */
	private String order = "asc";

	public Pagination() {

	}

	public int getPage() {
		return page>0?page:1;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * 计算从第几行开始
	 */
	public void initPageIndex() {
		this.page = (page - 1) * rows;
	}

	@Override
	public String toString() {
		return "Pagination [page=" + page + ", rows=" + rows + ", sort=" + sort
				+ ", order=" + order + "]";
	}

	public int getEnd() {
		end = page * rows;
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStart() {
		start = (page - 1) * rows;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}
