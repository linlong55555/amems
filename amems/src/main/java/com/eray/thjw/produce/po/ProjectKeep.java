package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.MaterialTool;

/**
 * 
 * @Description b_s2_011 项目保留
 * @CreateTime 2017年10月10日 下午2:44:03
 * @CreateBy 林龙
 */
public class ProjectKeep extends BizEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private Integer gdblzt;

    private String bldh;

    private String fjzch;

    private String sqrbmid;

    private String sqrid;

    private Date sqrq;

    private Integer bs145;

    private String gdid;

    private Integer fjbs1;

    private Integer fjbs2;

    private Integer fjbs3;

    private String dxgxtyq;

    private String blqjcqcs;

    private Integer fxsj;

    private Integer fxxh;

    private Integer fjbs4;

    private Integer fjbs5;

    private Integer fjbs6;

    private Date blqx;

    private String gzblxz;

    private String jhwcdd;

    private String pgr;

    private Date pgrq;

    private Integer fjbs7;

    private Integer fjbs8;

    private Integer fjbs9;

    private String jspgr;

    private Date jspgrq;

    private String shr; //审核人 -mybites不识别 

    private String shr1; //审核人  虚拟字段
    
    private Date shrq;

    private String shyj;

    private String pzr;

    private Date pzrq;

    private String pzyj;

    private String gzz;

    private Date gzrq;

    private String jcz;

    private Date jcrq;

    private String gbrbmid;

    private String gbrid;

    private Date gbsj;

    private String gbyy;
    


   private User whr; //维护人
    
    private Department dprt; //组织机构
    
    private List<Attachment> attachmentList; //d_011 附件表List
    
    private List<MaterialTool> materialToolList; //器材/工具list集合

	public List<MaterialTool> getMaterialToolList() {
		return materialToolList;
	}

	public void setMaterialToolList(List<MaterialTool> materialToolList) {
		this.materialToolList = materialToolList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getWhdwid() {
		return whdwid;
	}

	public void setWhdwid(String whdwid) {
		this.whdwid = whdwid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getGdblzt() {
		return gdblzt;
	}

	public void setGdblzt(Integer gdblzt) {
		this.gdblzt = gdblzt;
	}

	public String getBldh() {
		return bldh;
	}

	public void setBldh(String bldh) {
		this.bldh = bldh;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getSqrbmid() {
		return sqrbmid;
	}

	public void setSqrbmid(String sqrbmid) {
		this.sqrbmid = sqrbmid;
	}

	public String getSqrid() {
		return sqrid;
	}

	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}

	public Date getSqrq() {
		return sqrq;
	}

	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}

	public Integer getBs145() {
		return bs145;
	}

	public void setBs145(Integer bs145) {
		this.bs145 = bs145;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public Integer getFjbs1() {
		return fjbs1;
	}

	public void setFjbs1(Integer fjbs1) {
		this.fjbs1 = fjbs1;
	}

	public Integer getFjbs2() {
		return fjbs2;
	}

	public void setFjbs2(Integer fjbs2) {
		this.fjbs2 = fjbs2;
	}

	public Integer getFjbs3() {
		return fjbs3;
	}

	public void setFjbs3(Integer fjbs3) {
		this.fjbs3 = fjbs3;
	}

	public String getDxgxtyq() {
		return dxgxtyq;
	}

	public void setDxgxtyq(String dxgxtyq) {
		this.dxgxtyq = dxgxtyq;
	}

	public String getBlqjcqcs() {
		return blqjcqcs;
	}

	public void setBlqjcqcs(String blqjcqcs) {
		this.blqjcqcs = blqjcqcs;
	}

	public Integer getFxsj() {
		return fxsj;
	}

	public void setFxsj(Integer fxsj) {
		this.fxsj = fxsj;
	}

	public Integer getFxxh() {
		return fxxh;
	}

	public void setFxxh(Integer fxxh) {
		this.fxxh = fxxh;
	}

	public Integer getFjbs4() {
		return fjbs4;
	}

	public void setFjbs4(Integer fjbs4) {
		this.fjbs4 = fjbs4;
	}

	public Integer getFjbs5() {
		return fjbs5;
	}

	public void setFjbs5(Integer fjbs5) {
		this.fjbs5 = fjbs5;
	}

	public Integer getFjbs6() {
		return fjbs6;
	}

	public void setFjbs6(Integer fjbs6) {
		this.fjbs6 = fjbs6;
	}

	public Date getBlqx() {
		return blqx;
	}

	public void setBlqx(Date blqx) {
		this.blqx = blqx;
	}

	public String getGzblxz() {
		return gzblxz;
	}

	public void setGzblxz(String gzblxz) {
		this.gzblxz = gzblxz;
	}

	public String getJhwcdd() {
		return jhwcdd;
	}

	public void setJhwcdd(String jhwcdd) {
		this.jhwcdd = jhwcdd;
	}

	public String getPgr() {
		return pgr;
	}

	public void setPgr(String pgr) {
		this.pgr = pgr;
	}

	public Date getPgrq() {
		return pgrq;
	}

	public void setPgrq(Date pgrq) {
		this.pgrq = pgrq;
	}

	public Integer getFjbs7() {
		return fjbs7;
	}

	public void setFjbs7(Integer fjbs7) {
		this.fjbs7 = fjbs7;
	}

	public Integer getFjbs8() {
		return fjbs8;
	}

	public void setFjbs8(Integer fjbs8) {
		this.fjbs8 = fjbs8;
	}

	public Integer getFjbs9() {
		return fjbs9;
	}

	public void setFjbs9(Integer fjbs9) {
		this.fjbs9 = fjbs9;
	}

	public String getJspgr() {
		return jspgr;
	}

	public void setJspgr(String jspgr) {
		this.jspgr = jspgr;
	}

	public Date getJspgrq() {
		return jspgrq;
	}

	public void setJspgrq(Date jspgrq) {
		this.jspgrq = jspgrq;
	}

	public String getShr() {
		return shr;
	}
	
	public void setShr(String shr) {
		this.shr = shr;
	}
	public String getShr1() {
		return shr1;
	}

	public void setShr1(String shr1) {
		this.shr1 = shr;
	}

	public Date getShrq() {
		return shrq;
	}

	public void setShrq(Date shrq) {
		this.shrq = shrq;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getPzr() {
		return pzr;
	}

	public void setPzr(String pzr) {
		this.pzr = pzr;
	}

	public Date getPzrq() {
		return pzrq;
	}

	public void setPzrq(Date pzrq) {
		this.pzrq = pzrq;
	}

	public String getPzyj() {
		return pzyj;
	}

	public void setPzyj(String pzyj) {
		this.pzyj = pzyj;
	}

	public String getGzz() {
		return gzz;
	}

	public void setGzz(String gzz) {
		this.gzz = gzz;
	}

	public Date getGzrq() {
		return gzrq;
	}

	public void setGzrq(Date gzrq) {
		this.gzrq = gzrq;
	}

	public String getJcz() {
		return jcz;
	}

	public void setJcz(String jcz) {
		this.jcz = jcz;
	}

	public Date getJcrq() {
		return jcrq;
	}

	public void setJcrq(Date jcrq) {
		this.jcrq = jcrq;
	}

	public String getGbrbmid() {
		return gbrbmid;
	}

	public void setGbrbmid(String gbrbmid) {
		this.gbrbmid = gbrbmid;
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
	}

	public Date getGbsj() {
		return gbsj;
	}

	public void setGbsj(Date gbsj) {
		this.gbsj = gbsj;
	}

	public String getGbyy() {
		return gbyy;
	}

	public void setGbyy(String gbyy) {
		this.gbyy = gbyy;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
   
}