package com.eray.thjw.system.po;


import java.util.Date;
import com.eray.thjw.po.BaseEntity;
/**
 * t_BOOKMARK 书签
 * @author peixiu
 *
 */
public class BookMark extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String jgdm;//机构代码

    private String yhid;//用户id

    private String mc;//名称

    private String dz;//地址
    
    private Integer zt;//状态
    
    private Date whsj;//维护时间
    
    private String whrid;//维护人id

	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getYhid() {
		return yhid;
	}

	public void setYhid(String yhid) {
		this.yhid = yhid;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

}