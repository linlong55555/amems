package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import enu.ordersource.OrderSourceEnum;

public class Instruction extends BizEntity{
	
	
    private String id;		//主键id

    private String jszlh;	//技术指令号

    private String jx;		//机型

    private String ckzl;	//参考资料

    private String zhut;	//主题

    private String nr;		//内容

    private String bz;		//备注

    private String zdbmid;	//制单部门id

    private String zdrid;	//制单人id

    private Date zdsj;		//制单时间

    private Integer zt;		//状态
    
    private String ztText;	//状态（中文）

    private Integer dyzt;		//打印状态

    private String dprtcode;	//机构代码
    
    private List<OrderSource> orderSourceList;	//关联文件来源表
    
    private String pgdh;		//评估单号
    
    private String realname;	//真实姓名
    
    private String username;	//真实姓名

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;

    private String shbmid;

    private String shyj;

    private String shrid;

    private Date shsj;

    private String pfbmid;

    private String pfyj;

    private String pfrid;

    private Date pfsj;

    private String jsrid;
    
    private String shrrealname;
    
    private String pfrrealname;
    
    private List<InstructionContent> instructionContentList;

    private List<String> oldInstructionContentIds;
    
    private String fcrid;
    
    private Date fcrq;
    
    private String zxsx;
    
    private String bflyyj;
    
    private User shr_user;
    
    private User pfr_user;

    private User zdr_user;

    private User jsr_user;
    
    private User zdjsr_user;
    
    private User jsrUser;
    
    private User fcrUser;
    
    private Department jg_dprt;
    
    private String Pgjg;
    
    private String bb;
    
    private Integer jszt;
    
    private List<OrderAttachment> orderAttachmentList;

    private List<String > oldOrderAttachmentIds;
    
    
    

	public User getJsr_user() {
		return jsr_user;
	}

	public void setJsr_user(User jsr_user) {
		this.jsr_user = jsr_user;
	}

	public User getZdjsr_user() {
		return zdjsr_user;
	}

	public void setZdjsr_user(User zdjsr_user) {
		this.zdjsr_user = zdjsr_user;
	}

	public List<OrderAttachment> getOrderAttachmentList() {
		return orderAttachmentList;
	}

	public void setOrderAttachmentList(List<OrderAttachment> orderAttachmentList) {
		this.orderAttachmentList = orderAttachmentList;
	}

	public List<String> getOldOrderAttachmentIds() {
		return oldOrderAttachmentIds;
	}

	public void setOldOrderAttachmentIds(List<String> oldOrderAttachmentIds) {
		this.oldOrderAttachmentIds = oldOrderAttachmentIds;
	}

	public String getZtText() {
		return zt==null?"":OrderSourceEnum.getName(zt);
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public Integer getJszt() {
		return jszt;
	}

	public void setJszt(Integer jszt) {
		this.jszt = jszt;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public User getShr_user() {
		return shr_user;
	}

	public void setShr_user(User shr_user) {
		this.shr_user = shr_user;
	}

	public User getPfr_user() {
		return pfr_user;
	}

	public void setPfr_user(User pfr_user) {
		this.pfr_user = pfr_user;
	}

	public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public String getPgjg() {
		return Pgjg;
	}

	public void setPgjg(String pgjg) {
		Pgjg = pgjg;
	}

	public List<String> getOldInstructionContentIds() {
		return oldInstructionContentIds;
	}

	public void setOldInstructionContentIds(List<String> oldInstructionContentIds) {
		this.oldInstructionContentIds = oldInstructionContentIds;
	}

	public String getFcrid() {
		return fcrid;
	}

	public void setFcrid(String fcrid) {
		this.fcrid = fcrid;
	}

	public Date getFcrq() {
		return fcrq;
	}

	public void setFcrq(Date fcrq) {
		this.fcrq = fcrq;
	}

	public String getZxsx() {
		return zxsx;
	}

	public void setZxsx(String zxsx) {
		this.zxsx = zxsx;
	}

	public String getBflyyj() {
		return bflyyj;
	}

	public void setBflyyj(String bflyyj) {
		this.bflyyj = bflyyj;
	}

	public List<InstructionContent> getInstructionContentList() {
		return instructionContentList;
	}

	public void setInstructionContentList(
			List<InstructionContent> instructionContentList) {
		this.instructionContentList = instructionContentList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShrrealname() {
		return shrrealname;
	}

	public void setShrrealname(String shrrealname) {
		this.shrrealname = shrrealname;
	}

	public String getPfrrealname() {
		return pfrrealname;
	}

	public void setPfrrealname(String pfrrealname) {
		this.pfrrealname = pfrrealname;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public Date getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(Date zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getJsrid() {
		return jsrid;
	}

	public void setJsrid(String jsrid) {
		this.jsrid = jsrid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJszlh() {
        return jszlh;
    }

    public void setJszlh(String jszlh) {
        this.jszlh = jszlh == null ? null : jszlh.trim();
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getCkzl() {
        return ckzl;
    }

    public void setCkzl(String ckzl) {
        this.ckzl = ckzl == null ? null : ckzl.trim();
    }

    public String getZhut() {
        return zhut;
    }

    public void setZhut(String zhut) {
        this.zhut = zhut == null ? null : zhut.trim();
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }


    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getDyzt() {
		return dyzt;
	}

	public void setDyzt(Integer dyzt) {
		this.dyzt = dyzt;
	}

	public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }


	public List<OrderSource> getOrderSourceList() {
		return orderSourceList;
	}

	public void setOrderSourceList(List<OrderSource> orderSourceList) {
		this.orderSourceList = orderSourceList;
	}

	public User getJsrUser() {
		return jsrUser;
	}

	public void setJsrUser(User jsrUser) {
		this.jsrUser = jsrUser;
	}

	public User getFcrUser() {
		return fcrUser;
	}

	public void setFcrUser(User fcrUser) {
		this.fcrUser = fcrUser;
	}



	

    
    
}