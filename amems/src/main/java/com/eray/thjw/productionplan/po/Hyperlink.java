package com.eray.thjw.productionplan.po;

import org.apache.commons.lang.StringUtils;

/**
 * 超链接对象
 * @author zhuchao
 *
 */
public class Hyperlink {
    
	private String id;
    private Boolean esWorkOrder;
    private Integer gdlx;
    private String gdbh;
    private String zgdbh;
    
    public Hyperlink(String id,Boolean esWorkOrder,Integer gdlx,String gdbh,String zgdbh) {
		this.id = id;
		this.esWorkOrder = esWorkOrder;
		this.gdlx = gdlx;
		this.gdbh = gdbh;
		this.zgdbh = zgdbh;
	}

    public String toHtml() {
    	 StringBuffer workorderSb = new StringBuffer();
			workorderSb.append("<a href='#'");
			workorderSb.append(" id=");
			workorderSb.append(this.getId());
			workorderSb.append(" esWorkOrder=");
			workorderSb.append(this.getEsWorkOrder());
			workorderSb.append(" gddlx=");
			workorderSb.append(this.getGdlx());
			workorderSb.append(">");
			workorderSb.append(this.getGdbh());
			workorderSb.append("</a>");
			workorderSb.append("-");
			workorderSb.append(StringUtils.isNotBlank(zgdbh)?zgdbh:"主单");
			
    	return workorderSb.toString();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getEsWorkOrder() {
		return esWorkOrder;
	}

	public void setEsWorkOrder(Boolean esWorkOrder) {
		this.esWorkOrder = esWorkOrder;
	}

	public Integer getGdlx() {
		return gdlx;
	}

	public void setGdlx(Integer gdlx) {
		this.gdlx = gdlx;
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
	}

	public String getZgdbh() {
		return zgdbh;
	}

	public void setZgdbh(String zgdbh) {
		this.zgdbh = zgdbh;
	}
}