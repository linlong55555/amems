package com.eray.thjw.project2.po;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.HCMainData;

/** 
 * @Description 工卡信息导入
 * @CreateTime 2017-12-8 上午10:00:03
 * @CreateBy 雷伟	
 */
public class WorkCardImplList {
	
	/**1.工卡信息wc**/
	private String wcId; //已存在工卡ID
	private String newWcId; //新增工卡ID
	private String wcGkh; //工卡编号
	private String wcBb; //版本
	private String wcJx; //机型
	private String wcIsbj; //RII
	private String wcZjh; //ATA章节号
	private String wcGzbt; //工卡标题
	private String wcGzgs; //工作概述
	private String wcWxxmbh; //维修项目编号
	private String wcRwdh; //任务单号
	private String wcGzdh; //工作单号
	private String wcKzh; //控制号
	private String wcCmph; //CMP号  
	private String wcGzlx; //工作类别
	private String wcGklx; //工卡类型
	private String wcZy; //专业
	private String wcGzzid; //阶段Id
	private String wcGzzbh; //工作组编号
	private String wcJgcf; //间隔/重复 
	private String wcAccess; //接近
	private List<String> wcAccessList; //接近id+盖板号
	private String wcZone; //区域
	private String wcFjzw; //飞机站位
	private String wcJhgsrs; //参考人数
	private String wcJhgsxss; //每人耗时（小时数）
	private String wcSydw; //适用单位
	private String wcYjwj; //依据文件/版本
	private String wcTbsysm; //特别适用性说明
	private String wcPreBbId; //前版本ID


	/**2.器材清单qc**/
	private String qcId;
	private String qcGkh; //工卡编号
	private String qcBb; //版本
	private String qcJx; //机型
	private String qcJh; //件号
	private HCMainData qcHc;//航材信息
	private String qcWcId;//器材关联的工卡ID
	private String qcSl; //需求数量
	private String qcQcdh; //器材代号
	private String qcBxx; //必要性
	private String qcBz; //备注


	/**3.工具清单gj**/
	private String gjId;
	private String gjGkh; //工卡编号
	private String gjBb; //版本
	private String gjJx; //机型
	private String gjJh; //件号
	private HCMainData gjHc;//航材信息
	private String gjWcId;//工具关联的工卡ID
	private String gjSl; //需求数量
	private String gjQcdh; //器材代号
	private String gjBxx; //必要性
	private String gjBz; //备注
	

	/**4.工种工时gs**/
	private String gsId;
	private String gsGkh; //工卡编号
	private String gsBb; //版本
	private String gsJx; //机型
	private String gsWcId;//工种工时关联的工卡ID
	private String gsJdid; //阶段Id
	private String gsJdbm; //阶段编号
	private String gsGzzid; //工作组Id
	private String gsGzzbh; //工作组编号
	private String gsJhgsxss; //工时
	private String gsRw; //任务
	private String gsBz; //备注
	
