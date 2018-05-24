package com.eray.util.page;

/**
 * 此类有一个静态方法，返回带有分页的开始索引和结束索引值的对象
 */
public class PageIndex {
	private long startindex;
	private long endindex;

	public PageIndex(long startindex, long endindex) {
		this.startindex = startindex;
		this.endindex = endindex;
	}

	public long getStartindex() {
		return startindex;
	}

	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}

	public long getEndindex() {
		return endindex;
	}

	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}

	/**
	 * 静态方法来获取分页的开始索引和结束索引的值
	 * 
	 * @param viewpagecount
	 *            分页栏总共显示多少个页码
	 * @param currentPage
	 *            当前页码值
	 * @param totalpage
	 *            总共页码值
	 * @return 带有开始索引和结束索引的页码对象
	 */
	public static PageIndex getPageIndex(long viewpagecount, int currentPage,
			long totalpage) {
		long startpage = currentPage
				- (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1
						: viewpagecount / 2);
		long endpage = currentPage + viewpagecount / 2;
		if (startpage < 1) {
			startpage = 1;
			if (totalpage >= viewpagecount)
				endpage = viewpagecount;
			else
				endpage = totalpage;
		}
		if (endpage > totalpage) {
			endpage = totalpage;
			if ((endpage - viewpagecount) > 0)
				startpage = endpage - viewpagecount + 1;
			else
				startpage = 1;
		}
		return new PageIndex(startpage, endpage);
	}
}