package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import enu.ordersource.OrderSourceEnum;

public class Engineering extends BaseEntity{
	
	private String gczlzxdxid;
	
    public String getGczlzxdxid() {
		return gczlzxdxid;
	}

	public void setGczlzxdxid(String gczlzxdxid) {
		this.gczlzxdxid = gczlzxdxid;
	}

	private String id;			//主键id

    private String gczlbh;		//工程指令编号

    private String ckzl;		//参考资料

    private String xggzh;		//相关改装号
   
    private String zhut;		//主题

    private Integer isCfzxsx;		//是否重复执行时限

    private Integer isZlphyx;		//是否重量与平衡影响

    private String bb;			//版本

    private Double jhGs;	//计划工时

    private String txyj;		//填写依据

    private String bz;			//备注

    private String zddwid;		//制单单位id

    private String zdrid;		//制单人id

    private Date zdsj;			//制单时间

    private Integer zt;			//状态

    private String ztText;			//状态text
    
    private String dprtcode;	//机构代码

    private String zdjsrid;		//指定结束人id

    private Date zdjsrq;		//指定结束日期

    private String zdjsyy;		//指定结束原因

    private String shbmid;		//审核部门id

    private String shyj;		//审核意见

    private String shrid;		//审核人id

    private Date shsj;			//审核时间

    private String pfbmid;		//批复部门id

    private String pfyj;		//批复意见

    private String pfrid;		//批复人id

    private Date pfsj;			//批复时间
    
    private String realname;	//真实姓名
    
    private String username;	//用户名
    
    private String shrrealname;	//审核人姓名
    
    private String pfrrealname;	//批复人姓名
    
    private List<OrderSource> orderSourceList;		//关联评估单
    
    private List<DetailEngineering> detailEngineeringList; //明细表
    
    private List<String> olddetailEngineeringListId; //旧明细表
    
    private String pgdh;		//评估单号查询条件（假）
    
    private String keyword;		//关键字查询
    
    private String fjjx;		//飞机机型
    
    private String zjh;			//章节号
    
    private String zwms;			//中文名称
    
    private String eolx;		//Eo类型
    
    private String cj;			//重检
    
    private String bbly;		//颁布本工程指令理由
    
    private String syxcbw;		//受影响的出版物
    
    private String tcsj;		//停场时间
    
    private Integer isYxfjdqfzsj;	//影响飞机电气负载数据
    
    private Integer isYxcbw;		//影响出版物
    
    private Integer isSp;		//索赔
    
    private Integer isXytsqc;	//需要特殊器材
    
    private Integer isXytsgjsb;	//需要特殊工具和设备

    private Integer isXybfqcbhtzd;	//需要颁发器材备货通知单
    
    private String pgjg;	//评估结果
    
    private User shr_user;
    
    private User pfr_user;
    
    private User zdr_user;

    private User zdjsr_user;
    
    private List<String> ids;
    
    
	public User getZdjsr_user() {
		return zdjsr_user;
	}

	public void setZdjsr_user(User zdjsr_user) {
		this.zdjsr_user = zdjsr_user;
	}

	public List<String> getIds() {
		return ids;
	}

