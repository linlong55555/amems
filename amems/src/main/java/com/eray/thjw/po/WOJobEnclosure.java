package com.eray.thjw.po;
import java.math.BigDecimal;
import java.util.Date;
import com.eray.thjw.util.Utils;
/**
 * 工单附件实体
 * @author meizhiliang
 *对应表：b_g_00603
 */
public class WOJobEnclosure {
    private String id;

    private String mainid;

    private String yswjm;

    private String wbwjm;

    private String nbwjm;

    private String sm;

    private String cflj;

    private Integer yxzt;

    private String czdwid;

    private String czrid;

    private Date czsj;

    private String dprtcode;
    
    private User czr_user;
    
    private BigDecimal wjdx;
    
    private String hzm;
    
    private String wjdxStr;
    
    public String getWjdxStr() {
    	wjdxStr = Utils.FileUtil.bytes2unitG(this.getWjdx()==null?0:this.getWjdx().multiply(BigDecimal.valueOf(1024)).intValue());
		return wjdxStr;
	}

	public void setWjdxStr(String wjdxStr) {
		this.wjdxStr = wjdxStr;
	}
    
    
	public String getHzm() {
		return hzm;
	}

	public void setHzm(String hzm) {
		this.hzm = hzm;
	}

	public BigDecimal getWjdx() {
		return wjdx;
	}

	public void setWjdx(BigDecimal wjdx) {
		this.wjdx = wjdx;
	}

	public User getCzr_user() {
		return czr_user;
	}

	public void setCzr_user(User czr_user) {
		this.czr_user = czr_user;
	}

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

	public String getYswjm() {
		return yswjm;
	}

	public void setYswjm(String yswjm) {
		this.yswjm = yswjm;
	}

	public String getWbwjm() {
		return wbwjm;
	}

	public void setWbwjm(String wbwjm) {
		this.wbwjm = wbwjm;
	}

	public String getNbwjm() {
		return nbwjm;
	}

	public void setNbwjm(String nbwjm) {
		this.nbwjm = nbwjm;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getCflj() {
		return cflj;
	}

	public void setCflj(String cflj) {
		this.cflj = cflj;
	}

	public Integer getYxzt() {
		return yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
	}

	public String getCzdwid() {
		return czdwid;
	}

	public void setCzdwid(String czdwid) {
		this.czdwid = czdwid;
	}

	public String getCzrid() {
		return czrid;
	}

	public void setCzrid(String czrid) {
		this.czrid = czrid;
	}

	public Date getCzsj() {
		return czsj;
	}

	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	@Override
	public String toString() {
		return "WOJobEnclosure [id=" + id + ", mainid=" + mainid + ", yswjm="
				+ yswjm + ", wbwjm=" + wbwjm + ", nbwjm=" + nbwjm + ", sm="
				+ sm + ", cflj=" + cflj + ", yxzt=" + yxzt + ", czdwid="
				+ czdwid + ", czrid=" + czrid + ", czsj=" + czsj
				+ ", dprtcode=" + dprtcode + "]";
	}

   
}