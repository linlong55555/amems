package com.eray.thjw.produce.po;

import com.eray.thjw.po.BizEntity;

/** 
 * @Description 飞机装机清单导入Excel
 * @CreateTime 2017-11-28 下午4:56:19
 * @CreateBy 雷伟	
 */
public class InstallationList extends BizEntity{
	private static final long serialVersionUID = 1L;
	
	private String id; //基础id
	private String fjdid; //父节点id
	
	private Integer cj;  //层级
	private String zjh; //章节号
	private String jh;  //件号
	private String xlh; //序列号
	private String cjjh; //厂家件号
	private String ywmc; //英文名称
	private String zwmc; //中文名称
	private String xh; //型号
	private String wz; //（分类）位置
	private String pch; //批次号
	private String fjzw; //飞机站位
	private String zjsl; //装机数量
	private String jldw; //计量单位
	private String zjjlx; //装机件类型
	private String bjgzjl;//部件改装记录
	private String tsn; //TSN
	private String tso; //TSO
	private String csn; //CSN
	private String cso; //CSO
	private String bz; //备注
    private String chucrq; //出厂日期
    private String azrq; //安装日期
    private String azsj; //安装时间
    private String azjldh; //安装记录单号
    private String azr; //安装人
    private String azbz; //安装说明
    private String ccrq; //拆除日期
    private String ccsj; //拆除时间
    private String ccjldh; //拆除记录单号
    private String ccr; //拆除人
    private String ccbz; //拆除说明
    private String llklx;//履历卡类型
    private String llkbh;//履历卡编号

    private String csrq;//初始日期
    private String fxsj;//飞行时间
    private String fxxh;//飞行循环
    private String fdjapusj;//发动机时间/APU时间
    private String fdjapuxh;//发动机循环/APU循环
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFjdid() {
		return fjdid;
	}
	public void setFjdid(String fjdid) {
		this.fjdid = fjdid;
	}
	public Integer getCj() {
		return cj;
	}
	public void setCj(Integer cj) {
		this.cj = cj;
	}
	public String getZjh() {
		return zjh;
	}
	public void setZjh(String zjh) {
		this.zjh = zjh;
	}
	public String getJh() {
		return jh;
	}
	public void setJh(String jh) {
		this.jh = jh;
	}
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getCjjh() {
		return cjjh;
	}
	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}
	public String getYwmc() {
		return ywmc;
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	public String getZwmc() {
		return zwmc;
	}
	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}
	public String getWz() {
		return wz;
	}
	public void setWz(String wz) {
		this.wz = wz;
	}
	public String getPch() {
		return pch;
	}
	public void setPch(String pch) {
		this.pch = pch;
	}
	public String getFjzw() {
		return fjzw;
	}
	public void setFjzw(String fjzw) {
		this.fjzw = fjzw;
	}
	public String getZjsl() {
		return zjsl;
	}
	public void setZjsl(String zjsl) {
		this.zjsl = zjsl;
	}
	public String getJldw() {
		return jldw;
	}
	public void setJldw(String jldw) {
		this.jldw = jldw;
	}
	public String getZjjlx() {
		return zjjlx;
	}
	public void setZjjlx(String zjjlx) {
		this.zjjlx = zjjlx;
	}
	public String getBjgzjl() {
		return bjgzjl;
	}
	public void setBjgzjl(String bjgzjl) {
		this.bjgzjl = bjgzjl;
	}
	public String getTsn() {
		return tsn;
	}
	public void setTsn(String tsn) {
		this.tsn = tsn;
	}
	public String getTso() {
		return tso;
	}
	public void setTso(String tso) {
		this.tso = tso;
	}
	public String getCsn() {
		return csn;
	}
	public void setCsn(String csn) {
		this.csn = csn;
	}
	public String getCso() {
		return cso;
	}
	public void setCso(String cso) {
		this.cso = cso;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getChucrq() {
		return chucrq;
	}
	public void setChucrq(String chucrq) {
		this.chucrq = chucrq;
	}
	public String getAzrq() {
		return azrq;
	}
	public void setAzrq(String azrq) {
		this.azrq = azrq;
	}
	public String getAzsj() {
		return azsj;
	}
	public void setAzsj(String azsj) {
		this.azsj = azsj;
	}
	public String getAzjldh() {
		return azjldh;
	}
	public void setAzjldh(String azjldh) {
		this.azjldh = azjldh;
	}
	public String getAzr() {
		return azr;
	}
	public void setAzr(String azr) {
		this.azr = azr;
	}
	public String getAzbz() {
		return azbz;
	}
	public void setAzbz(String azbz) {
		this.azbz = azbz;
	}
	public String getCcrq() {
		return ccrq;
	}
	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}
	public String getCcsj() {
		return ccsj;
	}
	public void setCcsj(String ccsj) {
		this.ccsj = ccsj;
	}
	public String getCcjldh() {
		return ccjldh;
	}
	public void setCcjldh(String ccjldh) {
		this.ccjldh = ccjldh;
	}
	public String getCcr() {
		return ccr;
	}
	public void setCcr(String ccr) {
		this.ccr = ccr;
	}
	public String getCcbz() {
		return ccbz;
	}
	public void setCcbz(String ccbz) {
		this.ccbz = ccbz;
	}
	public String getLlklx() {
		return llklx;
	}
	public void setLlklx(String llklx) {
		this.llklx = llklx;
	}
	public String getLlkbh() {
		return llkbh;
	}
	public void setLlkbh(String llkbh) {
		this.llkbh = llkbh;
	}
	public String getCsrq() {
		return csrq;
	}
	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	public String getFxsj() {
		return fxsj;
	}
	public void setFxsj(String fxsj) {
		this.fxsj = fxsj;
	}
	public String getFxxh() {
		return fxxh;
	}
	public void setFxxh(String fxxh) {
		this.fxxh = fxxh;
	}
	public String getFdjapusj() {
		return fdjapusj;
	}
	public void setFdjapusj(String fdjapusj) {
		this.fdjapusj = fdjapusj;
	}
	public String getFdjapuxh() {
		return fdjapuxh;
	}
	public void setFdjapuxh(String fdjapuxh) {
		this.fdjapuxh = fdjapuxh;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	@Override
	public String toString() {
		return "InstallationList [id=" + id + ", fjdid=" + fjdid + ", cj=" + cj
				+ ", zjh=" + zjh + ", jh=" + jh + ", xlh=" + xlh + ", cjjh="
				+ cjjh + ", ywmc=" + ywmc + ", zwmc=" + zwmc + ", xh=" + xh
				+ ", wz=" + wz + ", pch=" + pch + ", fjzw=" + fjzw + ", zjsl="
				+ zjsl + ", jldw=" + jldw + ", zjjlx=" + zjjlx + ", bjgzjl="
				+ bjgzjl + ", tsn=" + tsn + ", tso=" + tso + ", csn=" + csn
				+ ", cso=" + cso + ", bz=" + bz + ", chucrq=" + chucrq
				+ ", azrq=" + azrq + ", azsj=" + azsj + ", azjldh=" + azjldh
				+ ", azr=" + azr + ", azbz=" + azbz + ", ccrq=" + ccrq
				+ ", ccsj=" + ccsj + ", ccjldh=" + ccjldh + ", ccr=" + ccr
				+ ", ccbz=" + ccbz + ", llklx=" + llklx + ", llkbh=" + llkbh
				+ ", csrq=" + csrq + ", fxsj=" + fxsj + ", fxxh=" + fxxh
				+ ", fdjapusj=" + fdjapusj + ", fdjapuxh=" + fdjapuxh + "]";
	}
}
