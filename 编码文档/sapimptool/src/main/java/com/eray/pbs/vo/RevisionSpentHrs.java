package com.eray.pbs.vo;

public class RevisionSpentHrs {
	private double totalHrs;
	private String rid;

	public double getTotalHrs() {
		return totalHrs;
	}

	public void setTotalHrs(double totalHrs) {
		this.totalHrs = totalHrs;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "RevisionSpentHrs [totalHrs=" + totalHrs + ", rid=" + rid + "]";
	}
	
	

}
