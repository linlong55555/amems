package com.eray.thjw.flightdata.po;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.util.Utils;

import enu.OperateEnum;

/**
 * d_011 附件表
 * @author zhuchao
 *
 */
@SuppressWarnings("serial")
public class Attachment extends BizEntity {
	private String id;

	private String mainid;

	private String yswjm;

	private String wbwjm;

	private String nbwjm;

	private BigDecimal wjdx;
	
	private String wjdxStr;

	private String hzm;

	private String sm;

	private String cflj;

	private Integer yxzt;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)  
	private Date czsj;

	private String czbmid;

	private String czrid;

	private String wjdxs;

	private String ids;

	
	private String biaoshi;//标识
	/**
	 * 扩展字段
	 * 内部文件名前缀（uuid字符）
	 */
	private String uuid;
	
	private String czrname;
	
	private OperateEnum operate;
	
	private String realname;
	
	private Integer type;
	
	
	public String getBiaoshi() {
		return biaoshi;
	}

	public void setBiaoshi(String biaoshi) {
		this.biaoshi = biaoshi;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getWjdxs() {
		return wjdxs;
	}

	public void setWjdxs(String wjdxs) {
		this.wjdxs = wjdxs;
	}

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

	public String getYswjm() {
		return yswjm;
	}

	public void setYswjm(String yswjm) {
		this.yswjm = yswjm == null ? null : yswjm.trim();
	}

	public String getWbwjm() {
		return wbwjm;
	}

	public void setWbwjm(String wbwjm) {
		this.wbwjm = wbwjm == null ? null : wbwjm.trim();
	}

	public String getNbwjm() {
		return nbwjm;
	}

	public void setNbwjm(String nbwjm) {
		this.nbwjm = nbwjm == null ? null : nbwjm.trim();
	}

	public BigDecimal getWjdx() {
		return wjdx;
	}

	public void setWjdx(BigDecimal wjdx) {
		this.wjdx = wjdx;
	}

	public String getHzm() {
		return hzm;
	}

	public void setHzm(String hzm) {
		this.hzm = hzm == null ? null : hzm.trim();
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm == null ? null : sm.trim();
	}

	public String getCflj() {
		return cflj;
	}

	public void setCflj(String cflj) {
		this.cflj = cflj == null ? null : cflj.trim();
	}

	public Integer getYxzt() {
		return yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
	}

	public Date getCzsj() {
		return czsj;
	}

	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}

	public String getCzbmid() {
		return czbmid;
	}

	public void setCzbmid(String czbmid) {
		this.czbmid = czbmid == null ? null : czbmid.trim();
	}

	public String getCzrid() {
		return czrid;
	}

	public void setCzrid(String czrid) {
		this.czrid = czrid == null ? null : czrid.trim();
	}

	public String getUuid() {
		if (StringUtils.isNotBlank(this.getNbwjm())) {
			uuid = this.getNbwjm();
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCzrname() {
		return czrname;
	}

	public void setCzrname(String czrname) {
		this.czrname = czrname;
	}

	public OperateEnum getOperate() {
		 
		return operate;
	}

	public void setOperate(OperateEnum operate) {
		this.operate = operate;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", mainid=" + mainid + ", yswjm="
				+ yswjm + ", wbwjm=" + wbwjm + ", nbwjm=" + nbwjm + ", wjdx="
				+ wjdx + ", hzm=" + hzm + ", sm=" + sm + ", cflj=" + cflj
				+ ", yxzt=" + yxzt + ", czsj=" + czsj + ", czbmid=" + czbmid
				+ ", czrid=" + czrid + ", wjdxs=" + wjdxs + ", ids=" + ids
				+ ", uuid=" + uuid + ", czrname=" + czrname + ", operate="
				+ operate + "]";
	}

	public String getWjdxStr() {
		
		wjdxStr = Utils.FileUtil.bytes2unitG(this.getWjdx()==null?0:this.getWjdx().multiply(BigDecimal.valueOf(1024)).intValue());
		return wjdxStr;
	}

	public void setWjdxStr(String wjdxStr) {
		this.wjdxStr = wjdxStr;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}