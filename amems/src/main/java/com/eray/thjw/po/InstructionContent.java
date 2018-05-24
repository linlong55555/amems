package com.eray.thjw.po;

import java.util.Date;

public class InstructionContent extends BaseEntity{
    private String id;

    private String mainid;

    private String gznr;

    private String czdwid;

    private String czrid;

    private Date czsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr == null ? null : gznr.trim();
    }

    public String getCzdwid() {
        return czdwid;
    }

    public void setCzdwid(String czdwid) {
        this.czdwid = czdwid == null ? null : czdwid.trim();
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid == null ? null : czrid.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

	@Override
	public String toString() {
		return "InstructionContent [id=" + id + ", mainid=" + mainid
				+ ", gznr=" + gznr + ", czdwid=" + czdwid + ", czrid=" + czrid
				+ ", czsj=" + czsj + "]";
	}
    
    
}