	public String getWcId() {
		return wcId;
	}
	public void setWcId(String wcId) {
		this.wcId = wcId;
	}
	public String getWcGkh() {
		return wcGkh;
	}
	public void setWcGkh(String wcGkh) {
		this.wcGkh = wcGkh;
	}
	public String getWcBb() {
		return wcBb;
	}
	public void setWcBb(String wcBb) {
		this.wcBb = wcBb;
	}
	public String getWcJx() {
		return wcJx;
	}
	public void setWcJx(String wcJx) {
		this.wcJx = wcJx;
	}
	public String getWcIsbj() {
		return wcIsbj;
	}
	public void setWcIsbj(String wcIsbj) {
		this.wcIsbj = wcIsbj;
	}
	public String getWcZjh() {
		return wcZjh;
	}
	public void setWcZjh(String wcZjh) {
		this.wcZjh = wcZjh;
	}
	public String getWcGzbt() {
		return wcGzbt;
	}
	public void setWcGzbt(String wcGzbt) {
		this.wcGzbt = wcGzbt;
	}
	public String getWcGzgs() {
		return wcGzgs;
	}
	public void setWcGzgs(String wcGzgs) {
		this.wcGzgs = wcGzgs;
	}
	public String getWcWxxmbh() {
		return wcWxxmbh;
	}
	public void setWcWxxmbh(String wcWxxmbh) {
		this.wcWxxmbh = wcWxxmbh;
	}
	public String getWcRwdh() {
		return wcRwdh;
	}
	public void setWcRwdh(String wcRwdh) {
		this.wcRwdh = wcRwdh;
	}
	public String getWcGzdh() {
		return wcGzdh;
	}
	public void setWcGzdh(String wcGzdh) {
		this.wcGzdh = wcGzdh;
	}
	public String getWcKzh() {
		return wcKzh;
	}
	public void setWcKzh(String wcKzh) {
		this.wcKzh = wcKzh;
	}
	public String getWcCmph() {
		return wcCmph;
	}
	public void setWcCmph(String wcCmph) {
		this.wcCmph = wcCmph;
	}
	public String getWcGzlx() {
		return wcGzlx;
	}
	public void setWcGzlx(String wcGzlx) {
		this.wcGzlx = wcGzlx;
	}
	public String getWcGklx() {
		return wcGklx;
	}
	public void setWcGklx(String wcGklx) {
		this.wcGklx = wcGklx;
	}
	public String getWcZy() {
		return wcZy;
	}
	public void setWcZy(String wcZy) {
		this.wcZy = wcZy;
	}
	public String getWcGzzbh() {
		return wcGzzbh;
	}
	public void setWcGzzbh(String wcGzzbh) {
		this.wcGzzbh = wcGzzbh;
	}
	public String getWcJgcf() {
		return wcJgcf;
	}
	public void setWcJgcf(String wcJgcf) {
		this.wcJgcf = wcJgcf;
	}
	public String getWcAccess() {
		return wcAccess;
	}
	public void setWcAccess(String wcAccess) {
		this.wcAccess = wcAccess;
	}
	public String getWcZone() {
		return wcZone;
	}
	public void setWcZone(String wcZone) {
		this.wcZone = wcZone;
	}
	public String getWcFjzw() {
		return wcFjzw;
	}
	public void setWcFjzw(String wcFjzw) {
		this.wcFjzw = wcFjzw;
	}
	public String getWcJhgsrs() {
		return wcJhgsrs;
	}
	public void setWcJhgsrs(String wcJhgsrs) {
		this.wcJhgsrs = wcJhgsrs;
	}
	public String getWcJhgsxss() {
		return wcJhgsxss;
	}
	public void setWcJhgsxss(String wcJhgsxss) {
		this.wcJhgsxss = wcJhgsxss;
	}
	public String getWcSydw() {
		return wcSydw;
	}
	public void setWcSydw(String wcSydw) {
		this.wcSydw = wcSydw;
	}
	public String getWcYjwj() {
		return wcYjwj;
	}
	public void setWcYjwj(String wcYjwj) {
		this.wcYjwj = wcYjwj;
	}
	public String getWcTbsysm() {
		return wcTbsysm;
	}
	public void setWcTbsysm(String wcTbsysm) {
		this.wcTbsysm = wcTbsysm;
	}
	public String getQcId() {
		return qcId;
	}
	public void setQcId(String qcId) {
		this.qcId = qcId;
	}
	public String getQcGkh() {
		return qcGkh;
	}
	public void setQcGkh(String qcGkh) {
		this.qcGkh = qcGkh;
	}
	public String getQcBb() {
		return qcBb;
	}
	public void setQcBb(String qcBb) {
		this.qcBb = qcBb;
	}
	public String getQcJx() {
		return qcJx;
	}
	public void setQcJx(String qcJx) {
		this.qcJx = qcJx;
	}
	public String getQcJh() {
		return qcJh;
	}
	public void setQcJh(String qcJh) {
		this.qcJh = qcJh;
	}
	public HCMainData getQcHc() {
		return qcHc;
	}
	public void setQcHc(HCMainData qcHc) {
		this.qcHc = qcHc;
	}
	public String getQcSl() {
		return qcSl;
	}
	public void setQcSl(String qcSl) {
		this.qcSl = qcSl;
	}
	public String getQcQcdh() {
		return qcQcdh;
	}
	public void setQcQcdh(String qcQcdh) {
		this.qcQcdh = qcQcdh;
	}
	public String getQcBxx() {
		return qcBxx;
	}
	public void setQcBxx(String qcBxx) {
		this.qcBxx = qcBxx;
	}
	public String getQcBz() {
		return qcBz;
	}
	public void setQcBz(String qcBz) {
		this.qcBz = qcBz;
	}
	public String getGjId() {
		return gjId;
	}
	public void setGjId(String gjId) {
		this.gjId = gjId;
	}
	public String getGjGkh() {
		return gjGkh;
	}
	public void setGjGkh(String gjGkh) {
		this.gjGkh = gjGkh;
	}
	public String getGjBb() {
		return gjBb;
	}
	public void setGjBb(String gjBb) {
		this.gjBb = gjBb;
	}
	public String getGjJx() {
		return gjJx;
	}
	public void setGjJx(String gjJx) {
		this.gjJx = gjJx;
	}
	public String getGjJh() {
		return gjJh;
	}
	public void setGjJh(String gjJh) {
		this.gjJh = gjJh;
	}
	public String getGjSl() {
		return gjSl;
	}
	public void setGjSl(String gjSl) {
		this.gjSl = gjSl;
	}
	public String getGjQcdh() {
		return gjQcdh;
	}
	public void setGjQcdh(String gjQcdh) {
		this.gjQcdh = gjQcdh;
	}
	public String getGjBxx() {
		return gjBxx;
	}
	public void setGjBxx(String gjBxx) {
		this.gjBxx = gjBxx;
	}
	public String getGjBz() {
		return gjBz;
	}
	public void setGjBz(String gjBz) {
		this.gjBz = gjBz;
	}
	public String getGsId() {
		return gsId;
	}
	public void setGsId(String gsId) {
		this.gsId = gsId;
	}
	public String getGsGkh() {
		return gsGkh;
	}
	public void setGsGkh(String gsGkh) {
		this.gsGkh = gsGkh;
	}
	public String getGsBb() {
		return gsBb;
	}
	public void setGsBb(String gsBb) {
		this.gsBb = gsBb;
	}
	public String getGsJx() {
		return gsJx;
	}
	public void setGsJx(String gsJx) {
		this.gsJx = gsJx;
	}
	public String getGsJdbm() {
		return gsJdbm;
	}
	public void setGsJdbm(String gsJdbm) {
		this.gsJdbm = gsJdbm;
	}
	public String getGsGzzbh() {
		return gsGzzbh;
	}
	public void setGsGzzbh(String gsGzzbh) {
		this.gsGzzbh = gsGzzbh;
	}
	public String getGsJhgsxss() {
		return gsJhgsxss;
	}
	public void setGsJhgsxss(String gsJhgsxss) {
		this.gsJhgsxss = gsJhgsxss;
	}
	public String getGsRw() {
		return gsRw;
	}
	public void setGsRw(String gsRw) {
		this.gsRw = gsRw;
	}
	public String getGsBz() {
		return gsBz;
	}
	public void setGsBz(String gsBz) {
		this.gsBz = gsBz;
	}
	public String getQcWcId() {
		return qcWcId;
	}
	public void setQcWcId(String qcWcId) {
		this.qcWcId = qcWcId;
	}
	public HCMainData getGjHc() {
		return gjHc;
	}
	public void setGjHc(HCMainData gjHc) {
		this.gjHc = gjHc;
	}
	public String getGjWcId() {
		return gjWcId;
	}
	public void setGjWcId(String gjWcId) {
		this.gjWcId = gjWcId;
	}
	public String getWcGzzid() {
		return wcGzzid;
	}
	public void setWcGzzid(String wcGzzid) {
		this.wcGzzid = wcGzzid;
	}
	public String getGsJdid() {
		return gsJdid;
	}
	public void setGsJdid(String gsJdid) {
		this.gsJdid = gsJdid;
	}
	public String getGsGzzid() {
		return gsGzzid;
	}
	public void setGsGzzid(String gsGzzid) {
		this.gsGzzid = gsGzzid;
	}
	public String getWcPreBbId() {
		return wcPreBbId;
	}
	public void setWcPreBbId(String wcPreBbId) {
		this.wcPreBbId = wcPreBbId;
	}
	public String getGsWcId() {
		return gsWcId;
	}
	public void setGsWcId(String gsWcId) {
		this.gsWcId = gsWcId;
	}
	public List<String> getWcAccessList() {
		return wcAccessList;
	}
	public void setWcAccessList(List<String> wcAccessList) {
		this.wcAccessList = wcAccessList;
	}
	public String getNewWcId() {
		return newWcId;
	}
	public void setNewWcId(String newWcId) {
		this.newWcId = newWcId;
	}
	@Override
	public String toString() {
		return "WorkCardImplList [wcId=" + wcId + ", newWcId=" + newWcId
				+ ", wcGkh=" + wcGkh + ", wcBb=" + wcBb + ", wcJx=" + wcJx
				+ ", wcIsbj=" + wcIsbj + ", wcZjh=" + wcZjh + ", wcGzbt="
				+ wcGzbt + ", wcGzgs=" + wcGzgs + ", wcWxxmbh=" + wcWxxmbh
				+ ", wcRwdh=" + wcRwdh + ", wcGzdh=" + wcGzdh + ", wcKzh="
				+ wcKzh + ", wcCmph=" + wcCmph + ", wcGzlx=" + wcGzlx
				+ ", wcGklx=" + wcGklx + ", wcZy=" + wcZy + ", wcGzzid="
				+ wcGzzid + ", wcGzzbh=" + wcGzzbh + ", wcJgcf=" + wcJgcf
				+ ", wcAccess=" + wcAccess + ", wcAccessList=" + wcAccessList
				+ ", wcZone=" + wcZone + ", wcFjzw=" + wcFjzw + ", wcJhgsrs="
				+ wcJhgsrs + ", wcJhgsxss=" + wcJhgsxss + ", wcSydw=" + wcSydw
				+ ", wcYjwj=" + wcYjwj + ", wcTbsysm=" + wcTbsysm
				+ ", wcPreBbId=" + wcPreBbId + ", qcId=" + qcId + ", qcGkh="
				+ qcGkh + ", qcBb=" + qcBb + ", qcJx=" + qcJx + ", qcJh="
				+ qcJh + ", qcHc=" + qcHc + ", qcWcId=" + qcWcId + ", qcSl="
				+ qcSl + ", qcQcdh=" + qcQcdh + ", qcBxx=" + qcBxx + ", qcBz="
				+ qcBz + ", gjId=" + gjId + ", gjGkh=" + gjGkh + ", gjBb="
				+ gjBb + ", gjJx=" + gjJx + ", gjJh=" + gjJh + ", gjHc=" + gjHc
				+ ", gjWcId=" + gjWcId + ", gjSl=" + gjSl + ", gjQcdh="
				+ gjQcdh + ", gjBxx=" + gjBxx + ", gjBz=" + gjBz + ", gsId="
				+ gsId + ", gsGkh=" + gsGkh + ", gsBb=" + gsBb + ", gsJx="
				+ gsJx + ", gsWcId=" + gsWcId + ", gsJdid=" + gsJdid
				+ ", gsJdbm=" + gsJdbm + ", gsGzzid=" + gsGzzid + ", gsGzzbh="
				+ gsGzzbh + ", gsJhgsxss=" + gsJhgsxss + ", gsRw=" + gsRw
				+ ", gsBz=" + gsBz + "]";
	}

}
