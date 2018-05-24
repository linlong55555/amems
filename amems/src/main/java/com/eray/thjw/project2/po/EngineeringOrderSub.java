package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_g2_01000 EO-附加信息
 * @CreateTime 2017-8-20 上午9:33:58
 * @CreateBy 雷伟
 */
public class EngineeringOrderSub extends BizEntity {
    private String id;

    private String mainid;

    private BigDecimal scjszy;

    private String ywjsyfw;

    private String gzgs;

    private String fhx;

    private String wxgzdxid;

    private String wxgzdx;

    private String wxgzbs;

    private String wxgzbsQt;

    private BigDecimal isZzphbh;

    private String mtc;

    private String wtc;

    private String arm;

    private String bhnr;

    private String yxsc;

    private BigDecimal qcjg;

    private String qcjgdw;

    private BigDecimal bfqczb;

    private String bfqczbtzd;

    private BigDecimal zztjbs;

    private String zztj;

    private String gcjy;

    private String scap;

    private String yyjms;

    private String clcs;

    private String gbjlBc;

    private String gbjlYqbbgx;

    private String gbjlGbyy;

    private BigDecimal isYxcbw;

    private String sycjzrjqd;

    private String dqfzsj;

    private String wxxmxgx;

    private String fkyq;

    private String hcxqsqd;

    private String yyljcl;

    private String hhxx;

    private String qt;

    private BigDecimal isTsgjsb;

    private BigDecimal isSpQc;

    private String spQcsm;

    private BigDecimal isSpRg;

    private String spRgsm;

    private Date spqx;

    private String spJgxx;

    private BigDecimal isDqfzsj;
    
    /**部件信息*/
    private HCMainData hcMainData;
    
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

	public BigDecimal getScjszy() {
		return scjszy;
	}

	public void setScjszy(BigDecimal scjszy) {
		this.scjszy = scjszy;
	}

	public String getYwjsyfw() {
		return ywjsyfw;
	}

	public void setYwjsyfw(String ywjsyfw) {
		this.ywjsyfw = ywjsyfw;
	}

	public String getGzgs() {
		return gzgs;
	}

	public void setGzgs(String gzgs) {
		this.gzgs = gzgs;
	}

	public String getFhx() {
		return fhx;
	}

	public void setFhx(String fhx) {
		this.fhx = fhx;
	}

	public String getWxgzdxid() {
		return wxgzdxid;
	}

	public void setWxgzdxid(String wxgzdxid) {
		this.wxgzdxid = wxgzdxid;
	}

	public String getWxgzdx() {
		return wxgzdx;
	}

	public void setWxgzdx(String wxgzdx) {
		this.wxgzdx = wxgzdx;
	}

	public String getWxgzbs() {
		return wxgzbs;
	}

	public void setWxgzbs(String wxgzbs) {
		this.wxgzbs = wxgzbs;
	}

	public String getWxgzbsQt() {
		return wxgzbsQt;
	}

	public void setWxgzbsQt(String wxgzbsQt) {
		this.wxgzbsQt = wxgzbsQt;
	}

	public BigDecimal getIsZzphbh() {
		return isZzphbh;
	}

	public void setIsZzphbh(BigDecimal isZzphbh) {
		this.isZzphbh = isZzphbh;
	}

	public String getMtc() {
		return mtc;
	}

	public void setMtc(String mtc) {
		this.mtc = mtc;
	}

	public String getWtc() {
		return wtc;
	}