	public String getZtText() {
		return zt==null?"":OrderSourceEnum.getName(zt);
	}
	public void setZtText(String ztText) {
		this.ztText = ztText;
	}
	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms;
	}

	public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
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

	public String getPgjg() {
		return pgjg;
	}

	public void setPgjg(String pgjg) {
		this.pgjg = pgjg;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getEolx() {
		return eolx;
	}

	public void setEolx(String eolx) {
		this.eolx = eolx;
	}

	public String getCj() {
		return cj;
	}

	public void setCj(String cj) {
		this.cj = cj;
	}

	public String getBbly() {
		return bbly;
	}

	public void setBbly(String bbly) {
		this.bbly = bbly;
	}

	public String getSyxcbw() {
		return syxcbw;
	}

	public void setSyxcbw(String syxcbw) {
		this.syxcbw = syxcbw;
	}

	public String getTcsj() {
		return tcsj;
	}

	public void setTcsj(String tcsj) {
		this.tcsj = tcsj;
	}

	
	public Integer getIsYxfjdqfzsj() {
		return isYxfjdqfzsj;
	}

	public void setIsYxfjdqfzsj(Integer isYxfjdqfzsj) {
		this.isYxfjdqfzsj = isYxfjdqfzsj;
	}

	public Integer getIsYxcbw() {
		return isYxcbw;
	}

	public void setIsYxcbw(Integer isYxcbw) {
		this.isYxcbw = isYxcbw;
	}

	public Integer getIsSp() {
		return isSp;
	}

	public void setIsSp(Integer isSp) {
		this.isSp = isSp;
	}

	public Integer getIsXytsqc() {
		return isXytsqc;
	}

	public void setIsXytsqc(Integer isXytsqc) {
		this.isXytsqc = isXytsqc;
	}

	public Integer getIsXytsgjsb() {
		return isXytsgjsb;
	}

	public void setIsXytsgjsb(Integer isXytsgjsb) {
		this.isXytsgjsb = isXytsgjsb;
	}

	public Integer getIsXybfqcbhtzd() {
		return isXybfqcbhtzd;
	}

	public void setIsXybfqcbhtzd(Integer isXybfqcbhtzd) {
		this.isXybfqcbhtzd = isXybfqcbhtzd;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public List<String> getOlddetailEngineeringListId() {
		return olddetailEngineeringListId;
	}

	public void setOlddetailEngineeringListId(
			List<String> olddetailEngineeringListId) {
		this.olddetailEngineeringListId = olddetailEngineeringListId;
	}

	public List<DetailEngineering> getDetailEngineeringList() {
		return detailEngineeringList;
	}

	public void setDetailEngineeringList(
			List<DetailEngineering> detailEngineeringList) {
		this.detailEngineeringList = detailEngineeringList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGczlbh() {
        return gczlbh;
    }

    public void setGczlbh(String gczlbh) {
        this.gczlbh = gczlbh == null ? null : gczlbh.trim();
    }

    public String getCkzl() {
        return ckzl;
    }

    public void setCkzl(String ckzl) {
        this.ckzl = ckzl == null ? null : ckzl.trim();
    }

    public String getXggzh() {
        return xggzh;
    }

    public void setXggzh(String xggzh) {
        this.xggzh = xggzh == null ? null : xggzh.trim();
    }

    public String getZhut() {
        return zhut;
    }

    public void setZhut(String zhut) {
        this.zhut = zhut == null ? null : zhut.trim();
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb == null ? null : bb.trim();
    }


    public Double getJhGs() {
		return jhGs;
	}

	public void setJhGs(Double jhGs) {
		this.jhGs = jhGs;
	}

	public String getTxyj() {
        return txyj;
    }

    public void setTxyj(String txyj) {
        this.txyj = txyj == null ? null : txyj.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
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


    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
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
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
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
        this.pfbmid = pfbmid == null ? null : pfbmid.trim();
    }

    public String getPfyj() {
        return pfyj;
    }

    public void setPfyj(String pfyj) {
        this.pfyj = pfyj == null ? null : pfyj.trim();
    }

    public String getPfrid() {
        return pfrid;
    }

    public void setPfrid(String pfrid) {
        this.pfrid = pfrid == null ? null : pfrid.trim();
    }

    public Date getPfsj() {
        return pfsj;
    }

    public void setPfsj(Date pfsj) {
        this.pfsj = pfsj;
    }

	public Integer getIsCfzxsx() {
		return isCfzxsx;
	}

	public void setIsCfzxsx(Integer isCfzxsx) {
		this.isCfzxsx = isCfzxsx;
	}

	public Integer getIsZlphyx() {
		return isZlphyx;
	}

	public void setIsZlphyx(Integer isZlphyx) {
		this.isZlphyx = isZlphyx;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public List<OrderSource> getOrderSourceList() {
		return orderSourceList;
	}

	public void setOrderSourceList(List<OrderSource> orderSourceList) {
		this.orderSourceList = orderSourceList;
	}

	public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh;
	}

	@Override
	public String toString() {
		return "Engineering [pagination=" + pagination + ", id=" + id
				+ ", gczlbh=" + gczlbh + ", ckzl=" + ckzl + ", xggzh=" + xggzh
				+ ", zhut=" + zhut + ", isCfzxsx=" + isCfzxsx + ", isZlphyx="
				+ isZlphyx + ", bb=" + bb + ", jhGs=" + jhGs + ", txyj=" + txyj
				+ ", bz=" + bz + ", zddwid=" + zddwid + ", zdrid=" + zdrid
				+ ", zdsj=" + zdsj + ", zt=" + zt + ", dprtcode=" + dprtcode
				+ ", zdjsrid=" + zdjsrid + ", zdjsrq=" + zdjsrq + ", zdjsyy="
				+ zdjsyy + ", shbmid=" + shbmid + ", shyj=" + shyj + ", shrid="
				+ shrid + ", shsj=" + shsj + ", pfbmid=" + pfbmid + ", pfyj="
				+ pfyj + ", pfrid=" + pfrid + ", pfsj=" + pfsj + ", realname="
				+ realname + ", orderSourceList=" + orderSourceList + ", pgdh="
				+ pgdh + "]";
	}
    
    
    
    
}