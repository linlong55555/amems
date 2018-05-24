package com.eray.util.page;

import java.util.List;

/**
 * 封装了Action向jsf所存放的一些数据
 * 该类最重要的是调用setQueryResult方法，他会连环设置该类其他属性的值。
 * @author bruce
 * @param <T>
 */
public class PageView<T> {
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageindex;
	/** 总页数 **/
	private long totalpage = 1;
	/** 每页显示记录数 **/
	private int maxresult = 12;
	/** 当前页 **/
	private int currentpage = 1;
	/** 总记录数 **/
	private long totalrecord;
	/** 页码数量 **/
	private int pagecode = 10;
	/** 要获取记录的开始索引 **/
	public int getFirstResult() {
		return (this.currentpage-1)*this.maxresult;
	}
	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	/**
	 * 构造方法，要使用这个类，必须传进每页显示记录数和当前页。
	 * @param maxresult
	 * @param currentpage
	 */
	public PageView(int maxresult, int currentpage) {
		this.maxresult = maxresult;
		this.currentpage = currentpage;
	}
	
	/**
	 * 该方法存在一个连环的调用
	 * 将从数据库分页查询出来的总结果集和总记录数设置进来。
	 * 在设置总记录数的同时，会换算出总页数。详情参看setTotalrecord方法。
	 * 在设置总页数的同时，包含分页的开始索引和结束索引的PageIndex对象也确定了下来。详情参看setTotalpage方法。
	 * @param qr
	 */
	public void setQueryResult(QueryResult<T> qr){
		setRecords(qr.getResultlist());
		setTotalrecord(qr.getTotalrecord());
	}
	
	public long getTotalrecord() {
		return totalrecord;
	}
	/**
	 * 在设置总记录数的同时，会将总页数也设置进来。
	 * 在设置总页数的同时，包含分页的开始索引和结束索引的PageIndex对象也确定了下来。
	 * @param totalrecord
	 */
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
		setTotalpage(this.totalrecord%this.maxresult==0? this.totalrecord/this.maxresult : this.totalrecord/this.maxresult+1);
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public PageIndex getPageindex() {
		return pageindex;
	}
	public long getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
		this.pageindex = PageIndex.getPageIndex(pagecode, currentpage, totalpage);
	}
	public int getMaxresult() {
		return maxresult;
	}
	public int getCurrentpage() {
		return currentpage;
	}
}