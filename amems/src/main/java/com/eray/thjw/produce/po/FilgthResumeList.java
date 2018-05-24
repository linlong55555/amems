package com.eray.thjw.produce.po;

import com.eray.thjw.po.BizEntity;

/** 
 * @Description 飞行履历导入Excel
 * @CreateTime 2017-11-28 下午4:56:19
 * @CreateBy 雷伟	
 */
public class FilgthResumeList extends BizEntity{
	private static final long serialVersionUID = 1L;
	
	private String id; //基础id
	private String fjzch; //飞机注册号
	private String fxrq; //飞行日期
	private String jlbym; //飞行（记录）本页码
	private String hbh; //航班号
	private String fxrwlx; //(飞行)任务类型
	private String qfz; //起飞站
	private String zlz; //着陆站
	private String kcsj; //开车
	private String qfsj; //起飞
	private String ldsj; //落地
	private String tcsj; //停车
	private String sysjFz; //使用
	private String fxsjFz; //FH 飞行时间
	private String fxxh; //FC 飞行循环
	private String lxqlcs; //连续起落次数
	private String f1SjFz; //1#发时间-分钟
	private String f1Xh; //1#发循环
	private String f1Hytjl; //1#发滑油添加量
	private String f2SjFz; //2#发时间-分钟
	private String f2Xh; //2#发循环
	private String f2Hytjl; //2#发滑油添加量
	private String f3SjFz; //3#发时间-分钟
	private String f3Xh; //3#发循环
	private String f3Hytjl; //3#发滑油添加量
	private String f4SjFz; //4#发时间-分钟
	private String f4Xh; //4#发循环
	private String f4Hytjl; //4#发滑油添加量
	private String apuSjFz; //APU时间-分钟
	private String apuXh; //APU循环
	private String apuHytjl; //APU滑油添加量
	private String idgtksj; //IDG脱开时间
	private String yyy; //液压油
	private String ryCyl; //燃油存油量
	private String ryJyl; //燃油加油量
	private String rySyyl; //燃油剩余油量
	private String jz; //机长
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFjzch() {
		return fjzch;
	}
	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}
	public String getFxrq() {
		return fxrq;
	}
	public void setFxrq(String fxrq) {
		this.fxrq = fxrq;
	}
	public String getJlbym() {
		return jlbym;
	}
	public void setJlbym(String jlbym) {
		this.jlbym = jlbym;
	}
	public String getHbh() {
		return hbh;
	}
	public void setHbh(String hbh) {
		this.hbh = hbh;
	}
	public String getFxrwlx() {
		return fxrwlx;
	}
	public void setFxrwlx(String fxrwlx) {
		this.fxrwlx = fxrwlx;
	}
	public String getQfz() {
		return qfz;
	}
	public void setQfz(String qfz) {
		this.qfz = qfz;
	}
	public String getZlz() {
		return zlz;
	}
	public void setZlz(String zlz) {
		this.zlz = zlz;
	}
	public String getKcsj() {
		return kcsj;
	}
	public void setKcsj(String kcsj) {
		this.kcsj = kcsj;
	}
	public String getQfsj() {
		return qfsj;
	}
	public void setQfsj(String qfsj) {
		this.qfsj = qfsj;
	}
	public String getLdsj() {
		return ldsj;
	}
	public void setLdsj(String ldsj) {
		this.ldsj = ldsj;
	}
	public String getTcsj() {
		return tcsj;
	}
	public void setTcsj(String tcsj) {
		this.tcsj = tcsj;
	}
	public String getSysjFz() {
		return sysjFz;
	}
	public void setSysjFz(String sysjFz) {
		this.sysjFz = sysjFz;
	}
	public String getFxsjFz() {
		return fxsjFz;
	}
	public void setFxsjFz(String fxsjFz) {
		this.fxsjFz = fxsjFz;
	}
	public String getFxxh() {
		return fxxh;
	}
	public void setFxxh(String fxxh) {
		this.fxxh = fxxh;
	}
	public String getLxqlcs() {
		return lxqlcs;
	}
	public void setLxqlcs(String lxqlcs) {
		this.lxqlcs = lxqlcs;
	}
	public String getF1SjFz() {
		return f1SjFz;
	}
	public void setF1SjFz(String f1SjFz) {
		this.f1SjFz = f1SjFz;
	}
	public String getF1Xh() {
		return f1Xh;
	}
	public void setF1Xh(String f1Xh) {
		this.f1Xh = f1Xh;
	}
	public String getF1Hytjl() {
		return f1Hytjl;
	}
	public void setF1Hytjl(String f1Hytjl) {
		this.f1Hytjl = f1Hytjl;
	}
	public String getF2SjFz() {
		return f2SjFz;
	}
	public void setF2SjFz(String f2SjFz) {
		this.f2SjFz = f2SjFz;
	}
	public String getF2Xh() {
		return f2Xh;
	}
	public void setF2Xh(String f2Xh) {
		this.f2Xh = f2Xh;
	}
	public String getF2Hytjl() {
		return f2Hytjl;
	}
	public void setF2Hytjl(String f2Hytjl) {
		this.f2Hytjl = f2Hytjl;
	}
	public String getF3SjFz() {
		return f3SjFz;
	}
	public void setF3SjFz(String f3SjFz) {
		this.f3SjFz = f3SjFz;
	}
	public String getF3Xh() {
		return f3Xh;
	}
	public void setF3Xh(String f3Xh) {
		this.f3Xh = f3Xh;
	}
	public String getF3Hytjl() {
		return f3Hytjl;
	}
	public void setF3Hytjl(String f3Hytjl) {
		this.f3Hytjl = f3Hytjl;
	}
	public String getF4SjFz() {
		return f4SjFz;
	}
	public void setF4SjFz(String f4SjFz) {
		this.f4SjFz = f4SjFz;
	}
	public String getF4Xh() {
		return f4Xh;
	}
	public void setF4Xh(String f4Xh) {
		this.f4Xh = f4Xh;
	}
	public String getF4Hytjl() {
		return f4Hytjl;
	}
	public void setF4Hytjl(String f4Hytjl) {
		this.f4Hytjl = f4Hytjl;
	}
	public String getApuSjFz() {
		return apuSjFz;
	}
	public void setApuSjFz(String apuSjFz) {
		this.apuSjFz = apuSjFz;
	}
	public String getApuXh() {
		return apuXh;
	}
	public void setApuXh(String apuXh) {
		this.apuXh = apuXh;
	}
	public String getApuHytjl() {
		return apuHytjl;
	}
	public void setApuHytjl(String apuHytjl) {
		this.apuHytjl = apuHytjl;
	}
	public String getIdgtksj() {
		return idgtksj;
	}
	public void setIdgtksj(String idgtksj) {
		this.idgtksj = idgtksj;
	}
	public String getYyy() {
		return yyy;
	}
	public void setYyy(String yyy) {
		this.yyy = yyy;
	}
	public String getRyCyl() {
		return ryCyl;
	}
	public void setRyCyl(String ryCyl) {
		this.ryCyl = ryCyl;
	}
	public String getRyJyl() {
		return ryJyl;
	}
	public void setRyJyl(String ryJyl) {
		this.ryJyl = ryJyl;
	}
	public String getRySyyl() {
		return rySyyl;
	}
	public void setRySyyl(String rySyyl) {
		this.rySyyl = rySyyl;
	}
	public String getJz() {
		return jz;
	}
	public void setJz(String jz) {
		this.jz = jz;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "FilgthResumeList [id=" + id + ", fjzch=" + fjzch + ", fxrq="
				+ fxrq + ", jlbym=" + jlbym + ", hbh=" + hbh + ", fxrwlx="
				+ fxrwlx + ", qfz=" + qfz + ", zlz=" + zlz + ", kcsj=" + kcsj
				+ ", qfsj=" + qfsj + ", ldsj=" + ldsj + ", tcsj=" + tcsj
				+ ", sysjFz=" + sysjFz + ", fxsjFz=" + fxsjFz + ", fxxh="
				+ fxxh + ", lxqlcs=" + lxqlcs + ", f1SjFz=" + f1SjFz
				+ ", f1Xh=" + f1Xh + ", f1Hytjl=" + f1Hytjl + ", f2SjFz="
				+ f2SjFz + ", f2Xh=" + f2Xh + ", f2Hytjl=" + f2Hytjl
				+ ", f3SjFz=" + f3SjFz + ", f3Xh=" + f3Xh + ", f3Hytjl="
				+ f3Hytjl + ", f4SjFz=" + f4SjFz + ", f4Xh=" + f4Xh
				+ ", f4Hytjl=" + f4Hytjl + ", apuSjFz=" + apuSjFz + ", apuXh="
				+ apuXh + ", apuHytjl=" + apuHytjl + ", idgtksj=" + idgtksj
				+ ", yyy=" + yyy + ", ryCyl=" + ryCyl + ", ryJyl=" + ryJyl
				+ ", rySyyl=" + rySyyl + ", jz=" + jz + ", pagination="
				+ pagination + ", getId()=" + getId() + ", getFjzch()="
				+ getFjzch() + ", getFxrq()=" + getFxrq() + ", getJlbym()="
				+ getJlbym() + ", getHbh()=" + getHbh() + ", getFxrwlx()="
				+ getFxrwlx() + ", getQfz()=" + getQfz() + ", getZlz()="
				+ getZlz() + ", getKcsj()=" + getKcsj() + ", getQfsj()="
				+ getQfsj() + ", getLdsj()=" + getLdsj() + ", getTcsj()="
				+ getTcsj() + ", getSysjFz()=" + getSysjFz() + ", getFxsjFz()="
				+ getFxsjFz() + ", getFxxh()=" + getFxxh() + ", getLxqlcs()="
				+ getLxqlcs() + ", getF1SjFz()=" + getF1SjFz() + ", getF1Xh()="
				+ getF1Xh() + ", getF1Hytjl()=" + getF1Hytjl()
				+ ", getF2SjFz()=" + getF2SjFz() + ", getF2Xh()=" + getF2Xh()
				+ ", getF2Hytjl()=" + getF2Hytjl() + ", getF3SjFz()="
				+ getF3SjFz() + ", getF3Xh()=" + getF3Xh() + ", getF3Hytjl()="
				+ getF3Hytjl() + ", getF4SjFz()=" + getF4SjFz()
				+ ", getF4Xh()=" + getF4Xh() + ", getF4Hytjl()=" + getF4Hytjl()
				+ ", getApuSjFz()=" + getApuSjFz() + ", getApuXh()="
				+ getApuXh() + ", getApuHytjl()=" + getApuHytjl()
				+ ", getIdgtksj()=" + getIdgtksj() + ", getYyy()=" + getYyy()
				+ ", getRyCyl()=" + getRyCyl() + ", getRyJyl()=" + getRyJyl()
				+ ", getRySyyl()=" + getRySyyl() + ", getJz()=" + getJz()
				+ ", getZdrid()=" + getZdrid() + ", getZdsj()=" + getZdsj()
				+ ", getDprtcode()=" + getDprtcode() + ", getKeyword()="
				+ getKeyword() + ", getZdbmid()=" + getZdbmid()
				+ ", getZdrrealname()=" + getZdrrealname() + ", getZdr()="
				+ getZdr() + ", getAttachments()=" + getAttachments()
				+ ", getDelIds()=" + getDelIds() + ", getDprtname()="
				+ getDprtname() + ", getDprtcodes()=" + getDprtcodes()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