	public void setWtc(String wtc) {
		this.wtc = wtc;
	}

	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
	}

	public String getBhnr() {
		return bhnr;
	}

	public void setBhnr(String bhnr) {
		this.bhnr = bhnr;
	}

	public String getYxsc() {
		return yxsc;
	}

	public void setYxsc(String yxsc) {
		this.yxsc = yxsc;
	}

	public BigDecimal getQcjg() {
		return qcjg;
	}

	public void setQcjg(BigDecimal qcjg) {
		this.qcjg = qcjg;
	}

	public String getQcjgdw() {
		return qcjgdw;
	}

	public void setQcjgdw(String qcjgdw) {
		this.qcjgdw = qcjgdw;
	}

	public BigDecimal getBfqczb() {
		return bfqczb;
	}

	public void setBfqczb(BigDecimal bfqczb) {
		this.bfqczb = bfqczb;
	}

	public String getBfqczbtzd() {
		return bfqczbtzd;
	}

	public void setBfqczbtzd(String bfqczbtzd) {
		this.bfqczbtzd = bfqczbtzd;
	}

	public BigDecimal getZztjbs() {
		return zztjbs;
	}

	public void setZztjbs(BigDecimal zztjbs) {
		this.zztjbs = zztjbs;
	}

	public String getZztj() {
		return zztj;
	}

	public void setZztj(String zztj) {
		this.zztj = zztj;
	}

	public String getGcjy() {
		return gcjy;
	}

	public void setGcjy(String gcjy) {
		this.gcjy = gcjy;
	}

	public String getScap() {
		return scap;
	}

	public void setScap(String scap) {
		this.scap = scap;
	}

	public String getYyjms() {
		return yyjms;
	}

	public void setYyjms(String yyjms) {
		this.yyjms = yyjms;
	}

	public String getClcs() {
		return clcs;
	}

	public void setClcs(String clcs) {
		this.clcs = clcs;
	}

	public String getGbjlBc() {
		return gbjlBc;
	}

	public void setGbjlBc(String gbjlBc) {
		this.gbjlBc = gbjlBc;
	}

	public String getGbjlYqbbgx() {
		return gbjlYqbbgx;
	}

	public void setGbjlYqbbgx(String gbjlYqbbgx) {
		this.gbjlYqbbgx = gbjlYqbbgx;
	}

	public String getGbjlGbyy() {
		return gbjlGbyy;
	}

	public void setGbjlGbyy(String gbjlGbyy) {
		this.gbjlGbyy = gbjlGbyy;
	}

	public BigDecimal getIsYxcbw() {
		return isYxcbw;
	}

	public void setIsYxcbw(BigDecimal isYxcbw) {
		this.isYxcbw = isYxcbw;
	}

	public String getSycjzrjqd() {
		return sycjzrjqd;
	}

	public void setSycjzrjqd(String sycjzrjqd) {
		this.sycjzrjqd = sycjzrjqd;
	}

	public String getDqfzsj() {
		return dqfzsj;
	}

	public void setDqfzsj(String dqfzsj) {
		this.dqfzsj = dqfzsj;
	}

	public String getWxxmxgx() {
		return wxxmxgx;
	}

	public void setWxxmxgx(String wxxmxgx) {
		this.wxxmxgx = wxxmxgx;
	}

	public String getFkyq() {
		return fkyq;
	}

	public void setFkyq(String fkyq) {
		this.fkyq = fkyq;
	}

	public String getHcxqsqd() {
		return hcxqsqd;
	}

	public void setHcxqsqd(String hcxqsqd) {
		this.hcxqsqd = hcxqsqd;
	}

	public String getYyljcl() {
		return yyljcl;
	}

	public void setYyljcl(String yyljcl) {
		this.yyljcl = yyljcl;
	}

	public String getHhxx() {
		return hhxx;
	}

	public void setHhxx(String hhxx) {
		this.hhxx = hhxx;
	}

	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}

	public BigDecimal getIsTsgjsb() {
		return isTsgjsb;
	}

	public void setIsTsgjsb(BigDecimal isTsgjsb) {
		this.isTsgjsb = isTsgjsb;
	}

	public BigDecimal getIsSpQc() {
		return isSpQc;
	}

	public void setIsSpQc(BigDecimal isSpQc) {
		this.isSpQc = isSpQc;
	}

	public String getSpQcsm() {
		return spQcsm;
	}

	public void setSpQcsm(String spQcsm) {
		this.spQcsm = spQcsm;
	}

	public BigDecimal getIsSpRg() {
		return isSpRg;
	}

	public void setIsSpRg(BigDecimal isSpRg) {
		this.isSpRg = isSpRg;
	}

	public String getSpRgsm() {
		return spRgsm;
	}

	public void setSpRgsm(String spRgsm) {
		this.spRgsm = spRgsm;
	}

	public Date getSpqx() {
		return spqx;
	}

	public void setSpqx(Date spqx) {
		this.spqx = spqx;
	}

	public String getSpJgxx() {
		return spJgxx;
	}

	public void setSpJgxx(String spJgxx) {
		this.spJgxx = spJgxx;
	}

	public BigDecimal getIsDqfzsj() {
		return isDqfzsj;
	}

	public void setIsDqfzsj(BigDecimal isDqfzsj) {
		this.isDqfzsj = isDqfzsj;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	@Override
	public String toString() {
		return "EngineeringOrderSub [id=" + id + ", mainid=" + mainid
				+ ", scjszy=" + scjszy + ", ywjsyfw=" + ywjsyfw + ", gzgs="
				+ gzgs + ", fhx=" + fhx + ", wxgzdxid=" + wxgzdxid
				+ ", wxgzdx=" + wxgzdx + ", wxgzbs=" + wxgzbs + ", wxgzbsQt="
				+ wxgzbsQt + ", isZzphbh=" + isZzphbh + ", mtc=" + mtc
				+ ", wtc=" + wtc + ", arm=" + arm + ", bhnr=" + bhnr
				+ ", yxsc=" + yxsc + ", qcjg=" + qcjg + ", qcjgdw=" + qcjgdw
				+ ", bfqczb=" + bfqczb + ", bfqczbtzd=" + bfqczbtzd
				+ ", zztjbs=" + zztjbs + ", zztj=" + zztj + ", gcjy=" + gcjy
				+ ", scap=" + scap + ", yyjms=" + yyjms + ", clcs=" + clcs
				+ ", gbjlBc=" + gbjlBc + ", gbjlYqbbgx=" + gbjlYqbbgx
				+ ", gbjlGbyy=" + gbjlGbyy + ", isYxcbw=" + isYxcbw
				+ ", sycjzrjqd=" + sycjzrjqd + ", dqfzsj=" + dqfzsj
				+ ", wxxmxgx=" + wxxmxgx + ", fkyq=" + fkyq + ", hcxqsqd="
				+ hcxqsqd + ", yyljcl=" + yyljcl + ", hhxx=" + hhxx + ", qt="
				+ qt + ", isTsgjsb=" + isTsgjsb + ", isSpQc=" + isSpQc
				+ ", spQcsm=" + spQcsm + ", isSpRg=" + isSpRg + ", spRgsm="
				+ spRgsm + ", spqx=" + spqx + ", spJgxx=" + spJgxx
				+ ", isDqfzsj=" + isDqfzsj
				+ ", hcMainData=" + hcMainData + "]";
	}
}