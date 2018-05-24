package com.eray.thjw.flightdata.po;

public class DisassemblyRecord {

    private String id;
    /**
     * 与主表id对应，第一个sql和第二个sql的id是1对多的关系
     */
    private String mainid;
    /**
     * --监控分类编码
     */
    private String jkflbh;
    /**
     * --监控项目编号
     */
    private String jklbh;

    /**
     * --规定上限
     */
    private String gdsx;

    /**
     * --规定上限单位
     */
    private String gdsx_dw;
    
    /**
     * --装机前已用
     */
    private String bjyy;
    
    /**
     * --装机前已用单位
     */
    private String bjyy_dw;
    
    /**
     * --在机使用
     */
    private String zjsy;
    
//    private DisassemblyRecord
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getJkflbh() {
		return jkflbh;
	}

	public void setJkflbh(String jkflbh) {
		this.jkflbh = jkflbh;
	}

	public String getJklbh() {
		return jklbh;
	}

	public void setJklbh(String jklbh) {
		this.jklbh = jklbh;
	}

	public String getGdsx() {
		return gdsx;
	}

	public void setGdsx(String gdsx) {
		this.gdsx = gdsx;
	}

	public String getGdsx_dw() {
		return gdsx_dw;
	}

	public void setGdsx_dw(String gdsx_dw) {
		this.gdsx_dw = gdsx_dw;
	}

	public String getBjyy() {
		return bjyy;
	}

	public void setBjyy(String bjyy) {
		this.bjyy = bjyy;
	}

	public String getBjyy_dw() {
		return bjyy_dw;
	}

	public void setBjyy_dw(String bjyy_dw) {
		this.bjyy_dw = bjyy_dw;
	}

	public String getZjsy() {
		return zjsy;
	}

	public void setZjsy(String zjsy) {
		this.zjsy = zjsy;
	}
 
}