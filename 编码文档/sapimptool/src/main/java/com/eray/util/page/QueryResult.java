package com.eray.util.page;

import java.util.List;
/**
 * QueryResult类来存放结果集和总记录数
 * @author GQ
 *
 * @param <T>
 */
public class QueryResult<T> {
	private List<T> resultlist;
	private long totalrecord;

	
	public QueryResult() {
	}

	public QueryResult(List<T> resultlist, long totalrecord) {
		this.resultlist = resultlist;
		this.totalrecord = totalrecord;
	}

	public List<T> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}

	public long getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
}
