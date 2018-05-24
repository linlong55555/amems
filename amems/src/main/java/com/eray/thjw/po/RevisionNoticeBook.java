package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import enu.ordersource.OrderSourceEnum;

/**
 * @author pingxiaojun
 * @description 修订通知书 po类
 * @develop date 2016.08.15
 */
public class RevisionNoticeBook extends BizEntity  {
    private String id;

    private String jszlh;

    private Integer tzslx;

    private String jx;

    private String ckzl;

    private String xdzt;

    private String xdnr;

    private String xdrid;

    private Date xdqx;

    private String bz;

    private Integer zt;
    
    private String ztText;

    private Integer dyzt;

    private List<OrderSource> orderSources;
    
    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;

    private User xdr;

    private User zdr;
    
    private Department dpt;
    
    private String keyword;
    
    private String shrid;
    
    private String pfrid;
    
    private Date shsj;
    
    private Date pfsj;
    
    private String shyj;
    
    private String pfyj;
    
    private String shrrealname;

    private String pfrrealname;
    
    private String shbmid;
    
    private String pfbmid;
    
    private String jsrid;
    
    private String jxgcsid;
    
    private Date pgrq;
    
    private Date jssj;
    
    private User shr_user;
    
    private User pfr_user;

    private User zdr_user;

    private User zdjs_user;
    
    private Department bm_dprt;
    
    private Date wcrq;
    
    private String bb;
    
    private Integer jszt;
    
    private Integer syts;
    
    
    
    public Integer getSyts() {
		return syts;
	}

	public void setSyts(Integer syts) {
		this.syts = syts;
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

	public User getZdjs_user() {
		return zdjs_user;
	}

	public void setZdjs_user(User zdjs_user) {
		this.zdjs_user = zdjs_user;
	}

	public Date getWcrq() {
		return wcrq;
	}

	public void setWcrq(Date wcrq) {
		this.wcrq = wcrq;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
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

	public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

	public String getJsrid() {
		return jsrid;
	}

	public void setJsrid(String jsrid) {
		this.jsrid = jsrid;
	}

	public String getJxgcsid() {
		return jxgcsid;
	}

	public void setJxgcsid(String jxgcsid) {
		this.jxgcsid = jxgcsid;
	}

	public Date getPgrq() {
		return pgrq;
	}

	public void setPgrq(Date pgrq) {
		this.pgrq = pgrq;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
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

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
	}


	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public Department getDpt() {
		return dpt;
	}

	public void setDpt(Department dpt) {
		this.dpt = dpt;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	//扩展字段
    private String pgdhs;
    
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

    public Integer getTzslx() {
        return tzslx;
    }

    public void setTzslx(Integer tzslx) {
        this.tzslx = tzslx;
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

    public String getXdzt() {
        return xdzt;
    }

    public void setXdzt(String xdzt) {
        this.xdzt = xdzt == null ? null : xdzt.trim();
    }

    public String getXdnr() {
        return xdnr;
    }

    public void setXdnr(String xdnr) {
        this.xdnr = xdnr == null ? null : xdnr.trim();
    }

    public String getXdrid() {
        return xdrid;
    }

    public void setXdrid(String xdrid) {
        this.xdrid = xdrid == null ? null : xdrid.trim();
    }

    public Date getXdqx() {
        return xdqx;
    }

    public void setXdqx(Date xdqx) {
        this.xdqx = xdqx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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
    
	public List<OrderSource> getOrderSources() {
		return orderSources;
	}

	public void setOrderSources(List<OrderSource> orderSources) {
		this.orderSources = orderSources;
	}
	
	


	@Override
	public String toString() {
		return "RevisionNoticeBook [id=" + id + ", jszlh=" + jszlh + ", tzslx="
				+ tzslx + ", jx=" + jx + ", ckzl=" + ckzl + ", xdzt=" + xdzt
				+ ", xdnr=" + xdnr + ", xdrid=" + xdrid + ", xdqx=" + xdqx
				+ ", bz=" + bz + ", zt=" + zt + ", dyzt=" + dyzt
				+ ", orderSources=" + orderSources + ", zdjsrid=" + zdjsrid
				+ ", zdjsrq=" + zdjsrq + ", zdjsyy=" + zdjsyy + ", xdr=" + xdr
				+ ", dpt=" + dpt + ", keyword=" + keyword + ", shrid=" + shrid
				+ ", pfrid=" + pfrid + ", shsj=" + shsj + ", pfsj=" + pfsj
				+ ", shyj=" + shyj + ", pfyj=" + pfyj + ", shrrealname="
				+ shrrealname + ", pfrrealname=" + pfrrealname + ", shbmid="
				+ shbmid + ", pfbmid=" + pfbmid + ", jsrid=" + jsrid
				+ ", jxgcsid=" + jxgcsid + ", pgrq=" + pgrq + ", jssj=" + jssj
				+ ", shr_user=" + shr_user + ", pfr_user=" + pfr_user
				+ ", zdr_user=" + zdr_user + ", bm_dprt=" + bm_dprt
				+ ", pgdhs=" + pgdhs + "]";
	}

	public String getPgdhs() {
		if (orderSources!=null && !orderSources.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (OrderSource orderSource : orderSources) {
				if (sb.length()==0) {
					sb.append(orderSource.getPgdh());
				}
				else {
					sb.append(",".concat(orderSource.getPgdh()));
				}
			}
			pgdhs = sb.toString();
		}
		return pgdhs;
	}

	public void setPgdhs(String pgdhs) {
		this.pgdhs = pgdhs;
	}

	public User getXdr() {
		return xdr;
	}

	public void setXdr(User xdr) {
		this.xdr = xdr;
	}
}