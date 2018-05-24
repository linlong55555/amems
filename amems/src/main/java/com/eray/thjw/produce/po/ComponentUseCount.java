package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_S2_913 部件使用汇总
 * @CreateTime 2017年10月25日 下午4:46:44
 * @CreateBy 徐勇
 */
public class ComponentUseCount extends BaseEntity {
  
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private Date whsj;

    private String jh;

    private String xlh;

    private String fjzch;

    private String zjqdid;

    private String jklbh;

    private String jkflbh;

    private Integer sjFz;

    private Integer csz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public Integer getSjFz() {
        return sjFz;
    }

    public void setSjFz(Integer sjFz) {
        this.sjFz = sjFz;
    }

    public Integer getCsz() {
        return csz;
    }

    public void setCsz(Integer csz) {
        this.csz = csz;
    }
}